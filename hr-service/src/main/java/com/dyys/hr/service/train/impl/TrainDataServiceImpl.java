package com.dyys.hr.service.train.impl;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Matcher;
import cn.hutool.json.JSONUtil;
import com.dagongma.kernel.commons.enums.ResultCode;
import com.dagongma.kernel.commons.exceptions.BusinessException;
import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.train.TrainDataMapper;
import com.dyys.hr.dao.train.TrainMaterialsLearningRecordMapper;
import com.dyys.hr.dto.train.ELearningDTO;
import com.dyys.hr.dto.train.PersonDTO;
import com.dyys.hr.entity.staff.StaffDepartment;
import com.dyys.hr.entity.staff.StaffJob;
import com.dyys.hr.entity.train.*;
import com.dyys.hr.entity.train.excel.TrainHistoryDataExcel;
import com.dyys.hr.service.staff.IStaffDepartmentService;
import com.dyys.hr.service.staff.IStaffJobService;
import com.dyys.hr.service.staff.IStaffUserInfoService;
import com.dyys.hr.service.train.*;
import com.dyys.hr.vo.common.PsPersionVO;
import com.dyys.hr.vo.statis.PosnGradeCostVO;
import com.dyys.hr.vo.train.*;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;
import tk.mybatis.mapper.entity.Condition;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 培训集合数据表
 *
 * @author sie sie
 * @since 1.0.0 2022-09-01
 */
@Service
public class TrainDataServiceImpl extends AbstractCrudService<TrainData, Long> implements TrainDataService {
    @Autowired
    private TrainBaseCourseService trainBaseCourseService;
    @Autowired
    private TrainBaseCourseTeacherService trainBaseCourseTeacherService;
    @Autowired
    private TrainBaseTeacherService trainBaseTeacherService;
    @Autowired
    private TrainBaseTeacherTaughtService trainBaseTeacherTaughtService;
    @Autowired
    private TrainProgramsService trainProgramsService;
    @Autowired
    private TrainTraineeSummaryService trainTraineeSummaryService;
    @Autowired
    private TrainProgramsPlanService trainProgramsPlanService;
    @Autowired
    private TrainProgramsParticipantsService trainProgramsParticipantsService;
    @Autowired
    private TrainDataMapper trainDataMapper;
    @Autowired
    private TrainEmployeeCertificateService trainEmployeeCertificateService;
    @Autowired
    private TrainConstantService trainConstantService;
    @Autowired
    private IStaffUserInfoService iStaffUserInfoService;
    @Autowired
    private IStaffJobService iStaffJobService;
    @Autowired
    private IStaffDepartmentService staffDepartmentService;
    @Autowired
    private TrainCostService trainCostService;
    @Autowired
    private TrainMaterialsLearningRecordMapper trainMaterialsLearningRecordMapper;

    @Override
    public PageInfo<ELearningPageVO> eLearningPageList(Map<String, Object> params){
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        List<ELearningPageVO> voList = trainDataMapper.eLearningPageList(params);
        return new PageInfo<>(voList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean eLearningSave(List<ELearningDTO> dataList){
        if(dataList.isEmpty()){
            throw new BusinessException(ResultCode.ERROR,"同步数据为空");
        }
        //获取所有已存在的基础课程编码
        List<TrainBaseCourse> courseList = trainBaseCourseService.allCourseList();
        List<TrainData> entityList = new ArrayList<>();
        for(ELearningDTO dto : dataList){
            //校验课程不重复插入
            Matcher<TrainBaseCourse> matcher = trainBaseCourse -> trainBaseCourse.getNumber().equals(dto.getCourseNumber());
            int orderBy = ListUtil.lastIndexOf(courseList, matcher);
            if(orderBy < 0){
                TrainBaseCourse baseCourse = new TrainBaseCourse();
                baseCourse.setNumber(dto.getCourseNumber());
                baseCourse.setName(dto.getCourseName());
                baseCourse.setCategory(dto.getCourseCategory());
                baseCourse.setClassHours(dto.getCourseClassHours());
                baseCourse.setCredit(dto.getCourseCredit());
                baseCourse.setCreateUser("");
                baseCourse.setCreateTime(System.currentTimeMillis()/1000);
                trainBaseCourseService.insertSelective(baseCourse);
            }
            TrainData trainData = new TrainData();
            BeanUtils.copyProperties(dto,trainData);
            trainData.setCourseStartTime(dto.getGetCreditTime());
            trainData.setCourseEndTime(dto.getGetCreditTime());
            trainData.setType(1);
            trainData.setStatus(0);
            trainData.setCreateUser("");
            trainData.setCreateTime(System.currentTimeMillis()/1000);
            entityList.add(trainData);
        }
        if(!entityList.isEmpty()){
            return this.insertBatchSelective(entityList);
        }
        return false;
    }

    @Override
    public PageInfo<HistoryDataPageVO> historyDataPageList(Map<String, Object> params){
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        List<HistoryDataPageVO> voList = trainDataMapper.historyDataPageList(params);
        return new PageInfo<>(voList);
    }

    /**
     * 同步培训历史数据
     * @return
     * @param loginEmplId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateHistoryData(String loginEmplId){
        //选择未处理的历史数据
        TrainData entity = new TrainData();
        entity.setType(2); // 培训历史数据
        entity.setStatus(0); // 未同步
        List<TrainData> dataList = trainDataMapper.select(entity);
        if (!dataList.isEmpty()) {
            Map<String, TrainPrograms> programMap = new HashMap<>();
            Map<String, TrainBaseCourse> courseMap = new HashMap<>();
            Map<String, TrainBaseTeacher> teacherMap = new HashMap<>();
            Map<String, TrainProgramsPlan> programsPlanMap = new HashMap<>();
            Map<String, TrainProgramsParticipants> participantsMap = new HashMap<>();
            Map<String, TrainBaseCourseTeacher> courseTeacherMap = new HashMap<>();
            Map<String, TrainBaseTeacherTaught> teacherTaughtMap = new HashMap<>();
            List<TrainProgramsPlan> programsPlanList = new ArrayList<>(); // 初始化培训项目的培训计划表数据
            List<TrainProgramsParticipants> programsParticipantList = new ArrayList<>(); // 初始化培训项目的参训人员数据
            List<TrainTraineeSummary> summaryList = new ArrayList<>(); // 初始化培训项目总结数据
            List<TrainBaseCourseTeacher> courseTeacherList = new ArrayList<>(); // 初始化课程的可授课讲师数据
            List<TrainBaseTeacherTaught> teacherTaughtList = new ArrayList<>(); // 初始化讲师的已授课程数据
            Long currStamp = System.currentTimeMillis() / 1000;
            int i = 1;
            for (TrainData tData : dataList) {
                TrainData dataUpdate = new TrainData();
                Long stamp = System.currentTimeMillis(); // 当前时间戳
                Long num = stamp + i;
                Map<String, Date> dateMap = this.getFinalDate(tData);
                Date minCourseStartTime = dateMap.get("minCourseStartTime"); // 最小课程开始日期
                Date maxCourseEndTime = dateMap.get("maxCourseEndTime"); // 最大课程结束日期

                String programMapKey = tData.getTrainName() + "-" + tData.getStartTime() + "-" + tData.getEndTime(); // 培训项目唯一标识
                // 处理培训项目
                TrainPrograms programsInfo = new TrainPrograms();
                if (!programMap.containsKey(programMapKey)) {
                    TrainPrograms programsUnique = new TrainPrograms();
                    programsUnique.setTrainName(tData.getTrainName());
                    programsUnique.setStartTime(tData.getStartTime());
                    programsUnique.setEndTime(tData.getEndTime());
                    programsUnique.setIsImport(1); // 培训历史数据同步
                    TrainPrograms proInfo = trainProgramsService.selectOne(programsUnique);
                    if (proInfo == null) {
                        // 培训项目不存在则生成新培训项目
                        String trainNumber = "P" + num;
                        Long programsId = this.trainProgramsSave(tData, trainNumber, loginEmplId);
                        programsInfo.setId(programsId);
                        programsInfo.setNumber(trainNumber);
                    } else {
                        programsInfo.setId(proInfo.getId());
                        programsInfo.setNumber(proInfo.getNumber());
                    }
                    programMap.put(programMapKey, programsInfo);
                } else {
                    programsInfo = programMap.get(programMapKey);
                }
                String courseMapKey = tData.getCourseName() + "-" + tData.getCourseCategory(); // 培训课程唯一标识
                // 处理课程
                TrainBaseCourse courseInfo = new TrainBaseCourse();
                if (!courseMap.containsKey(courseMapKey)) {
                    TrainBaseCourse courseUnique = new TrainBaseCourse();
                    courseUnique.setName(tData.getCourseName());
                    courseUnique.setCategory(tData.getCourseCategory());
                    TrainBaseCourse couInfo = trainBaseCourseService.selectOne(courseUnique);
                    if (couInfo == null) {
                        // 课程不存在则生成新课程
                        String courseNumber = "C" + stamp + i;
                        Long courseId = this.trainBaseCourseSave(tData, courseNumber, loginEmplId);
                        courseInfo.setId(courseId);
                        courseInfo.setNumber(courseNumber);
                    } else {
                        courseInfo.setId(couInfo.getId());
                        courseInfo.setNumber(couInfo.getNumber());
                    }
                    courseMap.put(courseMapKey, courseInfo);
                } else {
                    courseInfo = courseMap.get(courseMapKey);
                }
                // 处理讲师
                if (tData.getTeacherType() != null
                        && (tData.getTeacherType().equals(1) || tData.getTeacherType().equals(2))) {
                    String teacherMapKey = tData.getTeacherType() + "-" + tData.getTeacherNumber() + "-" + tData.getTeacherName(); // 培训讲师唯一标识
                    TrainBaseTeacher teacherInfo = new TrainBaseTeacher();
                    if (!teacherMap.containsKey(teacherMapKey)) {
                        TrainBaseTeacher teacherUnique = new TrainBaseTeacher();
                        teacherUnique.setType(tData.getTeacherType());
                        if (tData.getTeacherType().equals(1)) {
                            // 内部讲师
                            teacherUnique.setNumber(tData.getTeacherNumber());
                        } else {
                            // 外部讲师
                            teacherUnique.setName(tData.getTeacherName());
                        }
                        TrainBaseTeacher teachInfo = trainBaseTeacherService.selectOne(teacherUnique);
                        if (teachInfo == null) {
                            // 讲师不存在则生成新讲师
                            String teacherNumber = tData.getTeacherType().equals(1) ? tData.getTeacherNumber() : ("T" + stamp + i);
                            Long teacherId = this.trainBaseTeacherSave(tData, teacherNumber, loginEmplId);
                            teacherInfo.setId(teacherId);
                            teacherInfo.setNumber(teacherNumber);
                        } else {
                            teacherInfo.setId(teachInfo.getId());
                            teacherInfo.setNumber(teachInfo.getNumber());
                        }
                        teacherMap.put(teacherMapKey, teacherInfo);
                    } else {
                        teacherInfo = teacherMap.get(teacherMapKey);
                    }
                    String courseTeacherMapKey = courseMapKey + "-" + teacherMapKey; // 课程的可授课讲师唯一标识
                    // 处理课程的可授课讲师
                    if (!courseTeacherMap.containsKey(courseTeacherMapKey)) {
                        TrainBaseCourseTeacher courseTeacher = new TrainBaseCourseTeacher();
                        courseTeacher.setCourseId(courseInfo.getId());
                        courseTeacher.setTeacherId(teacherInfo.getId());
                        TrainBaseCourseTeacher courseTeacherInfo = trainBaseCourseTeacherService.selectOne(courseTeacher);
                        if (courseTeacherInfo == null) {
                            // 不存在则新增
                            courseTeacher.setNumber(teacherInfo.getNumber());
                            courseTeacher.setName(tData.getTeacherName());
                            courseTeacher.setType(tData.getTeacherType());
                            courseTeacher.setOrganizationName(tData.getTeacherOrganizationName());
                            courseTeacherList.add(courseTeacher);
                        }
                        courseTeacherMap.put(courseTeacherMapKey, courseTeacher);
                    }
                    String teacherTaughtMapKey = programMapKey + "-" + courseMapKey + "-" + teacherMapKey; // 讲师的已授课程唯一标识
                    // 处理讲师已授课程
                    if (!teacherTaughtMap.containsKey(teacherTaughtMapKey)) {
                        TrainBaseTeacherTaught teacherTaught = new TrainBaseTeacherTaught();
                        teacherTaught.setTeacherId(teacherInfo.getId());
                        teacherTaught.setProgramsId(programsInfo.getId());
                        teacherTaught.setCourseId(courseInfo.getId());
                        TrainBaseTeacherTaught teacherTaughtInfo = trainBaseTeacherTaughtService.selectOne(teacherTaught);
                        if (teacherTaughtInfo == null) {
                            teacherTaught.setCourseStartTime(minCourseStartTime);
                            teacherTaught.setCourseEndTime(maxCourseEndTime);
                            teacherTaught.setExamineForm(tData.getExamineForm());
                            teacherTaught.setLearningForm(tData.getLearningForm());
                            teacherTaught.setParticipantsTotal(tData.getParticipantsNum());
                            teacherTaught.setCreateUser(loginEmplId);
                            teacherTaught.setCreateTime(currStamp);
                            teacherTaughtList.add(teacherTaught);
                        }
                        teacherTaughtMap.put(teacherTaughtMapKey, teacherTaught);
                    }
                    dataUpdate.setTeacherNumber(teacherInfo.getNumber());
                }
                String programsPlanMapKey = programMapKey + "-" + courseMapKey; // 培训计划表唯一标识
                // 处理培训计划表
                if (!programsPlanMap.containsKey(programsPlanMapKey)) {
                    TrainProgramsPlan programsPlanInfo = trainProgramsPlanService.getPlanInfo(programsInfo.getId(), courseInfo.getId(), 1);
                    TrainProgramsPlan programsPlan = new TrainProgramsPlan();
                    if (programsPlanInfo == null) {
                        // 不存在则需要新增
                        programsPlan.setProgramsId(programsInfo.getId());
                        programsPlan.setCourseId(courseInfo.getId());
                        programsPlan.setStartTime(minCourseStartTime);
                        programsPlan.setEndTime(maxCourseEndTime);
                        programsPlan.setLearningForm(tData.getLearningForm());
                        programsPlan.setExamineForm(tData.getExamineForm());
                        programsPlan.setIsImport(1); // 培训历史数据
                        programsPlan.setCreateUser(loginEmplId);
                        programsPlan.setCreateTime(currStamp);
                        programsPlan.setUpdateUser(loginEmplId);
                        programsPlan.setUpdateTime(currStamp);
                        programsPlanList.add(programsPlan);
                    } else {
                        // 存在则需要处理一下培训计划中课程开始日期和结束日期
                        if (!programsPlanInfo.getStartTime().equals(minCourseStartTime)
                                || !programsPlanInfo.getEndTime().equals(maxCourseEndTime)) {
                            programsPlan.setId(programsPlanInfo.getId());
                            programsPlan.setStartTime(minCourseStartTime);
                            programsPlan.setEndTime(maxCourseEndTime);
                            trainProgramsPlanService.updateSelective(programsPlan);
                        }
                    }
                    programsPlanMap.put(programsPlanMapKey, programsPlan);
                }
                String participantsMapKey = programMapKey + "-" + tData.getEmplId(); // 培训项目参训人员唯一标识
                // 处理参训人员和培训总结信息
                if (!participantsMap.containsKey(participantsMapKey)) {
                    // 处理参训人员
                    TrainProgramsParticipants participants = new TrainProgramsParticipants();
                    participants.setProgramsId(programsInfo.getId());
                    participants.setUserId(tData.getEmplId());
                    participants.setIsImport(1); // 培训历史数据
                    TrainProgramsParticipants participantsInfo = trainProgramsParticipantsService.selectOne(participants);
                    if (participantsInfo == null) {
                        // 若不在参训人员中，则生成
                        StaffJob staffJob = iStaffJobService.selectById(tData.getEmplId());
                        participants.setCompanyCode(staffJob.getCompany());
                        participants.setDepartmentCode(staffJob.getDeptId());
                        participants.setJobCode(staffJob.getPostCode());
                        participants.setStatus(1); // 默认为已报名
                        participants.setCreateUser(loginEmplId);
                        participants.setCreateTime(currStamp);
                        participants.setUpdateUser(loginEmplId);
                        participants.setUpdateTime(currStamp);
                        programsParticipantList.add(participants);
                    }
                    participantsMap.put(participantsMapKey, participants);
                    // 处理培训总结
                    TrainTraineeSummary summary = new TrainTraineeSummary();
                    summary.setProgramsId(programsInfo.getId());
                    summary.setUserId(tData.getEmplId());
                    summary.setSourceType(20);
                    TrainTraineeSummary summaryInfo = trainTraineeSummaryService.selectOne(summary);
                    if (summaryInfo == null) {
                        // 若不存在，则生成培训总结
                        summary.setTotalScore(tData.getTotalScore());
                        summary.setIsObtain(tData.getIsObtain());
                        summary.setCreateUser(loginEmplId);
                        summary.setCreateTime(currStamp);
                        summary.setUpdateUser(loginEmplId);
                        summary.setUpdateTime(currStamp);
                        summaryList.add(summary);
                    }
                }
                dataUpdate.setId(tData.getId());
                dataUpdate.setTrainNumber(programsInfo.getNumber());
                dataUpdate.setCourseNumber(courseInfo.getNumber());
                dataUpdate.setUpdateUser(loginEmplId);
                dataUpdate.setUpdateTime(currStamp);
                dataUpdate.setStatus(1); // 已处理
                updateSelective(dataUpdate); // 更新数据
                i++;
            }
            if (!programsPlanList.isEmpty()) {
                trainProgramsPlanService.insertBatchSelective(programsPlanList); // 批量保存培训计划表数据
            }
            if (!programsParticipantList.isEmpty()) {
                trainProgramsParticipantsService.insertBatchSelective(programsParticipantList); // 批量保存参训人员数据
            }
            if (!summaryList.isEmpty()) {
                trainTraineeSummaryService.insertBatchSelective(summaryList); // 批量保存参训人员培训总结
            }
            if (!courseTeacherList.isEmpty()) {
                trainBaseCourseTeacherService.insertBatchSelective(courseTeacherList); // 批量保存课程可授课讲师数据
            }
            if (!teacherTaughtList.isEmpty()) {
                trainBaseTeacherTaughtService.insertBatchSelective(teacherTaughtList); // 批量保存讲师已授课程数据
            }
            // 处理培训费用
            for (Map.Entry<String, TrainPrograms> entry : programMap.entrySet()) {
                // 处理项目费用
                handleTrainCost(entry.getValue(), loginEmplId);
            }
        }
        return true;
    }

    /**
     * 历史数据同步-处理项目培训费用
     * @param programs
     * @param loginEmplId
     */
    private void handleTrainCost(TrainPrograms programs, String loginEmplId) {
        Long currStamp = System.currentTimeMillis()/1000; // 当前时间戳
        Float maxOutlay = trainDataMapper.getMaxOutlay(programs.getNumber(), 2);
        if (maxOutlay != null) {
            TrainCost costData = new TrainCost();
            costData.setProgramsId(programs.getId());
            costData.setSubjectsId(0); // 历史数据同步的费用不分费用科目，所以默认为0
            TrainCost costInfo = trainCostService.selectOne(costData);
            if (costInfo == null) {
                // 不存在费用记录则新增
                costData.setAmount(maxOutlay);
                costData.setCreateUser(loginEmplId);
                costData.setCreateTime(currStamp);
                costData.setUpdateUser(loginEmplId);
                costData.setUpdateTime(currStamp);
                trainCostService.insertSelective(costData);
            } else {
                costInfo.setAmount(maxOutlay);
                costInfo.setUpdateUser(loginEmplId);
                costInfo.setUpdateTime(currStamp);
                trainCostService.updateSelective(costInfo);
            }
            Integer partTol = trainDataMapper.getPartTol(programs.getNumber(), 2); // 当前培训项目实际参与人数
            BigDecimal avgFee = BigDecimal.valueOf(maxOutlay).divide(BigDecimal.valueOf(partTol), 2, BigDecimal.ROUND_HALF_UP); // 参训人员平均费用
            // 批量更新参训人员平均费用
            Condition condition = new Condition(TrainData.class);
            condition.createCriteria().andEqualTo("trainNumber", programs.getNumber())
                    .andEqualTo("type", 2);
            TrainData trainData = new TrainData();
            trainData.setParticipantsNum(partTol);
            trainData.setAvgFee(avgFee.toString());
            this.updateByConditionSelective(trainData, condition);
        }
    }

    /**
     * 历史数据同步-获取同一培训项目同一课程的最小开始时间和最大开始时间
     * @param tData
     * @return
     */
    private Map<String, Date> getFinalDate(TrainData tData) {
        Map<String, Object> params = new HashMap<>();
        params.put("trainName", tData.getTrainName());
        params.put("startTime", tData.getStartTime());
        params.put("endTime", tData.getEndTime());
        params.put("courseName", tData.getCourseName());
        params.put("courseCategory", tData.getCourseCategory());
        params.put("type", 2); // 培训历史数据
        Date minCourseStartTime = trainDataMapper.getMinCourseStartTime(params);
        Date maxCourseEndTime = trainDataMapper.getMaxCourseEndTime(params);
        Map<String, Date> map = new HashMap<>();
        map.put("minCourseStartTime", minCourseStartTime);
        map.put("maxCourseEndTime", maxCourseEndTime);
        return map;
    }

    /**
     * 历史数据同步-讲师数据保存
     * @param tData
     * @param teacherNumber
     * @param loginEmplId
     * @return
     */
    private Long trainBaseTeacherSave(TrainData tData, String teacherNumber, String loginEmplId) {
        Long currStamp = System.currentTimeMillis()/1000;
        TrainBaseTeacher baseTeacher = new TrainBaseTeacher();
        baseTeacher.setNumber(teacherNumber);
        baseTeacher.setName(tData.getTeacherName());
        baseTeacher.setType(tData.getTeacherType());
        baseTeacher.setSex(tData.getTeacherSex());
        baseTeacher.setContactPhone(tData.getTeacherContactPhone());
        baseTeacher.setEmail(tData.getTeacherEmail());
        baseTeacher.setOrganizationName(tData.getTeacherOrganizationName());
        baseTeacher.setStatus(1); // 默认有效
        baseTeacher.setIsImport(2); // 培训历史数据同步
        baseTeacher.setIsDelete(0);
        baseTeacher.setCreateUser(loginEmplId);
        baseTeacher.setCreateTime(currStamp);
        baseTeacher.setUpdateUser(loginEmplId);
        baseTeacher.setUpdateTime(currStamp);
        return trainBaseTeacherService.insertSelective(baseTeacher);
    }

    /**
     * 历史数据同步-课程数据保存
     * @param tData
     * @param courseNumber
     * @param loginEmplId
     * @return
     */
    private Long trainBaseCourseSave(TrainData tData, String courseNumber, String loginEmplId) {
        Long currStamp = System.currentTimeMillis()/1000;
        TrainBaseCourse baseCourse = new TrainBaseCourse();
        baseCourse.setNumber(courseNumber);
        baseCourse.setName(tData.getCourseName());
        baseCourse.setCategory(tData.getCourseCategory());
        baseCourse.setClassHours(tData.getCourseClassHours());
        baseCourse.setCredit(tData.getCourseCredit());
        baseCourse.setInstructions(tData.getCourseInstructions());
        baseCourse.setIsImport(2); // 培训历史数据同步
        baseCourse.setCourseSource(2); // 默认为自有课程
        baseCourse.setIsDelete(0);
        baseCourse.setCreateUser(loginEmplId);
        baseCourse.setCreateTime(currStamp);
        baseCourse.setUpdateUser(loginEmplId);
        baseCourse.setUpdateTime(currStamp);
        return trainBaseCourseService.insertSelective(baseCourse);
    }

    /**
     * 历史数据同步-培训项目数据保存
     * @param tData
     * @param trainNumber
     * @param loginEmplId
     * @return
     */
    private Long trainProgramsSave(TrainData tData, String trainNumber, String loginEmplId) {
        Long currStamp = System.currentTimeMillis()/1000;
        TrainPrograms trainPrograms = new TrainPrograms();
        trainPrograms.setNumber(trainNumber);
        trainPrograms.setTrainName(tData.getTrainName());
        trainPrograms.setCompanyCode(tData.getCompanyCode());
        trainPrograms.setDeptId(tData.getDeptId());
        trainPrograms.setTrainShape(tData.getTrainShape());
        trainPrograms.setStartTime(tData.getStartTime());
        trainPrograms.setEndTime(tData.getEndTime());
        trainPrograms.setParticipantsNum(tData.getParticipantsNum());
        trainPrograms.setOutlay(new BigDecimal(tData.getOutlay()));
        List<PersonDTO> principalList = new ArrayList<>();
        PersonDTO personDTO = new PersonDTO();
        personDTO.setEmplId(tData.getPrincipalId());
        personDTO.setEmplName(tData.getPrincipalName());
        principalList.add(personDTO);
        trainPrograms.setPrincipalList(JSONUtil.toJsonStr(principalList));
        trainPrograms.setIsImport(1); // 培训历史数据同步
        trainPrograms.setStatus(2); // 已完成
        trainPrograms.setIsDelete(0);
        trainPrograms.setCreateUser(loginEmplId);
        trainPrograms.setCreateTime(currStamp);
        trainPrograms.setUpdateUser(loginEmplId);
        trainPrograms.setUpdateTime(currStamp);
        return trainProgramsService.insertSelective(trainPrograms);
    }

    @Override
    public List<EmplTrainListVO> trainList(@ApiIgnore @RequestParam Map<String, Object> params){
        List<EmplTrainListVO> trainList = trainDataMapper.getGroupTrainList(params);
        for (EmplTrainListVO vo : trainList){
            params.put("trainNumber",vo.getTrainNumber());
           vo.setCourseList(trainDataMapper.getTrainCourseList(params));
        }
        return trainList;
    }

    @Override
    public List<EmplELearningListVO> emplELearningList(Map<String, Object> params){
        List<EmplELearningListVO> listVOS = trainDataMapper.emplELearningList(params);
        for (EmplELearningListVO listVO : listVOS){
//            List<String> durationList = trainMaterialsLearningRecordMapper.getHaveFinishDurationList(params.get("emplId").toString(), listVO.getTrainNumber());
//            int hour = 0;
//            int min = 0;
//            int sec = 0;
//            for (String duration : durationList){
//                List<String> list = Arrays.asList(duration.split(":"));
//                hour += Integer.parseInt(list.get(0));
//                min += Integer.parseInt(list.get(1));
//                sec += Integer.parseInt(list.get(2));
//            }
            listVO.setHaveLearnedTime(null);
        }
        return listVOS;
    }

    /**
     * 员工完成地图推荐指定课程或同等课程的数量
     * @param courseNums
     * @param emplId
     * @return
     */
    @Override
    public Integer countFinishCourse(List<String> courseNums, String emplId) {
        return trainDataMapper.countFinishCourse(courseNums, emplId);
    }

    /**
     * 员工培训数据
     * @param emplId
     * @return
     */
    @Override
    public EmployeeTrainDataVO employTrainData(String emplId){
        EmployeeTrainDataVO vo = new EmployeeTrainDataVO();
        //培训信息
        Map<String, Object> map = new HashMap<>();
        map.put("emplId",emplId);
        List<TrainData> list = trainDataMapper.getList(map);
        LinkedList<EmployeeTrainProgramsDataVO> externalList = new LinkedList<>();
        LinkedList<EmployeeTrainProgramsDataVO> internalList = new LinkedList<>();
        for (TrainData entity : list){
            //1.内部 2.外部
            if(entity.getTrainShape() == 1){
                EmployeeTrainProgramsDataVO dataVO = new EmployeeTrainProgramsDataVO();
                BeanUtils.copyProperties(entity,dataVO);
                internalList.add(dataVO);
            }
            if(entity.getTrainShape() == 2){
                EmployeeTrainProgramsDataVO dataVO = new EmployeeTrainProgramsDataVO();
                BeanUtils.copyProperties(entity,dataVO);
                externalList.add(dataVO);
            }
        }
        vo.setExternalList(externalList);
        vo.setInternalList(internalList);
        //证书信息
        vo.setCertificateList(trainEmployeeCertificateService.emplCertificateList(map));
        return vo;
    }

    /**
     * 培训历史数据excel模板下拉项
     * @return
     */
    @Override
    public Map<Integer, List<String>> excelSelectMap() {
        Map<Integer, List<String>> selectMap = new HashMap<>();
        // 培训形式下拉项：1内部，2外部
        List<String> trainShapeList = new ArrayList<>();
        trainShapeList.add("1");
        trainShapeList.add("2");
        selectMap.put(5, trainShapeList);
        // 是否取证下拉项：1是，0否
        List<String> isObtainList = new ArrayList<>();
        isObtainList.add("1");
        isObtainList.add("0");
        selectMap.put(13, isObtainList);
        // 课程类别下拉项
        List<String> courseCateList = trainConstantService.selectBoxList(1, "0");
        selectMap.put(15, courseCateList);
        // 学习形式下拉项
        List<String> learningFormList = trainConstantService.selectBoxList(10, "0");
        selectMap.put(21, learningFormList);
        // 考核方式下拉项：1考试，2考察
        List<String> examineFormList = new ArrayList<>();
        examineFormList.add("1");
        examineFormList.add("2");
        selectMap.put(22, examineFormList);
        // 讲师类型下拉项：1内部，2外部
        List<String> teacherTypeList = new ArrayList<>();
        teacherTypeList.add("1");
        teacherTypeList.add("2");
        selectMap.put(24, teacherTypeList);
        // 讲师性别下拉项：M男，F女
        List<String> teacherSexList = new ArrayList<>();
        teacherSexList.add("M");
        teacherSexList.add("F");
        selectMap.put(27, teacherSexList);
        return selectMap;
    }

    /**
     * 培训历史数据导入
     * @param excelList
     * @param loginEmplId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<TrainHistoryDataExcel> importExl(List<TrainHistoryDataExcel> excelList, String loginEmplId) {
        Long stamp = System.currentTimeMillis()/1000; // 系统当前时间戳
        Map<Integer, List<String>> selectMap = this.excelSelectMap(); // 下拉选项集
        List<String> trainShapeList = selectMap.get(5); // 培训形式下拉项集
        List<String> isObtainList = selectMap.get(13); // 是否取证下拉项集
        List<String> courseCateList = selectMap.get(15); // 课程类别下拉项集
        List<String> learningFormList = selectMap.get(21); // 学习形式下拉项集
        List<String> examineFormList = selectMap.get(22); // 考核方式下拉项集
        List<String> teacherTypeList = selectMap.get(24); // 讲师类型下拉项集
        List<String> teacherSexList = selectMap.get(27); // 讲师性别下拉项集
        List<TrainHistoryDataExcel> errorList = new ArrayList<>(); // 错误数据
        Map<String, Integer> checkMap = new HashMap<>();
        List<TrainData> dataList = new ArrayList<>();
        ArrayList<Long> delIds = new ArrayList<>();
        int i = 0;
        int errNum = 0;
        for (TrainHistoryDataExcel excel : excelList) {
            i++;
            TrainHistoryDataExcel errVO = new TrainHistoryDataExcel();
            // 判断学员工号是否为空
            if (excel.getEmplId() == null || excel.getEmplId().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，学员工号为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            PsPersionVO emplInfo = iStaffUserInfoService.getUserInfoById(excel.getEmplId());
            // 判断学员工号是否正确
            if (emplInfo == null) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，学员工号不正确");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断项目名称是否为空
            if (excel.getTrainName() == null || excel.getTrainName().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，项目名称为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断组织单位编码是否为空
            if (excel.getDeptId() == null || excel.getDeptId().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，组织单位编码为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断组织单位编码是否正确
            StaffDepartment deptInfo = staffDepartmentService.selectById(excel.getDeptId());
            if (deptInfo == null) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，组织单位编码不正确");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断组织单位名称是否为空
            if (excel.getDeptName() == null || excel.getDeptName().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，组织单位名称为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断组织单位名称是否正确
            if (!excel.getDeptName().equals(deptInfo.getDescr())) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，组织单位名称与编码不一致");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断培训形式是否为空
            if (excel.getTrainShape() == null) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，培训形式为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断培训形式是否正确
            String trainShapeStr = String.valueOf(excel.getTrainShape());
            if (!trainShapeList.contains(trainShapeStr)) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，培训形式不正确");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断项目开始日期是否为空
            if (excel.getStartTime() == null || excel.getStartTime().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，项目开始日期为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            Date startDate;
            try {
                SimpleDateFormat sdfStart = new SimpleDateFormat("yyyy/M/d");
                Date startDateTmp = sdfStart.parse(excel.getStartTime());
                sdfStart = new SimpleDateFormat("yyyy-MM-dd");
                String startDateStr = sdfStart.format(startDateTmp);
                startDate = java.sql.Date.valueOf(startDateStr);
            } catch (ParseException e) {
                e.printStackTrace();
                throw new BusinessException(ResultCode.EXCEPTION,"第" + i + "条记录，项目开始日期格式不正确");
            }
            // 判断项目结束日期是否为空
            if (excel.getEndTime() == null || excel.getEndTime().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，项目结束日期为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            Date endDate;
            try {
                SimpleDateFormat sdfEnd = new SimpleDateFormat("yyyy/M/d");
                Date endDateTmp = sdfEnd.parse(excel.getEndTime());
                sdfEnd = new SimpleDateFormat("yyyy-MM-dd");
                String endDateStr = sdfEnd.format(endDateTmp);
                endDate = java.sql.Date.valueOf(endDateStr);
            } catch (ParseException e) {
                e.printStackTrace();
                throw new BusinessException(ResultCode.EXCEPTION,"第" + i + "条记录，项目结束日期格式不正确");
            }
            // 判断项目结束日期不能小于开始日期
            if (endDate.before(startDate)) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，项目结束日期小于开始日期");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断项目负责人工号是否正确
            if (excel.getPrincipalId() != null && !excel.getPrincipalId().trim().equals("")) {
                PsPersionVO principalInfo = iStaffUserInfoService.getUserInfoById(excel.getPrincipalId());
                if (principalInfo == null) {
                    BeanUtils.copyProperties(excel, errVO);
                    errVO.setErrMsg("第" + i + "条记录，项目负责人工号不正确");
                    errorList.add(errVO);
                    errNum++;
                    continue;
                }
                excel.setPrincipalName(principalInfo.getEmplName()); // 项目负责人姓名
            }
            // 判断是否取证是否为空
            if (excel.getIsObtain() == null) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，是否取证标识为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断培训形式是否正确
            String isObtainStr = String.valueOf(excel.getIsObtain());
            if (!isObtainList.contains(isObtainStr)) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，是否取证标识不正确");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断课程名称是否为空
            if (excel.getCourseName() == null || excel.getCourseName().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，课程名称为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断课程类别是否为空
            if (excel.getCourseCategoryName() == null || excel.getCourseCategoryName().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，课程类别为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断课程类别是否正确
            if (!courseCateList.contains(excel.getCourseCategoryName())) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，课程类别不正确");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断课程开始日期是否为空
            if (excel.getCourseStartTime() == null || excel.getCourseStartTime().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，课程开始日期为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            Date courseStartDate;
            try {
                SimpleDateFormat sdfCourseStart = new SimpleDateFormat("yyyy/M/d");
                Date courseStartDateTmp = sdfCourseStart.parse(excel.getCourseStartTime());
                sdfCourseStart = new SimpleDateFormat("yyyy-MM-dd");
                String courseStartDateStr = sdfCourseStart.format(courseStartDateTmp);
                courseStartDate = java.sql.Date.valueOf(courseStartDateStr);
            } catch (ParseException e) {
                e.printStackTrace();
                throw new BusinessException(ResultCode.EXCEPTION,"第" + i + "条记录，课程开始日期格式不正确");
            }
            // 判断课程开始日期是否小于项目开始日期
            if (courseStartDate.before(startDate)) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，课程开始日期小于项目开始日期");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断课程结束日期是否为空
            if (excel.getCourseEndTime() == null || excel.getCourseEndTime().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，课程结束日期为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            Date courseEndDate;
            try {
                SimpleDateFormat sdfCourseEnd = new SimpleDateFormat("yyyy/M/d");
                Date courseEndDateTmp = sdfCourseEnd.parse(excel.getCourseEndTime());
                sdfCourseEnd = new SimpleDateFormat("yyyy-MM-dd");
                String courseEndDateStr = sdfCourseEnd.format(courseEndDateTmp);
                courseEndDate = java.sql.Date.valueOf(courseEndDateStr);
            } catch (ParseException e) {
                e.printStackTrace();
                throw new BusinessException(ResultCode.EXCEPTION,"第" + i + "条记录，课程结束日期格式不正确");
            }
            // 判断课程结束日期不能小于开始日期
            if (courseEndDate.before(courseStartDate)) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，课程结束日期小于开始日期");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断课程结束日期是否大于项目结束日期
            if (endDate.before(courseEndDate)) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，课程结束日期大于项目结束日期");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断学习方式是否为空
            if (excel.getLearningFormName() == null || excel.getLearningFormName().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，学习方式为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断学习方式是否正确
            if (!learningFormList.contains(excel.getLearningFormName())) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，学习方式不正确");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断考核方式是否为空
            if (excel.getExamineForm() == null) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，考核方式为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断考核方式是否正确
            String examineFormStr = String.valueOf(excel.getExamineForm());
            if (!examineFormList.contains(examineFormStr)) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，考核方式不正确");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 当考核方式为考试时，判断课程成绩是否为空
            if (excel.getExamineForm().equals(1) && (excel.getCourseScore() == null || excel.getCourseScore().trim().equals(""))) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录，课程成绩为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 有讲师信息时，判断讲师信息
            if (excel.getTeacherType() != null
                    || (excel.getTeacherNumber() != null && !excel.getTeacherNumber().trim().equals(""))
                    || (excel.getTeacherName() != null && !excel.getTeacherName().trim().equals(""))) {
                // 判断讲师类型是否为空
                if (excel.getTeacherType() == null) {
                    BeanUtils.copyProperties(excel, errVO);
                    errVO.setErrMsg("第" + i + "条记录，讲师类型为空");
                    errorList.add(errVO);
                    errNum++;
                    continue;
                }
                // 判断讲师类型是否正确
                String teacherTypeStr = String.valueOf(excel.getTeacherType());
                if (!teacherTypeList.contains(teacherTypeStr)) {
                    BeanUtils.copyProperties(excel, errVO);
                    errVO.setErrMsg("第" + i + "条记录，讲师类型不正确");
                    errorList.add(errVO);
                    errNum++;
                    continue;
                }
                // 内部讲师
                if (excel.getTeacherType().equals(1)) {
                    // 判断讲师工号是否为空
                    if (excel.getTeacherNumber() == null || excel.getTeacherNumber().trim().equals("")) {
                        BeanUtils.copyProperties(excel, errVO);
                        errVO.setErrMsg("第" + i + "条记录，讲师工号为空");
                        errorList.add(errVO);
                        errNum++;
                        continue;
                    }
                    // 判断讲师工号是否正确
                    PsPersionVO teacherInfo = iStaffUserInfoService.getUserInfoById(excel.getTeacherNumber());
                    if (teacherInfo == null) {
                        BeanUtils.copyProperties(excel, errVO);
                        errVO.setErrMsg("第" + i + "条记录，讲师工号不正确");
                        errorList.add(errVO);
                        errNum++;
                        continue;
                    }
                    excel.setTeacherName(teacherInfo.getEmplName()); // 讲师姓名
                    if (excel.getTeacherContactPhone() == null || excel.getTeacherContactPhone().trim().equals("")) {
                        excel.setTeacherContactPhone(teacherInfo.getMobile()); // 讲师电话
                    }
                    if (excel.getTeacherEmail() == null || excel.getTeacherEmail().trim().equals("")) {
                        excel.setTeacherEmail(teacherInfo.getEmail()); // 讲师邮箱
                    }
                    if (excel.getTeacherOrganizationName() == null || excel.getTeacherOrganizationName().trim().equals("")) {
                        excel.setTeacherOrganizationName(teacherInfo.getCompanyName()); // 讲师单位
                    }
                    if (excel.getTeacherSex() == null || excel.getTeacherSex().trim().equals("")) {
                        excel.setTeacherSex(teacherInfo.getGender()); // 讲师性别
                    }
                } else {
                    // 外部讲师，判断讲师姓名是否为空
                    if (excel.getTeacherName() == null || excel.getTeacherName().trim().equals("")) {
                        BeanUtils.copyProperties(excel, errVO);
                        errVO.setErrMsg("第" + i + "条记录，讲师姓名为空");
                        errorList.add(errVO);
                        errNum++;
                        continue;
                    }
                }
                // 当讲师性别不为空时，判断所选性别是否正确
                if (excel.getTeacherSex() != null && !excel.getTeacherSex().trim().equals("")
                        && !teacherSexList.contains(excel.getTeacherSex())) {
                    BeanUtils.copyProperties(excel, errVO);
                    errVO.setErrMsg("第" + i + "条记录，讲师性别不正确");
                    errorList.add(errVO);
                    errNum++;
                    continue;
                }
            }
            // 判断是否有重复数据
            String mapKey = excel.getEmplId() + excel.getTrainName() + excel.getTrainShape()
                    + excel.getStartTime() + excel.getEndTime() + excel.getCourseName() + excel.getCourseCategoryName();
            if (checkMap.containsKey(mapKey)) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录与第" + checkMap.get(mapKey) + "条记录，员工工号、项目名称、培训形式、项目开始日期、项目结束日期、课程名称、课程类别等信息重复");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            checkMap.put(mapKey, i);
            // 判断系统中是否已存在此数据
            TrainData existInfo = this.getImportExistInfo(excel, startDate, endDate);
            // 存在
            if (existInfo != null) {
                // 数据已同步
                if (existInfo.getStatus().equals(1)) {
                    BeanUtils.copyProperties(excel, errVO);
                    errVO.setErrMsg("第" + i + "条记录，已存在此数据且已同步，请勿重复导入");
                    errorList.add(errVO);
                    errNum++;
                    continue;
                } else {
                    delIds.add(existInfo.getId()); // 未同步则准备删除
                }
            }
            // 处理数据
            if (errNum == 0) {
                // 获取组织单位(公司)编号
                String companyCode = deptInfo.getCompany();
                // 获取课程类别ID
                String[] courseCateArr = excel.getCourseCategoryName().split("-");
                int courseCateId = Integer.parseInt(courseCateArr[0]);
                // 获取学习方式ID
                String[] learningFormArr = excel.getLearningFormName().split("-");
                int learningFormId = Integer.parseInt(learningFormArr[0]);
                // 经费预算-清除掉货币格式中的逗号
                if (excel.getOutlay() != null && !excel.getOutlay().trim().equals("")) {
                    excel.setOutlay(excel.getOutlay().replace(",", ""));
                }
                // 处理培训历史数据
                TrainData trainData = new TrainData();
                BeanUtils.copyProperties(excel, trainData);
                trainData.setEmplName(emplInfo.getEmplName());
                trainData.setCompanyCode(companyCode);
                trainData.setStartTime(startDate);
                trainData.setEndTime(endDate);
                trainData.setCourseCategory(courseCateId);
                trainData.setCourseStartTime(courseStartDate);
                trainData.setCourseEndTime(courseEndDate);
                trainData.setLearningForm(learningFormId);
                trainData.setType(2);
                trainData.setStatus(0);
                trainData.setCreateUser(loginEmplId);
                trainData.setCreateTime(stamp);
                trainData.setUpdateUser(loginEmplId);
                trainData.setUpdateTime(stamp);
                dataList.add(trainData);
            }
        }
        if (errNum == 0) {
            if (!delIds.isEmpty()) {
                this.deleteByIds(delIds); // 删掉已存在且未同步的重复数据
            }
            this.insertBatchSelective(dataList);
        }
        return errorList;
    }

    /**
     * 批量导入数据是否已存在
     * @param excel
     * @param startDate
     * @param endDate
     * @return
     */
    private TrainData getImportExistInfo(TrainHistoryDataExcel excel, Date startDate, Date endDate) {
        // 获取课程类别ID
        String[] courseCateArr = excel.getCourseCategoryName().split("-");
        int courseCateId = Integer.parseInt(courseCateArr[0]);
        TrainData dataWhere = new TrainData();
        dataWhere.setEmplId(excel.getEmplId());
        dataWhere.setTrainName(excel.getTrainName());
        dataWhere.setTrainShape(excel.getTrainShape());
        dataWhere.setStartTime(startDate);
        dataWhere.setEndTime(endDate);
        dataWhere.setCourseName(excel.getCourseName());
        dataWhere.setCourseCategory(courseCateId);
        dataWhere.setType(2); // 历史数据标识
        return selectOne(dataWhere);
    }

    /**
     * 按职级参训人员人次统计
     * @param mapParams
     * @return
     */
    @Override
    public PosnGradeCostVO countNumByGradeCode(Map<String, Object> mapParams) {
        return trainDataMapper.countNumByGradeCode(mapParams);
    }
}