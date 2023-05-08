package com.dyys.hr.dao.train;

import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.train.TrainPrograms;
import com.dyys.hr.vo.train.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TrainProgramsMapper extends ICrudMapper<TrainPrograms> {
    List<TrainProgramsVO> pageList(Map<String, Object> params);

    TrainProgramsDetailVO getDetailByQuery(Map<String, Object> params);

    Integer close(@Param("id") Long programsId);

    List<EmployeeProgramsVO> employeePageList(Map<String, Object> params);

    List<StatisticalProportionVo> trainingTypeDistribution(Map<String, Object> params);

    List<AdminResponsibleProgramsVO> trainingResponsibleList(Map<String, Object> params);

    List<Map<String,Object>> planCourseList(Map<String, Object> params);

    List<Map<String,Object>> courseList(Map<String, Object> params);

    List<TrainPlanYearImplementVO> trainPlanYearImplementPageList(Map<String, Object> params);

    List<StudentTrainingDetailsVO> studentTrainingDetails(Map<String, Object> params);

    List<CourseTrainingDetailsVO> courseTrainingDetails(Map<String, Object> params);

    List<CourseTeacherDetailsVO> courseTeacherDetails(Map<String, Object> params);

    List<TaughtCourseTeacherDetailsVO> taughtCourseTeacherDetails(Map<String, Object> params);

    List<TrainCostDetailsVO> trainCostDetails(Map<String, Object> params);

    List<StudentTrainCostDetailsVO> studentTrainCostDetails(Map<String, Object> params);
}