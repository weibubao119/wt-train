package com.dyys.hr.service.train.impl;

import cn.hutool.core.convert.Convert;
import com.dagongma.kernel.commons.enums.ResultCode;
import com.dagongma.kernel.commons.exceptions.BusinessException;
import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.train.TrainBaseTeacherMapper;
import com.dyys.hr.dto.train.TrainBaseTeacherDTO;
import com.dyys.hr.dto.train.TrainBaseTeacherProgressDTO;
import com.dyys.hr.entity.train.*;
import com.dyys.hr.entity.train.excel.*;
import com.dyys.hr.service.staff.IStaffJobService;
import com.dyys.hr.service.staff.IStaffUserInfoService;
import com.dyys.hr.service.train.TrainBaseTeacherProgressService;
import com.dyys.hr.service.train.TrainBaseTeacherService;
import com.dyys.hr.service.train.TrainConstantService;
import com.dyys.hr.vo.common.PsPersionVO;
import com.dyys.hr.vo.train.*;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class TrainBaseTeacherServiceImpl extends AbstractCrudService<TrainBaseTeacher, Long> implements TrainBaseTeacherService {
    @Autowired
    private TrainBaseTeacherMapper trainBaseTeacherMapper;
    @Autowired
    private TrainBaseTeacherProgressService trainBaseTeacherProgressService;
    @Autowired
    private TrainConstantService trainConstantService;
    @Autowired
    private IStaffJobService iStaffJobService;
    @Autowired
    private IStaffUserInfoService iStaffUserInfoService;

    @Override
    public PageInfo<TrainBaseTeacherVO> pageList(Map<String, Object> params){
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        List<TrainBaseTeacherVO> voList = trainBaseTeacherMapper.pageList(params);
        return new PageInfo<>(voList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(TrainBaseTeacherDTO dto,String loginUserId){
        //新增讲师
        TrainBaseTeacher teachEntity = new TrainBaseTeacher();
        BeanUtils.copyProperties(dto,teachEntity);
        //校验讲师编号,不重复添加。如果讲师是外部用户,则设置编号
        if(dto.getType() == 2){
            teachEntity.setNumber("T"+System.currentTimeMillis() + RandomStringUtils.randomNumeric(1));
        }
        teachEntity.setIsImport(0);
        teachEntity.setCreateTime(System.currentTimeMillis()/1000);
        teachEntity.setCreateUser(loginUserId);
        Long teacherId = this.insertSelective(teachEntity);
        if(teacherId > 0){
            //新增讲师历程
            List<TrainBaseTeacherProgress> entityList = new ArrayList<>();
            List<TrainBaseTeacherProgressDTO> progressList = dto.getProgressList();
            if(!progressList.isEmpty()){
                Date maxData = progressList.get(0).getStartTime();
                Integer level = progressList.get(0).getLevel();
                for (TrainBaseTeacherProgressDTO  progressDTO : progressList){
                    TrainBaseTeacherProgress progressEntity = new TrainBaseTeacherProgress();
                    BeanUtils.copyProperties(progressDTO,progressEntity);
                    progressEntity.setTeacherId(teacherId);
                    progressEntity.setCreateTime(System.currentTimeMillis()/1000);
                    progressEntity.setCreateUser(loginUserId);
                    entityList.add(progressEntity);
                    //判断最大开始时间,更新讲师等级
                    if(maxData.before(progressDTO.getStartTime())){
                        maxData = progressDTO.getStartTime();
                        level = progressDTO.getLevel();
                    }
                }
                if(!entityList.isEmpty()){
                    trainBaseTeacherProgressService.insertBatchSelective(entityList);
                    TrainBaseTeacher baseTeacher = new TrainBaseTeacher();
                    baseTeacher.setId(teacherId);
                    baseTeacher.setLevel(level);
                    this.updateSelective(baseTeacher);
                }
            }
        }
        return teacherId;
    }

    @Override
    public TrainBaseTeacherDetailVO selectByTeacherId(Long teacherId){
        TrainBaseTeacherDetailVO teacherVO = trainBaseTeacherMapper.selectByTeacherId(teacherId);
        if(teacherVO != null){
            teacherVO.setProgressList(trainBaseTeacherProgressService.getProgressByTeacherId(teacherId));
        }
        return teacherVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer update(TrainBaseTeacherDTO dto,String loginUserId){
        //更新讲师
        TrainBaseTeacher teachEntity = new TrainBaseTeacher();
        BeanUtils.copyProperties(dto,teachEntity);
        teachEntity.setId(dto.getId());
        teachEntity.setUpdateTime(System.currentTimeMillis()/1000);
        teachEntity.setUpdateUser(loginUserId);
        int updateRes = this.updateSelective(teachEntity);
        if(updateRes > 0){
            //删除旧讲师历程
            trainBaseTeacherProgressService.deleteByTeacherId(dto.getId());
            //新增讲师历程
            List<TrainBaseTeacherProgress> entityList = new ArrayList<>();
            List<TrainBaseTeacherProgressDTO> progressList = dto.getProgressList();
            Date maxData = progressList.get(0).getStartTime();
            Integer level = progressList.get(0).getLevel();
            for (TrainBaseTeacherProgressDTO  progressDTO : progressList){
                TrainBaseTeacherProgress progressEntity = new TrainBaseTeacherProgress();
                BeanUtils.copyProperties(progressDTO,progressEntity);
                progressEntity.setTeacherId(dto.getId());
                progressEntity.setCreateTime(System.currentTimeMillis()/1000);
                progressEntity.setCreateUser(loginUserId);
                entityList.add(progressEntity);
                //判断最大开始时间,更新讲师等级
                if(maxData.before(progressDTO.getStartTime())){
                    maxData = progressDTO.getStartTime();
                    level = progressDTO.getLevel();
                }
            }
            if(!entityList.isEmpty()){
                trainBaseTeacherProgressService.insertBatchSelective(entityList);
                TrainBaseTeacher baseTeacher = new TrainBaseTeacher();
                baseTeacher.setId(dto.getId());
                baseTeacher.setLevel(level);
                this.updateSelective(baseTeacher);
            }
        }
        return updateRes;
    }

    @Override
    public PageInfo<Map<String,Object>> selectList(Map<String, Object> params){
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        List<Map<String,Object>> voList = trainBaseTeacherMapper.selectList(params);
        return new PageInfo<>(voList);
    }

    /**
     * 根据讲师编码获取讲师信息
     * @param teacherNumber
     * @return
     */
    @Override
    public TrainBaseTeacher queryInfoByNumber(String teacherNumber) {
        TrainBaseTeacher teacherQuery = new TrainBaseTeacher();
        teacherQuery.setNumber(teacherNumber);
        return this.selectOne(teacherQuery);
    }


    /**
     * 讲师管理列表导出
     * @param params
     * @return
     */
    @Override
    public List<BaseTeacherListExcel> teacherListExport(Map<String, Object> params){
        return trainBaseTeacherMapper.teacherListExport(params);
    }

    /**
     * excel模板中下拉项
     * @return
     */
    @Override
    public Map<Integer, List<String>> excelSelectMap() {
        Map<Integer, List<String>> selectMap = new HashMap<>();
        // 讲师类型下拉项：1内部讲师，2外部讲师
        List<String> teacherTypeList = new ArrayList<>();
        teacherTypeList.add("1");
        teacherTypeList.add("2");
        selectMap.put(0, teacherTypeList);
        // 讲师状态下拉项：1正常，0失效
        List<String> teacherStatusList = new ArrayList<>();
        teacherStatusList.add("1");
        teacherStatusList.add("0");
        selectMap.put(3, teacherStatusList);
        // 讲师性别下拉项：M男，F女
        List<String> sexList = new ArrayList<>();
        sexList.add("M");
        sexList.add("F");
        selectMap.put(4, sexList);
        // 学历-文化程度/背景信息下拉项
        List<String> eduLevelList = iStaffJobService.selectDictBoxList("C_EDCT_BAKG");
        selectMap.put(9, eduLevelList);
        // 成长历程-讲师级别
        List<String> teacherLevelList = trainConstantService.selectBoxList(4, "0");
        selectMap.put(10, teacherLevelList);
        return selectMap;
    }

    /**
     * 讲师批量导入
     * @param excelList
     * @param loginEmplId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<BaseTeacherExcel> importExl(List<BaseTeacherExcel> excelList, String loginEmplId) {
        List<String> eduLevelList = iStaffJobService.selectDictBoxList("C_EDCT_BAKG");
        List<String> teacherLevelList = trainConstantService.selectBoxList(4, "0");
        List<BaseTeacherExcel> errorList = new ArrayList<>();
        Map<String, Integer> checkMap = new HashMap<>();
        Map<String, TrainBaseTeacher> teacherMap = new HashMap<>();
        Map<String, List<TrainBaseTeacherProgress>> teacherPorgressMap = new HashMap<>();
        Map<String, Integer> levelMap = new HashMap<>();
        Map<String, Date> endTimeMap = new HashMap<>();
        int i = 0;
        int errNum = 0;
        for (BaseTeacherExcel excel : excelList) {
            i++;
            BaseTeacherExcel errVO = new BaseTeacherExcel();
            // 判断讲师类型是否正确
            if (excel.getType() == null || (excel.getType() != 1 && excel.getType() != 2)) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录的讲师类型不正确");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断内部讲师工号是否正确
            if (excel.getType() == 1) {
                if (excel.getNumber() == null || excel.getNumber().trim().equals("")) {
                    BeanUtils.copyProperties(excel, errVO);
                    errVO.setErrMsg("第" + i + "条记录的讲师工号为空");
                    errorList.add(errVO);
                    errNum++;
                    continue;
                }
                PsPersionVO userInfo = iStaffUserInfoService.getUserInfoById(excel.getNumber());
                // 判断反馈人工号是否正确
                if (userInfo == null) {
                    BeanUtils.copyProperties(excel, errVO);
                    errVO.setErrMsg("第" + i + "条记录，讲师工号不正确");
                    errorList.add(errVO);
                    errNum++;
                    continue;
                }
                // 判断反馈人工号和姓名是否匹配
                if (!userInfo.getEmplName().equals(excel.getName().trim())) {
                    BeanUtils.copyProperties(excel, errVO);
                    errVO.setErrMsg("第" + i + "条记录，讲师工号和姓名不匹配");
                    errorList.add(errVO);
                    errNum++;
                    continue;
                }
                excel.setName(userInfo.getEmplName());
                excel.setSex(userInfo.getGender());
                excel.setAge(Integer.parseInt(userInfo.getAge()));
                excel.setOrganizationName(userInfo.getCompanyName());
                excel.setContactPhone(userInfo.getMobile());
                excel.setEmail(userInfo.getEmail());
                excel.setEducationLevelName(userInfo.getEdctBakg() + "-" + userInfo.getEducationLevel());
            } else {
                // 判断外部讲师姓名是否为空
                if (excel.getName() == null || excel.getName().trim().equals("")) {
                    BeanUtils.copyProperties(excel, errVO);
                    errVO.setErrMsg("第" + i + "条记录的讲师姓名为空");
                    errorList.add(errVO);
                    errNum++;
                    continue;
                }
                excel.setNumber("");
            }
            // 判断讲师状态是否正确
            if (excel.getStatus() == null || (excel.getStatus() != 1 && excel.getType() != 0)) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录的讲师状态不正确");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            if (excel.getType().equals(2)) {
                // 判断讲师性别是否正确
                if (excel.getSex() != null && !excel.getSex().trim().equals("")
                        && !excel.getSex().equals("M") && !excel.getSex().equals("F")) {
                    BeanUtils.copyProperties(excel, errVO);
                    errVO.setErrMsg("第" + i + "条记录的讲师性别不正确");
                    errorList.add(errVO);
                    errNum++;
                    continue;
                }
                // 判断学历是否正确
                if (excel.getEducationLevelName() != null && !excel.getEducationLevelName().trim().equals("")
                        && !eduLevelList.contains(excel.getEducationLevelName())) {
                    BeanUtils.copyProperties(excel, errVO);
                    errVO.setErrMsg("第" + i + "条记录的文化程度/背景信息不正确");
                    errorList.add(errVO);
                    errNum++;
                    continue;
                }
            }
            // 判断成长历程
            Date startDate = null;
            Date endDate = null;
            if ((excel.getLevelName() != null && !excel.getLevelName().trim().equals(""))
                    || (excel.getStartTime() != null && !excel.getStartTime().trim().equals(""))
                    || (excel.getEndTime() != null && !excel.getEndTime().trim().equals(""))) {
                if (excel.getLevelName() == null || excel.getLevelName().trim().equals("")) {
                    BeanUtils.copyProperties(excel, errVO);
                    errVO.setErrMsg("第" + i + "条记录的成长历程的讲师等级为空");
                    errorList.add(errVO);
                    errNum++;
                    continue;
                }
                if (!teacherLevelList.contains(excel.getLevelName())) {
                    BeanUtils.copyProperties(excel, errVO);
                    errVO.setErrMsg("第" + i + "条记录的成长历程的讲师等级不正确");
                    errorList.add(errVO);
                    errNum++;
                    continue;
                }
                if (excel.getStartTime() == null || excel.getStartTime().trim().equals("")) {
                    BeanUtils.copyProperties(excel, errVO);
                    errVO.setErrMsg("第" + i + "条记录的成长历程的开始日期为空");
                    errorList.add(errVO);
                    errNum++;
                    continue;
                }
                try {
                    SimpleDateFormat sdfStart = new SimpleDateFormat("yyyy/M/d");
                    Date startDateTmp = sdfStart.parse(excel.getStartTime());
                    sdfStart = new SimpleDateFormat("yyyy-MM-dd");
                    String startDateStr = sdfStart.format(startDateTmp);
                    startDate = java.sql.Date.valueOf(startDateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                    throw new BusinessException(ResultCode.EXCEPTION,"第" + i + "条记录，历程的开始日期格式不正确");
                }
                if (excel.getEndTime() == null || excel.getEndTime().trim().equals("")) {
                    BeanUtils.copyProperties(excel, errVO);
                    errVO.setErrMsg("第" + i + "条记录的成长历程的结束日期为空");
                    errorList.add(errVO);
                    errNum++;
                    continue;
                }
                try {
                    SimpleDateFormat sdfEnd = new SimpleDateFormat("yyyy/M/d");
                    Date endDateTmp = sdfEnd.parse(excel.getEndTime());
                    sdfEnd = new SimpleDateFormat("yyyy-MM-dd");
                    String endDateStr = sdfEnd.format(endDateTmp);
                    endDate = java.sql.Date.valueOf(endDateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                    throw new BusinessException(ResultCode.EXCEPTION,"第" + i + "条记录，成长历程的结束日期格式不正确");
                }
                if (!startDate.before(endDate)) {
                    BeanUtils.copyProperties(excel, errVO);
                    errVO.setErrMsg("第" + i + "条记录，成长历程开始日期必须小于结束日期");
                    errorList.add(errVO);
                    errNum++;
                    continue;
                }
            }
            // 判断是否有重复导入数据
            String mapCheckKey = excel.getType() + "-" + excel.getNumber() + "-" + excel.getName()
                    + "-" + excel.getLevelName() + "-" + excel.getStartTime() + "-" + excel.getEndTime();
            if (checkMap.containsKey(mapCheckKey)) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录与第" + checkMap.get(mapCheckKey) + "条记录的讲师类别、工号、姓名和成长历程的等级、开始日期、结束日期等信息重复");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            checkMap.put(mapCheckKey, i);
            // 判断讲师信息是否已存在系统中
            TrainBaseTeacherDTO teacherDTO = new TrainBaseTeacherDTO();
            BeanUtils.copyProperties(excel, teacherDTO);
            boolean res = checkUniqueData(teacherDTO);
            if (res) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录的讲师信息系统已存在");
                errorList.add(errVO);
                errNum++;
                continue;
            }

            // 处理数据
            if (errNum == 0) {
                String mapTeacherKey = excel.getType() + "-" + excel.getNumber() + "-" + excel.getName();
                TrainBaseTeacher teacher = new TrainBaseTeacher();
                TrainBaseTeacherProgress teacherProgress = new TrainBaseTeacherProgress();
                List<TrainBaseTeacherProgress> progressList;
                // 处理讲师信息
                if (teacherMap.containsKey(mapTeacherKey)) {
                    // 已存在当前讲师map，则取对应的成长历程
                    progressList = teacherPorgressMap.get(mapTeacherKey);
                } else {
                    BeanUtils.copyProperties(excel, teacher);
                    if (excel.getType().equals(2)) {
                        // 外部讲师生成讲师编码
                        teacher.setNumber("T"+ System.currentTimeMillis() + i);
                    }
                    if (excel.getEducationLevelName() != null && !excel.getEducationLevelName().trim().equals("")) {
                        String[] eduLevelName = excel.getEducationLevelName().split("-"); // 分出学历编号和名称
                        teacher.setEducationLevel(eduLevelName[0]);
                    }
                    teacherMap.put(mapTeacherKey, teacher);
                    // 初始化当前讲师的成长历程
                    progressList = new ArrayList<>();
                }
                // 成长历程不为空-处理成长历程信息
                if (excel.getLevelName() != null && !excel.getLevelName().trim().equals("")) {
                    String[] levelName = excel.getLevelName().split("-"); // 分出讲师等级ID和名称
                    teacherProgress.setLevel(Integer.parseInt(levelName[0]));
                    teacherProgress.setStartTime(startDate);
                    teacherProgress.setEndTime(endDate);
                    teacherProgress.setFee(excel.getFee());
                    teacherProgress.setInstructions(excel.getInstructions());
                    progressList.add(teacherProgress);

                    // 获取同一个讲师的成长历程中，最新的讲师等级作为讲师信息中的讲师等级
                    if (endTimeMap.containsKey(mapTeacherKey)) {
                        if (endTimeMap.get(mapTeacherKey).before(endDate)) {
                            // 如果原记录的成长历程结束日期小于当前历程记录的结束时间，则更换一下讲师等级
                            levelMap.put(mapTeacherKey, Integer.parseInt(levelName[0]));
                            endTimeMap.put(mapTeacherKey, endDate);
                        }
                    } else {
                        endTimeMap.put(mapTeacherKey, endDate);
                        levelMap.put(mapTeacherKey, Integer.parseInt(levelName[0]));
                    }
                }
                teacherPorgressMap.put(mapTeacherKey, progressList);
            }
        }
        if (errNum == 0) {
            this.batchDataSave(teacherMap, teacherPorgressMap, levelMap, loginEmplId); // 批量数据导入
        }
        return errorList;
    }

    /**
     * excel批量数据导入保存
     * @param teacherMap
     * @param teacherPorgressMap
     * @param levelMap
     * @param loginEmplId
     */
    private void batchDataSave(Map<String, TrainBaseTeacher> teacherMap, Map<String, List<TrainBaseTeacherProgress>> teacherPorgressMap, Map<String, Integer> levelMap, String loginEmplId) {
        Long stamp = System.currentTimeMillis()/1000; // 系统当前时间戳
        for (Map.Entry<String, TrainBaseTeacher> entry : teacherMap.entrySet()) {
            // 保存讲师信息
            String key = entry.getKey();
            TrainBaseTeacher teacherEntity = entry.getValue();
            Integer level = levelMap.get(key);
            teacherEntity.setLevel(level);
            teacherEntity.setIsImport(1);
            teacherEntity.setIsDelete(0);
            teacherEntity.setCreateUser(loginEmplId);
            teacherEntity.setCreateTime(stamp);
            teacherEntity.setUpdateUser(loginEmplId);
            teacherEntity.setUpdateTime(stamp);
            Long teacherId = this.insertSelective(teacherEntity);
            // 保存讲师成长历程信息
            List<TrainBaseTeacherProgress> progressList = teacherPorgressMap.get(key);
            if (!progressList.isEmpty()) {
                for (TrainBaseTeacherProgress progress : progressList) {
                    progress.setTeacherId(teacherId);
                    progress.setCreateUser(loginEmplId);
                    progress.setCreateTime(stamp);
                    progress.setUpdateUser(loginEmplId);
                    progress.setUpdateTime(stamp);
                }
                trainBaseTeacherProgressService.insertBatchSelective(progressList);
            }
        }
    }

    /**
     * 校验教师信息系统唯一
     * @param dto
     * @return
     */
    @Override
    public Boolean checkUniqueData(TrainBaseTeacherDTO dto) {
        Condition condition = new Condition(TrainBaseTeacher.class);
        if (dto.getId() == null) {
            // 内部讲师
            if (dto.getType().equals(1)) {
                condition.createCriteria().andEqualTo("number", dto.getNumber())
                        .andEqualTo("type", dto.getType())
                        .andEqualTo("isDelete", 0);
            } else {
                condition.createCriteria().andEqualTo("name", dto.getName())
                        .andEqualTo("type", dto.getType())
                        .andEqualTo("isDelete", 0);
            }
        } else {
            if (dto.getType().equals(1)) {
                // 内部讲师
                condition.createCriteria().andNotEqualTo("id", dto.getId())
                        .andEqualTo("number", dto.getNumber())
                        .andEqualTo("type", dto.getType())
                        .andEqualTo("isDelete", 0);
            } else {
                // 外部讲师
                condition.createCriteria().andNotEqualTo("id", dto.getId())
                        .andEqualTo("name", dto.getName())
                        .andEqualTo("type", dto.getType())
                        .andEqualTo("isDelete", 0);
            }
        }
        int res = trainBaseTeacherMapper.selectCountByCondition(condition);
        return res > 0;
    }

    /**
     * 导出讲师成长历程数据
     * @param params
     * @return
     */
    @Override
    public List<BaseTeacherProgressExcel> progressListExport(Map<String, Object> params) {
        return trainBaseTeacherMapper.progressListExport(params);
    }

    /**
     * 导出讲师可授课程
     * @param params
     * @return
     */
    @Override
    public List<AbleCourseExcel> ableCourseListExport(Map<String, Object> params) {
        return trainBaseTeacherMapper.ableCourseListExport(params);
    }

    /**
     * 导出讲师已授课程
     * @param params
     * @return
     */
    @Override
    public List<TaughtCourseExcel> taughtCourseListExport(Map<String, Object> params) {
        return trainBaseTeacherMapper.taughtCourseListExport(params);
    }
}