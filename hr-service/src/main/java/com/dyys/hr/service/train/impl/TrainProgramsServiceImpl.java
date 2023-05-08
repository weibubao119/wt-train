package com.dyys.hr.service.train.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.dagongma.kernel.commons.enums.ResultCode;
import com.dagongma.kernel.commons.exceptions.BusinessException;
import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dagongma.redis.common.util.RedisUtil;
import com.dyys.hr.dao.train.*;
import com.dyys.hr.dto.train.*;
import com.dyys.hr.entity.train.*;
import com.dyys.hr.service.train.*;
import com.dyys.hr.vo.common.OrgDeptVO;
import com.dyys.hr.vo.train.*;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
@Slf4j
public class TrainProgramsServiceImpl extends AbstractCrudService<TrainPrograms, Long> implements TrainProgramsService {
    @Autowired
    private TrainProgramsPlanService trainProgramsPlanService;
    @Autowired
    private TrainProgramsTeacherService trainProgramsTeacherService;
    @Autowired
    private TrainProgramsParticipantsService trainProgramsParticipantsService;
    @Autowired
    private TrainProgramsMapper trainProgramsMapper;
    @Autowired
    private TrainProgramsPlanMapper trainProgramsPlanMapper;
    @Autowired
    private TrainProgramsTeacherMapper trainProgramsTeacherMapper;
    @Autowired
    private TrainProgramsParticipantsMapper trainProgramsParticipantsMapper;
    @Autowired
    private TrainProgramsCourseService trainProgramsCourseService;
    @Autowired
    private TrainProgramsCourseMapper trainProgramsCourseMapper;
    @Autowired
    private TrainBaseCourseService trainBaseCourseService;
    @Autowired
    private TrainBaseTeacherService trainBaseTeacherService;
    @Autowired
    private TrainDataService trainDataService;
    @Autowired
    private TrainTraineeSummaryService trainTraineeSummaryService;
    @Autowired
    private TrainExaminerService trainExaminerService;
    @Autowired
    private TrainBaseSiteService trainBaseSiteService;
    @Autowired
    private TrainBaseTeacherTaughtService trainBaseTeacherTaughtService;
    @Autowired
    private TrainAppraiseService trainAppraiseService;
    @Autowired
    private TrainAttendanceRulesService trainAttendanceRulesService;
    @Autowired
    private TrainAttendanceRulesMapper trainAttendanceRulesMapper;
    @Autowired
    private TrainAttendanceRecordMapper trainAttendanceRecordMapper;
    @Autowired
    private TrainCostService trainCostService;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public PageInfo<TrainProgramsVO> pageList(Map<String, Object> params){
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        List<TrainProgramsVO> voList = trainProgramsMapper.pageList(params);
        for (TrainProgramsVO vo: voList){
            TrainPrograms trainPrograms = this.selectById(vo.getId());
            JSONArray objects = JSONUtil.parseArray(trainPrograms.getPrincipalList());
            vo.setPrincipalList(JSONUtil.toList(objects,PersonDTO.class));
        }
        return new PageInfo<>(voList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(TrainProgramsDTO dto, String loginUserId){
        //新增项目
        TrainPrograms programsEntity = new TrainPrograms();
        BeanUtils.copyProperties(dto,programsEntity);
        List<OrgDeptVO> coOrganizer = dto.getCoOrganizer();
        programsEntity.setCoOrganizer(JSONUtil.toJsonStr(coOrganizer));
        List<PersonDTO> principalList = dto.getPrincipalList();
        programsEntity.setNumber("P"+ System.currentTimeMillis());
        programsEntity.setStatus(1);
        programsEntity.setIsImport(0);
        programsEntity.setPrincipalList(JSONUtil.toJsonStr(principalList));
        programsEntity.setCreateUser(loginUserId);
        programsEntity.setCreateTime(System.currentTimeMillis()/1000);
        Long programsId = this.insertSelective(programsEntity);
        //插入项目计划
        //校验培训计划课程不能重复
        List<TrainProgramsPlanDTO> planDTOS = dto.getProgramsPlanList();
        List<Long> courseIdsList = new ArrayList<>();
        if(planDTOS != null && !planDTOS.isEmpty()){
            List<TrainProgramsPlan> planList = new ArrayList<>();
            for (TrainProgramsPlanDTO  planDTO : planDTOS){
                if(courseIdsList.contains(planDTO.getCourseId())){
                    throw new BusinessException(ResultCode.EXCEPTION,"培训计划课程不能重复");
                }else {
                    courseIdsList.add(planDTO.getCourseId());
                }
                TrainProgramsPlan planEntity = new TrainProgramsPlan();
                BeanUtils.copyProperties(planDTO,planEntity);
                planEntity.setProgramsId(programsId);
                planEntity.setIsImport(0);
                planEntity.setCreateUser(loginUserId);
                planEntity.setCreateTime(System.currentTimeMillis()/1000);
                planList.add(planEntity);
            }
            trainProgramsPlanService.insertBatchSelective(planList);
        }

        //插入项目课表
        List<TrainProgramsCourseDTO> courseDTOS = dto.getProgramsCourseList();
        if(courseDTOS != null && !courseDTOS.isEmpty()){
            List<TrainProgramsCourse> courseList = new ArrayList<>();
            //构造场地时间段数据结构
            Map<Integer, List<String>> siteTimeMap = new HashMap<>();
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
            for (TrainProgramsCourseDTO  courseDTO : courseDTOS){
                Integer siteId = courseDTO.getSiteId();
                String schoolTime = simpleDateFormat.format(courseDTO.getSchooltime());
                String timeString = schoolTime + " " + courseDTO.getStartTime() + "|" + schoolTime + " " + courseDTO.getEndTime();
                if(siteTimeMap.get(siteId) == null){
                    List<String> timeList = new ArrayList<>();
                    timeList.add(timeString);
                    siteTimeMap.put(siteId,timeList);
                }
                else{
                    siteTimeMap.get(siteId).add(timeString);
                }
            }
            //循环校验场地的时间不重复性
            for(Integer siteId : siteTimeMap.keySet()){
                List<String> list = siteTimeMap.get(siteId);
                String siteName = trainBaseSiteService.selectBySiteId(siteId).getSiteName();
                try {
                    DateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    for (int i = 0; i < list.size(); i++) {
                        String[] d1 = list.get(i).split("\\|");
                        Date startdate1 = formater.parse(d1[0]);
                        Date enddate1 = formater.parse(d1[1]);
                        for (int j = i + 1; j < list.size(); j++) {
                            String[] d2 = list.get(j).split("\\|");
                            Date startdate2 = formater.parse(d2[0]);
                            Date enddate2 = formater.parse(d2[1]);
                            if (!startdate2.before(startdate1)) {
                                if (!startdate2.after(enddate1)) {
                                    throw new BusinessException(ResultCode.EXCEPTION,"培训课表"+ siteName + "场地使用时间存在重叠,请排查");
                                }
                            } else if (!enddate2.before(startdate1)) {
                                throw new BusinessException(ResultCode.EXCEPTION,"培训课表"+ siteName + "场地使用时间存在重叠,请排查");
                            }
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                    throw new BusinessException(ResultCode.EXCEPTION,"培训课表场地校验异常");
                }
            }

            for (TrainProgramsCourseDTO  courseDTO : courseDTOS){
                TrainProgramsCourse courseEntity = new TrainProgramsCourse();
                BeanUtils.copyProperties(courseDTO,courseEntity);
                courseEntity.setProgramsId(programsId);
                courseEntity.setCreateUser(loginUserId);
                courseEntity.setCreateTime(System.currentTimeMillis()/1000);
                courseList.add(courseEntity);
            }
            trainProgramsCourseService.insertBatchSelective(courseList);
        }

        //插入项目讲师
        List<TrainProgramsTeacherDTO> teacherDTOS = dto.getProgramsTeacherList();
        if(teacherDTOS != null && !teacherDTOS.isEmpty()){
            List<TrainProgramsTeacher> teacherList = new ArrayList<>();
            for (TrainProgramsTeacherDTO  teacherDTO : teacherDTOS){
                TrainProgramsTeacher teacherEntity = new TrainProgramsTeacher();
                BeanUtils.copyProperties(teacherDTO,teacherEntity);
                teacherEntity.setProgramsId(programsId);
                teacherEntity.setCreateUser(loginUserId);
                teacherEntity.setCreateTime(System.currentTimeMillis()/1000);
                teacherList.add(teacherEntity);
            }
            trainProgramsTeacherService.insertBatchSelective(teacherList);
        }

        //插入项目参训人员
        List<TrainProgramsParticipantsDTO> participantsDTOS = dto.getProgramsParticipantsList();
        if(participantsDTOS != null && !participantsDTOS.isEmpty()){
            List<TrainProgramsParticipants> participantsList = new ArrayList<>();
            HashMap<String, Object> map = new HashMap<>();
            for (TrainProgramsParticipantsDTO participantsDTO : participantsDTOS){
                if(map.get(participantsDTO.getUserId()) == null){
                    map.put(participantsDTO.getUserId(),1);
                    TrainProgramsParticipants participantsEntity = new TrainProgramsParticipants();
                    BeanUtils.copyProperties(participantsDTO,participantsEntity);
                    participantsEntity.setProgramsId(programsId);
                    participantsEntity.setStatus(2);
                    participantsEntity.setIsImport(0);
                    participantsEntity.setCreateUser(loginUserId);
                    participantsEntity.setCreateTime(System.currentTimeMillis()/1000);
                    participantsList.add(participantsEntity);
                }
            }
            trainProgramsParticipantsService.insertBatchSelective(participantsList);
        }

        //redis插入项目状态处理数据
        redisUtil.set(programsId.toString(),0);

        return programsId;
    }

    @Override
    public TrainProgramsDetailVO getDetail(Long programsId){
        Map<String, Object> params = new HashMap<>();
        params.put("id",programsId);
        TrainProgramsDetailVO programsDetailVO = trainProgramsMapper.getDetailByQuery(params);
        if(programsDetailVO != null){
            TrainPrograms entity = this.selectById(programsId);
            BeanUtils.copyProperties(entity,programsDetailVO);
            JSONArray objects = JSONUtil.parseArray(entity.getCoOrganizer());
            programsDetailVO.setCoOrganizer(JSONUtil.toList(objects, OrgDeptVO.class));
            objects = JSONUtil.parseArray(entity.getPrincipalList());
            programsDetailVO.setPrincipalList(JSONUtil.toList(objects,PersonDTO.class));

            Map<String, Object> query = new HashMap<>();
            query.put("programs_id",programsId);
            //项目计划
            programsDetailVO.setProgramsPlanList(trainProgramsPlanMapper.getDetailList(query));
            //项目课表
            programsDetailVO.setProgramsCourseList(trainProgramsCourseMapper.getDetailList(query));
            //项目讲师
            programsDetailVO.setProgramsTeacherList(trainProgramsTeacherMapper.getDetailList(query));
            //项目参训人员
            programsDetailVO.setProgramsParticipantsList(trainProgramsParticipantsMapper.getDetailList(query));
        }
        return programsDetailVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer update(TrainProgramsDTO dto, String loginUserId){
        //新增项目
        TrainPrograms programsEntity = new TrainPrograms();
        BeanUtils.copyProperties(dto,programsEntity);
        List<OrgDeptVO> coOrganizer = dto.getCoOrganizer();
        programsEntity.setCoOrganizer(JSONUtil.toJsonStr(coOrganizer));
        List<PersonDTO> principalList = dto.getPrincipalList();
        programsEntity.setPrincipalList(JSONUtil.toJsonStr(principalList));
        programsEntity.setUpdateUser(loginUserId);
        programsEntity.setUpdateTime(System.currentTimeMillis()/1000);
        Integer updateResult = this.updateSelective(programsEntity);

        Long programsId = programsEntity.getId();
        Map<String, Object> params = new HashMap<>();
        params.put("programs_id",programsId);
        //清空旧数据,插入项目计划
        trainProgramsPlanService.deleteByParams(params);
        List<TrainProgramsPlanDTO> planDTOS = dto.getProgramsPlanList();
        if(planDTOS != null && !planDTOS.isEmpty()){
            List<TrainProgramsPlan> planList = new ArrayList<>();
            List<Long> courseIdsList = new ArrayList<>();
            for (TrainProgramsPlanDTO  planDTO : planDTOS){
                if(courseIdsList.contains(planDTO.getCourseId())){
                    throw new BusinessException(ResultCode.EXCEPTION,"培训计划课程不能重复");
                }else {
                    courseIdsList.add(planDTO.getCourseId());
                }
                TrainProgramsPlan planEntity = new TrainProgramsPlan();
                BeanUtils.copyProperties(planDTO,planEntity);
                planEntity.setProgramsId(programsId);
                planEntity.setIsImport(0);
                planEntity.setCreateUser(loginUserId);
                planEntity.setCreateTime(System.currentTimeMillis()/1000);
                planList.add(planEntity);
            }
            trainProgramsPlanService.insertBatchSelective(planList);
        }
        //清空旧数据,插入项目课表
        trainProgramsCourseMapper.deleteByParams(params);
        List<TrainProgramsCourseDTO> courseDTOS = dto.getProgramsCourseList();
        if(courseDTOS != null && !courseDTOS.isEmpty()){
            List<TrainProgramsCourse> courseList = new ArrayList<>();
            //构造场地时间段数据结构
            Map<Integer, List<String>> siteTimeMap = new HashMap<>();
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
            for (TrainProgramsCourseDTO  courseDTO : courseDTOS){
                Integer siteId = courseDTO.getSiteId();
                String schoolTime = simpleDateFormat.format(courseDTO.getSchooltime());
                String timeString = schoolTime + " " + courseDTO.getStartTime() + "|" + schoolTime + " " + courseDTO.getEndTime();
                if(siteTimeMap.get(siteId) == null){
                    List<String> timeList = new ArrayList<>();
                    timeList.add(timeString);
                    siteTimeMap.put(siteId,timeList);
                }
                else{
                    siteTimeMap.get(siteId).add(timeString);
                }
            }
            //循环校验场地的时间不重复性
            for(Integer siteId : siteTimeMap.keySet()){
                List<String> list = siteTimeMap.get(siteId);
                String siteName = trainBaseSiteService.selectBySiteId(siteId).getSiteName();
                try {
                    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    for (int i = 0; i < list.size(); i++) {
                        String[] d1 = list.get(i).split("\\|");
                        Date startdate1 = formatter.parse(d1[0]);
                        Date enddate1 = formatter.parse(d1[1]);
                        for (int j = i + 1; j < list.size(); j++) {
                            String[] d2 = list.get(j).split("\\|");
                            Date startdate2 = formatter.parse(d2[0]);
                            Date enddate2 = formatter.parse(d2[1]);
                            if (!startdate2.before(startdate1)) {
                                if (!startdate2.after(enddate1)) {
                                    throw new BusinessException(ResultCode.EXCEPTION,"培训课表"+ siteName + "场地使用时间存在重叠,请排查");
                                }
                            } else if (!enddate2.before(startdate1)) {
                                throw new BusinessException(ResultCode.EXCEPTION,"培训课表"+ siteName + "场地使用时间存在重叠,请排查");
                            }
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                    throw new BusinessException(ResultCode.EXCEPTION,"培训课表场地校验异常");
                }
            }

            for (TrainProgramsCourseDTO  courseDTO : courseDTOS){
                TrainProgramsCourse courseEntity = new TrainProgramsCourse();
                BeanUtils.copyProperties(courseDTO,courseEntity);
                courseEntity.setProgramsId(programsId);
                courseEntity.setCreateUser(loginUserId);
                courseEntity.setCreateTime(System.currentTimeMillis()/1000);
                courseList.add(courseEntity);
            }
            trainProgramsCourseService.insertBatchSelective(courseList);
        }

        //清空旧数据,插入项目讲师
        trainProgramsTeacherMapper.deleteByParams(params);
        List<TrainProgramsTeacherDTO> teacherDTOS = dto.getProgramsTeacherList();
        if(teacherDTOS != null && !teacherDTOS.isEmpty()){
            List<TrainProgramsTeacher> teacherList = new ArrayList<>();
            for (TrainProgramsTeacherDTO  teacherDTO : teacherDTOS){
                TrainProgramsTeacher teacherEntity = new TrainProgramsTeacher();
                BeanUtils.copyProperties(teacherDTO,teacherEntity);
                teacherEntity.setProgramsId(programsId);
                teacherEntity.setCreateUser(loginUserId);
                teacherEntity.setCreateTime(System.currentTimeMillis()/1000);
                teacherList.add(teacherEntity);
            }
            trainProgramsTeacherService.insertBatchSelective(teacherList);
        }
        return updateResult;
    }

    /**
     * 结束项目
     * @param programsId
     * @param loginUserId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer close(Long programsId,String loginUserId){
        Date currTime = new Date(); // 当前时间
        Long stamp = System.currentTimeMillis() / 1000; // 当前时间戳
        //处理培训相关所有数据到数据集合表
        TrainProgramsDetailVO trainPrograms = this.getDetail(programsId);
        //获取培训计划所有的课程,循环确认参训人员,查找对应字段数据构造插入
        List<TrainProgramsPlanDetailVO> planList = trainPrograms.getProgramsPlanList();
        List<TrainProgramsParticipantsDetailVO> participantsList = trainPrograms.getProgramsParticipantsList();
        int applyNum = trainProgramsParticipantsService.getApplyNum(programsId); // 已报名参训人数-实际参与人数
        Float trainFee = trainCostService.getTotalFeeByProgramsId(programsId); // 获取项目培训总费用
        List<TrainData> dataList = new ArrayList<>();
        for (TrainProgramsPlanDetailVO plan : planList){
            for (TrainProgramsParticipantsDetailVO participants : participantsList){
                // 已确认报名的参训人员
                if(participants.getStatus() == 1){
                    TrainData trainData = new TrainData();
                    //培训项目数据
                    trainData.setEmplId(participants.getUserId());
                    trainData.setEmplName(participants.getUserName());
                    trainData.setTrainNumber(trainPrograms.getNumber());
                    trainData.setTrainName(trainPrograms.getTrainName());
                    trainData.setCompanyCode(trainPrograms.getCompanyCode());
                    trainData.setDeptId(trainPrograms.getDeptId());
                    trainData.setTrainShape(trainPrograms.getTrainShape());
                    trainData.setStartTime(trainPrograms.getStartTime());
                    trainData.setEndTime(trainPrograms.getEndTime());
                    trainData.setParticipantsNum(applyNum);
                    if(trainFee != null){
                        trainData.setOutlay(trainFee.toString());
                        if (applyNum > 0) {
                            BigDecimal avgFee = BigDecimal.valueOf(trainFee).divide(BigDecimal.valueOf(applyNum), 2, BigDecimal.ROUND_HALF_UP); // 参训人员平均费用
                            trainData.setAvgFee(avgFee.toString());
                        }
                    }
                    if(!trainPrograms.getPrincipalList().isEmpty()){
                        trainData.setPrincipalId(trainPrograms.getPrincipalList().get(0).getEmplId());
                        trainData.setPrincipalName(trainPrograms.getPrincipalList().get(0).getEmplName());
                    }
                    //查找参训人员的项目总成绩,是否获证 => 培训总结表,员工证书表
                    TrainTraineeSummary summaryQuery = new TrainTraineeSummary();
                    summaryQuery.setProgramsId(programsId);
                    summaryQuery.setUserId(participants.getUserId());
                    TrainTraineeSummary traineeSummary = trainTraineeSummaryService.selectOne(summaryQuery);
                    if(traineeSummary != null){
                        trainData.setTotalScore(traineeSummary.getTotalScore());
                        trainData.setIsObtain(traineeSummary.getIsObtain());
                    }
                    //培训课程数据
                    TrainBaseCourse trainBaseCourse = trainBaseCourseService.selectById(plan.getCourseId());
                    trainData.setCourseNumber(trainBaseCourse.getNumber());
                    trainData.setCourseName(plan.getCourseName());
                    trainData.setCourseCategory(Integer.parseInt(plan.getCategory().toString()));
                    if(plan.getClassHour() != null){
                        trainData.setCourseClassHours(plan.getClassHour());
                    }
                    trainData.setCourseInstructions(trainBaseCourse.getInstructions());
                    trainData.setCourseCredit(trainBaseCourse.getCredit());
                    trainData.setCourseStartTime(plan.getStartTime());
                    trainData.setCourseEndTime(plan.getEndTime());
                    trainData.setLearningForm(plan.getLearningForm());
                    trainData.setExamineForm(plan.getExamineForm());
                    // 取员工在某个培训项目某个课程的最后一次考试成绩
                    TrainExaminer trainExaminer = trainExaminerService.getFinalExamResults(participants.getUserId(), programsId, plan.getCourseId());
                    if (trainExaminer != null) {
                        // 考试通过
                        if (trainExaminer.getStatus().equals(1)) {
                            trainData.setIsPass(1); // 考试通过
                            trainData.setCredit(trainBaseCourse.getCredit()); // 获得课程学分
                            trainData.setGetCreditTime(currTime); // 获得学分时间
                        } else {
                            trainData.setIsPass(0); // 考试未通过
                        }
                        if(trainExaminer.getHighestScore() != null){
                            trainData.setExamScore(trainExaminer.getHighestScore());
                            trainData.setCourseScore(trainExaminer.getHighestScore());
                        }
                    }
                    //培训讲师
                    //获取课表对应的首个讲师
                    TrainProgramsCourse programsCourseQuery = new TrainProgramsCourse();
                    programsCourseQuery.setProgramsId(programsId);
                    programsCourseQuery.setCourseId(plan.getCourseId());
                    TrainProgramsCourse trainProgramsCourse = trainProgramsCourseService.selectOne(programsCourseQuery);
                    if(trainProgramsCourse != null){
                        TrainBaseTeacher baseTeacher = trainBaseTeacherService.selectById(trainProgramsCourse.getTeacherId());
                        trainData.setTeacherNumber(baseTeacher.getNumber());
                        trainData.setTeacherName(baseTeacher.getName());
                        trainData.setTeacherType(baseTeacher.getType());
                        trainData.setTeacherSex(baseTeacher.getSex());
                        trainData.setTeacherContactPhone(baseTeacher.getContactPhone());
                        trainData.setTeacherEmail(baseTeacher.getEmail());
                        trainData.setTeacherOrganizationName(baseTeacher.getOrganizationName());
                    }

                    trainData.setType(3);
                    trainData.setStatus(1);
                    trainData.setCreateTime(stamp);
                    trainData.setCreateUser(loginUserId);
                    dataList.add(trainData);
                }
            }
            if(!dataList.isEmpty()){
                trainDataService.insertBatchSelective(dataList);
            }
        }
        // 处理讲师已授课程
        List<TrainProgramsCourse> teacherCourseList = trainProgramsCourseService.teacherCourseList(programsId);
        List<TrainBaseTeacherTaught> taughtList = new ArrayList<>();
        for (TrainProgramsCourse pc : teacherCourseList) {
            // 课程培训计划信息
            TrainProgramsPlan plan = trainProgramsPlanService.getPlanInfo(programsId, pc.getCourseId(), 0);
            if(plan == null) continue;
            // 获取当前课程的最后一次评估得分记录
            TrainAppraise appraiseCourse = trainAppraiseService.getFinalInfo(programsId, pc.getCourseId(), 1);
            // 获取当前课程的讲师最后一次评估得分记录
            TrainAppraise appraiseTeacher = trainAppraiseService.getFinalInfo(programsId, pc.getCourseId(), 2);
            TrainBaseTeacherTaught taught = new TrainBaseTeacherTaught();
            taught.setTeacherId(pc.getTeacherId());
            taught.setProgramsId(programsId);
            taught.setCourseId(pc.getCourseId());
            taught.setParticipantsTotal(applyNum);
            taught.setCourseStartTime(plan.getStartTime());
            taught.setCourseEndTime(plan.getEndTime());
            taught.setLearningForm(plan.getLearningForm());
            taught.setExamineForm(plan.getExamineForm());
            taught.setInstitutionId(plan.getInstitutionId());
            taught.setTrainFee(plan.getOutlay());
            if(appraiseCourse != null){
                taught.setCourseScore(appraiseCourse.getCourseScore());
            }
            if(appraiseTeacher != null){
                taught.setTeacherScore(appraiseTeacher.getTeacherScore());
            }
            taught.setCreateUser(loginUserId);
            taught.setCreateTime(stamp);
            taughtList.add(taught);
        }
        if (!taughtList.isEmpty()) {
            trainBaseTeacherTaughtService.insertBatchSelective(taughtList);
        }

        //处理考勤数据
        Map<String, Object> map = new HashMap<>();
        map.put("programsId",programsId);
        List<TrainAttendanceRulesVO> rulesList = trainAttendanceRulesMapper.pageList(map);
        for (TrainAttendanceRulesVO rules : rulesList){
            TrainAttendanceRecord recordUpdate = new TrainAttendanceRecord();
            recordUpdate.setAttendanceRulesId(rules.getId());
            recordUpdate.setStatus(0);
            //未签到人数
            int absentNum = trainAttendanceRecordMapper.selectCount(recordUpdate);
            //出勤人数
            recordUpdate.setStatus(1);
            int attendanceNum = trainAttendanceRecordMapper.selectCount(recordUpdate);
            //迟到人数
            recordUpdate.setStatus(2);
            int lateNum = trainAttendanceRecordMapper.selectCount(recordUpdate);

            TrainAttendanceRules attendanceRules = new TrainAttendanceRules();
            attendanceRules.setId(rules.getId());
            attendanceRules.setAbsentNum(absentNum);
            attendanceRules.setAttendanceNum(attendanceNum);
            attendanceRules.setLateNum(lateNum);
            attendanceRules.setUpdateUser(loginUserId);
            attendanceRules.setUpdateTime(stamp);
            trainAttendanceRulesService.updateSelective(attendanceRules);
        }

        return trainProgramsMapper.close(programsId);
    }

    @Override
    public PageInfo<EmployeeProgramsVO> employeePageList(Map<String, Object> params){
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        List<EmployeeProgramsVO> voList = trainProgramsMapper.employeePageList(params);
        return new PageInfo<>(voList);
    }

    @Override
    public List<StatisticalProportionVo> trainingTypeDistribution(Map<String, Object> params){
        List<StatisticalProportionVo>  voList = trainProgramsMapper.trainingTypeDistribution(params);
        if(voList != null && !voList.isEmpty()){
            int total = voList.stream().mapToInt(StatisticalProportionVo::getNum).sum();
            for (StatisticalProportionVo vo:voList){
                vo.setRate((int)Math.round((Double.parseDouble(vo.getNum().toString()) / total*100)) + "");
            }
        }
        return voList;
    }

    @Override
    public PageInfo<AdminResponsibleProgramsVO> trainingResponsibleList(Map<String, Object> params){
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        List<AdminResponsibleProgramsVO> voList = trainProgramsMapper.trainingResponsibleList(params);
        return new PageInfo<>(voList);
    }

    @Override
    public PageInfo<Map<String,Object>> planCourseList(Map<String, Object> params){
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        List<Map<String,Object>> voList = trainProgramsMapper.planCourseList(params);
        return new PageInfo<>(voList);
    }

    @Override
    public PageInfo<Map<String,Object>> courseList(Map<String, Object> params){
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        List<Map<String,Object>> voList = trainProgramsMapper.courseList(params);
        return new PageInfo<>(voList);
    }

    /**
     * 年度培训计划执行列表分页
     * @param params
     * @return
     */
    @Override
    public PageInfo<TrainPlanYearImplementVO> trainPlanYearImplementPageList(Map<String, Object> params){
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        List<TrainPlanYearImplementVO> voList = trainProgramsMapper.trainPlanYearImplementPageList(params);
        for (TrainPlanYearImplementVO vo :voList){
            if(vo.getOutlay() != null && vo.getActualCost() != null && vo.getOutlay().compareTo(BigDecimal.ZERO) != 0){
                //计算费用执行率
                int rate = NumberUtil.div(vo.getActualCost(), vo.getOutlay(), 2).multiply(BigDecimal.valueOf(100)).intValue();
                vo.setCostRate(rate + "%");
            }
        }
        return new PageInfo<>(voList);
    }

    /**
     * 学员培训明细列表
     * @param params
     * @return
     */
    @Override
    public PageInfo<StudentTrainingDetailsVO> studentTrainingDetails(Map<String, Object> params){
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        List<StudentTrainingDetailsVO> voList = trainProgramsMapper.studentTrainingDetails(params);
        return new PageInfo<>(voList);
    }

    /**
     * 课程培训明细报表
     * @param params
     * @return
     */
    @Override
    public PageInfo<CourseTrainingDetailsVO> courseTrainingDetails(Map<String, Object> params){
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        List<CourseTrainingDetailsVO> voList = trainProgramsMapper.courseTrainingDetails(params);
        return new PageInfo<>(voList);
    }

    /**
     * 讲师课程明细列表
     * @param params
     * @return
     */
    @Override
    public  PageInfo<CourseTeacherDetailsVO> courseTeacherDetails(Map<String, Object> params){
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        List<CourseTeacherDetailsVO> voList = trainProgramsMapper.courseTeacherDetails(params);
        return new PageInfo<>(voList);
    }

    /**
     * 讲师已授课程明细列表
     * @param params
     * @return
     */
    @Override
    public PageInfo<TaughtCourseTeacherDetailsVO> taughtCourseTeacherDetails(Map<String, Object> params){
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        List<TaughtCourseTeacherDetailsVO> voList = trainProgramsMapper.taughtCourseTeacherDetails(params);
        return new PageInfo<>(voList);
    }


    /**
     * 培训项目费用明细列表
     * @param params
     * @return
     */
    @Override
    public PageInfo<TrainCostDetailsVO> trainCostDetails(Map<String, Object> params){
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        List<TrainCostDetailsVO> voList = trainProgramsMapper.trainCostDetails(params);
        for (TrainCostDetailsVO vo : voList){
            vo.setSubjectAmountList(trainCostService.subjectAmountList(vo.getProgramsId()));
        }
        return new PageInfo<>(voList);
    }


    /**
     * studentTrainCostDetails
     * @param params
     * @return
     */
    @Override
    public PageInfo<StudentTrainCostDetailsVO> studentTrainCostDetails(Map<String, Object> params){
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        List<StudentTrainCostDetailsVO> voList = trainProgramsMapper.studentTrainCostDetails(params);
        return new PageInfo<>(voList);
    }
}