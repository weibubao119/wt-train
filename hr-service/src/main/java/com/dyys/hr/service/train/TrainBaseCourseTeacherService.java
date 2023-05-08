package com.dyys.hr.service.train;

import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.dto.train.TeacherDTO;
import com.dyys.hr.entity.train.TrainBaseCourseTeacher;
import com.dyys.hr.vo.train.TeacherCanTeachingRecordsVO;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface TrainBaseCourseTeacherService extends ICrudService<TrainBaseCourseTeacher, Long> {
    /**
     * 课程授课讲师列表
     * @param courseId
     * @return
     */
    List<TeacherDTO> getSelectByCourseId(Long courseId);

    /**
     * 删除课程授课讲师
     * @param courseId
     */
    void deleteByCourseId(Long courseId);

    /**
     * 讲师可授课记录
     * @param params
     * @return
     */
    List<TeacherCanTeachingRecordsVO> canTeachingRecords(Map<String, Object> params);


    /**
     * 授课讲师下拉api
     * @param params
     * @return
     */
    PageInfo<Map<String,Object>> selectList(Map<String, Object> params);
}