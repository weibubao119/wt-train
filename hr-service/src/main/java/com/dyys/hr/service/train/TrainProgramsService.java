package com.dyys.hr.service.train;

import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.dto.train.TrainProgramsDTO;
import com.dyys.hr.entity.train.TrainPrograms;
import com.dyys.hr.vo.train.*;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface TrainProgramsService extends ICrudService<TrainPrograms, Long> {
    PageInfo<TrainProgramsVO> pageList(Map<String, Object> params);

    Long save(TrainProgramsDTO dto, String loginUserId);

    TrainProgramsDetailVO getDetail(Long programsId);

    Integer update(TrainProgramsDTO dto, String loginUserId);

    /**
     * 结束项目
     * @param programsId
     * @param loginUserId
     * @return
     */
    Integer close(Long programsId,String loginUserId);

    PageInfo<EmployeeProgramsVO> employeePageList(Map<String, Object> params);

    List<StatisticalProportionVo> trainingTypeDistribution(Map<String, Object> params);

    PageInfo<AdminResponsibleProgramsVO> trainingResponsibleList(Map<String, Object> params);

    PageInfo<Map<String,Object>> planCourseList(Map<String, Object> params);

    PageInfo<Map<String,Object>> courseList(Map<String, Object> params);

    /**
     * 年度培训计划执行列表
     * @param params
     * @return
     */
    PageInfo<TrainPlanYearImplementVO> trainPlanYearImplementPageList(Map<String, Object> params);

    /**
     * 学员培训明细列表
     * @param params
     * @return
     */
    PageInfo<StudentTrainingDetailsVO> studentTrainingDetails(Map<String, Object> params);

    /**
     * 课程培训明细报表
     * @param params
     * @return
     */
    PageInfo<CourseTrainingDetailsVO> courseTrainingDetails(Map<String, Object> params);

    /**
     * 讲师课程明细列表
     * @param params
     * @return
     */
    PageInfo<CourseTeacherDetailsVO> courseTeacherDetails(Map<String, Object> params);

    /**
     * 讲师已授课程明细列表
     * @param params
     * @return
     */
    PageInfo<TaughtCourseTeacherDetailsVO> taughtCourseTeacherDetails(Map<String, Object> params);


    /**
     * 培训项目费用明细列表
     * @param params
     * @return
     */
    PageInfo<TrainCostDetailsVO> trainCostDetails(Map<String, Object> params);


    /**
     * 学员培训费用明细列表
     * @param params
     * @return
     */
    PageInfo<StudentTrainCostDetailsVO> studentTrainCostDetails(Map<String, Object> params);
}