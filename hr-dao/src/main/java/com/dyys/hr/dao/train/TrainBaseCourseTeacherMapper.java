package com.dyys.hr.dao.train;

import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.dto.train.TeacherDTO;
import com.dyys.hr.entity.train.TrainBaseCourseTeacher;
import com.dyys.hr.vo.train.TeacherCanTeachingRecordsVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TrainBaseCourseTeacherMapper extends ICrudMapper<TrainBaseCourseTeacher> {
    List<TeacherDTO> getSelectByCourseId(@Param("courseId") Long courseId);

    void deleteByCourseId(@Param("courseId") Long courseId);

    List<TeacherCanTeachingRecordsVO> canTeachingRecords(Map<String, Object> params);

    List<Map<String,Object>> selectList(Map<String, Object> params);
}