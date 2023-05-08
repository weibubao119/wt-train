package com.dyys.hr.service.train.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.train.TrainBaseCourseMapper;
import com.dyys.hr.dto.train.FileDTO;
import com.dyys.hr.dto.train.TeacherDTO;
import com.dyys.hr.dto.train.TrainBaseCourseDTO;
import com.dyys.hr.entity.train.TrainBaseCourse;
import com.dyys.hr.entity.train.TrainBaseCourseTeacher;
import com.dyys.hr.entity.train.TrainBaseTeacher;
import com.dyys.hr.entity.train.excel.AbleTeacherExcel;
import com.dyys.hr.entity.train.excel.BaseCourseExcel;
import com.dyys.hr.entity.train.excel.BaseCourseListExcel;
import com.dyys.hr.service.train.TrainBaseCourseService;
import com.dyys.hr.service.train.TrainBaseCourseTeacherService;
import com.dyys.hr.service.train.TrainBaseTeacherService;
import com.dyys.hr.service.train.TrainConstantService;
import com.dyys.hr.vo.train.TrainBaseCourseVO;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class TrainBaseCourseServiceImpl extends AbstractCrudService<TrainBaseCourse, Long> implements TrainBaseCourseService {
    @Autowired
    private TrainBaseCourseMapper trainBaseCourseMapper;
    @Autowired
    private TrainBaseCourseTeacherService trainBaseCourseTeacherService;
    @Autowired
    private TrainConstantService trainConstantService;
    @Autowired
    private TrainBaseTeacherService trainBaseTeacherService;

    @Override
    public PageInfo<TrainBaseCourseVO> pageList(Map<String, Object> params){
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        List<TrainBaseCourseVO> voList = trainBaseCourseMapper.pageList(params);
        return new PageInfo<>(voList);
    }

    /**
     * 导出课程列表
     * @param params
     * @return
     */
    @Override
    public List<BaseCourseListExcel> courseListExport(Map<String, Object> params){
        return trainBaseCourseMapper.courseListExport(params);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(TrainBaseCourseDTO dto,String loginUserId){
        TrainBaseCourse trainBaseCourse = new TrainBaseCourse();
        BeanUtils.copyProperties(dto,trainBaseCourse);
        List<FileDTO> fileList = dto.getFileList();
        trainBaseCourse.setFileList(JSONUtil.toJsonStr(fileList));
        trainBaseCourse.setNumber("C" + System.currentTimeMillis() + RandomStringUtils.randomNumeric(1));
        //默认生效状态
        trainBaseCourse.setStatus(1);
        trainBaseCourse.setCreateUser(loginUserId);
        trainBaseCourse.setCreateTime(System.currentTimeMillis()/1000);
        this.insertSelective(trainBaseCourse);
        Long courseId = trainBaseCourse.getId();
        if(courseId > 0){
            List<TeacherDTO> teacherList = dto.getTeacherList();
            List<TrainBaseCourseTeacher> entityList = new ArrayList<>();
            for (TeacherDTO teacherDTO : teacherList){
                TrainBaseCourseTeacher teacher = new TrainBaseCourseTeacher();
                BeanUtils.copyProperties(teacherDTO,teacher);
                teacher.setCourseId(courseId);
                entityList.add(teacher);
            }
            if(!entityList.isEmpty()){
                trainBaseCourseTeacherService.insertBatchSelective(entityList);
            }
        }
        return courseId;
    }

    @Override
    public TrainBaseCourseDTO selectByCourseId(Long courseId){
        TrainBaseCourse entity = trainBaseCourseMapper.selectByCourseId(courseId);
        TrainBaseCourseDTO dto = new TrainBaseCourseDTO();
        if(entity != null){
            BeanUtils.copyProperties(entity,dto);
            JSONArray objects = JSONUtil.parseArray(entity.getFileList());
            dto.setFileList(JSONUtil.toList(objects,FileDTO.class));
            dto.setTeacherList(trainBaseCourseTeacherService.getSelectByCourseId(courseId));
        }
        return dto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer update(TrainBaseCourseDTO dto,String loginUserId){
        TrainBaseCourse trainBaseCourse = new TrainBaseCourse();
        BeanUtils.copyProperties(dto,trainBaseCourse);
        List<FileDTO> fileList = dto.getFileList();
        trainBaseCourse.setFileList(JSONUtil.toJsonStr(fileList));
        trainBaseCourse.setId(dto.getId());
        trainBaseCourse.setUpdateUser(loginUserId);
        trainBaseCourse.setUpdateTime(System.currentTimeMillis()/1000);
        int updateRes = this.updateSelective(trainBaseCourse);
        if(updateRes > 0 ){
            //删除之前的关联positionCode
            trainBaseCourseTeacherService.deleteByCourseId(dto.getId());
            List<TeacherDTO> teacherList = dto.getTeacherList();
            List<TrainBaseCourseTeacher> entityList = new ArrayList<>();
            for (TeacherDTO teacherDTO : teacherList){
                TrainBaseCourseTeacher teacher = new TrainBaseCourseTeacher();
                BeanUtils.copyProperties(teacherDTO,teacher);
                teacher.setCourseId(dto.getId());
                entityList.add(teacher);
            }
            if(!entityList.isEmpty()){
                trainBaseCourseTeacherService.insertBatchSelective(entityList);
            }
        }
        return updateRes;
    }

    @Override
    public PageInfo<Map<String,Object>> selectList(Map<String, Object> params){
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        List<Map<String,Object>> voList = trainBaseCourseMapper.selectList(params);
        return new PageInfo<>(voList);
    }

    @Override
    public List<TrainBaseCourse> allCourseList(){
        return trainBaseCourseMapper.allCourseList();
    }

    /**
     * 根据课程编码查询可用课程信息
     * @param courseNumber
     * @return
     */
    @Override
    public TrainBaseCourse getInfoByNumber(String courseNumber) {
        TrainBaseCourse courseQuery = new TrainBaseCourse();
        courseQuery.setNumber(courseNumber);
        courseQuery.setIsDelete(0);
        return selectOne(courseQuery);
    }

    /**
     * 导出课程可授课讲师
     * @param params
     * @return
     */
    @Override
    public List<AbleTeacherExcel> ableTeacherList(Map<String, Object> params) {
        return trainBaseCourseMapper.ableTeacherList(params);
    }

    /**
     * excel模板中下拉项
     * @return
     */
    @Override
    public Map<Integer, List<String>> excelSelectMap() {
        Map<Integer, List<String>> selectMap = new HashMap<>();
        // 课程类别下拉项
        List<String> courseCateList = trainConstantService.selectBoxList(1, "0");
        selectMap.put(1, courseCateList);
        // 课程来源下拉项：1外部，2自有
        List<String> sourceList = new ArrayList<>();
        sourceList.add("1");
        sourceList.add("2");
        selectMap.put(2, sourceList);
        return selectMap;
    }

    /**
     * 课程批量导入
     * @param excelList
     * @param loginEmplId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<BaseCourseExcel> importExl(List<BaseCourseExcel> excelList, String loginEmplId) {
        List<String> cateList = trainConstantService.selectBoxList(1, "0");
        List<BaseCourseExcel> errorList = new ArrayList<>();
        Map<String, Integer> checkMap = new HashMap<>();
        Map<String, TrainBaseCourse> courseMap = new HashMap<>();
        Map<String, List<TrainBaseCourseTeacher>> ableTeacherMap = new HashMap<>();
        int i = 0;
        int errNum = 0;
        for (BaseCourseExcel excel : excelList) {
            i++;
            BaseCourseExcel errVO = new BaseCourseExcel();
            // 判断课程名称是否为空
            if (excel.getName() == null || excel.getName().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录的课程名称为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断课程名称是否已存在系统中
            TrainBaseCourseDTO courseDTO = new TrainBaseCourseDTO();
            BeanUtils.copyProperties(excel, courseDTO);
            boolean res = checkUniqueData(courseDTO);
            if (res) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录的课程名称系统已存在");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断课程类别是否为空
            if (excel.getCateName() == null || excel.getCateName().trim().equals("")) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录的课程类别为空");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断课程类别是否正确
            if (!cateList.contains(excel.getCateName())) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录的课程类别选择不正确");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断课程来源是否为空
            if (excel.getCourseSource() == null || (!excel.getCourseSource().equals(1) && !excel.getCourseSource().equals(2))) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录的课程来源选择不正确");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断课程课时数
            if (excel.getClassHours() == null || excel.getClassHours().compareTo(BigDecimal.ZERO) < 1) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录的课时数必须大于0");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            // 判断课程学分
            if (excel.getCredit() == null || excel.getCredit().compareTo(BigDecimal.ZERO) < 1) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录的课程学分必须大于0");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            TrainBaseCourseTeacher courseTeacher = new TrainBaseCourseTeacher();
            // 判断可授课讲师信息
            if ((excel.getTeacherNumber() != null && !excel.getTeacherNumber().trim().equals("") )
                    || (excel.getTeacherName() != null && !excel.getTeacherName().trim().equals(""))) {
                if (excel.getTeacherNumber() == null || excel.getTeacherNumber().trim().equals("")) {
                    BeanUtils.copyProperties(excel, errVO);
                    errVO.setErrMsg("第" + i + "条记录的可授课讲师工号/编号为空");
                    errorList.add(errVO);
                    errNum++;
                    continue;
                }
                if (excel.getTeacherName() == null || excel.getTeacherName().trim().equals("")) {
                    BeanUtils.copyProperties(excel, errVO);
                    errVO.setErrMsg("第" + i + "条记录的可授课讲师姓名为空");
                    errorList.add(errVO);
                    errNum++;
                    continue;
                }
                // 判断可授课讲师信息是否正确
                TrainBaseTeacher teacherInfo = trainBaseTeacherService.queryInfoByNumber(excel.getTeacherNumber());
                if (teacherInfo == null) {
                    BeanUtils.copyProperties(excel, errVO);
                    errVO.setErrMsg("第" + i + "条记录的讲师工号/编号的讲师不存在");
                    errorList.add(errVO);
                    errNum++;
                    continue;
                }
                if (!teacherInfo.getName().equals(excel.getTeacherName())) {
                    BeanUtils.copyProperties(excel, errVO);
                    errVO.setErrMsg("第" + i + "条记录的讲师工号/编号与讲师姓名不一致");
                    errorList.add(errVO);
                    errNum++;
                    continue;
                }
                courseTeacher.setTeacherId(teacherInfo.getId());
                courseTeacher.setNumber(teacherInfo.getNumber());
                courseTeacher.setName(teacherInfo.getName());
                courseTeacher.setOrganizationName(teacherInfo.getOrganizationName());
                courseTeacher.setType(teacherInfo.getType());
            }
            // 判断是否有重复导入数据
            String mapCheckKey = excel.getName() + "-" + excel.getTeacherNumber();
            if (checkMap.containsKey(mapCheckKey)) {
                BeanUtils.copyProperties(excel, errVO);
                errVO.setErrMsg("第" + i + "条记录与第" + checkMap.get(mapCheckKey) + "条记录的课程名称、可授课讲师工号/编码等信息重复");
                errorList.add(errVO);
                errNum++;
                continue;
            }
            checkMap.put(mapCheckKey, i);
            // 处理数据
            if (errNum == 0) {
                String mapCourseKey = excel.getName();
                TrainBaseCourse course = new TrainBaseCourse();
                List<TrainBaseCourseTeacher> teacherList;
                // 处理课程信息
                if (courseMap.containsKey(mapCourseKey)) {
                    // 已存在当前课程map，则取课程对应的可授课讲师
                    teacherList = ableTeacherMap.get(mapCourseKey);
                } else {
                    BeanUtils.copyProperties(excel, course);
                    String[] cateArr = excel.getCateName().split("-"); // 分出课程类别ID和名称
                    course.setNumber("C" + System.currentTimeMillis() + i); // 课程编号
                    course.setCategory(Integer.parseInt(cateArr[0])); // 课程分类
                    courseMap.put(mapCourseKey, course);
                    // 初始化当前课程的可授课讲师
                    teacherList = new ArrayList<>();
                }
                // 处理可授课讲师数据
                if (excel.getTeacherNumber() != null && !excel.getTeacherNumber().trim().equals("")) {
                    teacherList.add(courseTeacher);
                }
                ableTeacherMap.put(mapCourseKey, teacherList);
            }
       }
        if (errNum == 0) {
            this.batchDataSave(courseMap, ableTeacherMap, loginEmplId); // 批量处理课程导入数据
        }
        return errorList;
    }

    /**
     * 批量处理课程导入数据
     * @param courseMap
     * @param ableTeacherMap
     * @param loginEmplId
     */
    private void batchDataSave(Map<String, TrainBaseCourse> courseMap, Map<String, List<TrainBaseCourseTeacher>> ableTeacherMap, String loginEmplId) {
        Long stamp = System.currentTimeMillis()/1000; // 系统当前时间戳
        for (Map.Entry<String, TrainBaseCourse> entry : courseMap.entrySet()) {
            // 保存课程信息
            String key = entry.getKey();
            TrainBaseCourse courseEntity = entry.getValue();
            courseEntity.setIsImport(1); // 批量导入标识
            courseEntity.setIsDelete(0);
            courseEntity.setCreateUser(loginEmplId);
            courseEntity.setCreateTime(stamp);
            courseEntity.setUpdateUser(loginEmplId);
            courseEntity.setUpdateTime(stamp);
            Long courseId = this.insertSelective(courseEntity);
            // 保存课程的可授课讲师
            List<TrainBaseCourseTeacher> teacherList = ableTeacherMap.get(key);
            if (!teacherList.isEmpty()) {
                for (TrainBaseCourseTeacher teacher : teacherList) {
                    teacher.setCourseId(courseId);
                }
                trainBaseCourseTeacherService.insertBatchSelective(teacherList);
            }
        }
    }

    /**
     * 校验课程数据唯一
     * @param dto
     * @return
     */
    @Override
    public Boolean checkUniqueData(TrainBaseCourseDTO dto) {
        Condition condition = new Condition(TrainBaseCourse.class);
        if (dto.getId() == null) {
            condition.createCriteria().andEqualTo("name", dto.getName())
                    .andEqualTo("category", dto.getCategory())
                    .andEqualTo("isDelete", 0);
        } else {
            condition.createCriteria().andNotEqualTo("id", dto.getId())
                    .andEqualTo("name", dto.getName())
                    .andEqualTo("category", dto.getCategory())
                    .andEqualTo("isDelete", 0);
        }
        int res = trainBaseCourseMapper.selectCountByCondition(condition);
        return res > 0;
    }
}