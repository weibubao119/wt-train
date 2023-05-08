package com.dyys.hr.dao.train;

import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.train.TrainBaseCourse;
import com.dyys.hr.entity.train.excel.AbleTeacherExcel;
import com.dyys.hr.entity.train.excel.BaseCourseListExcel;
import com.dyys.hr.vo.train.TrainBaseCourseVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TrainBaseCourseMapper extends ICrudMapper<TrainBaseCourse> {
    List<TrainBaseCourseVO> pageList(Map<String, Object> params);

    /**
     * 导出课程列表
     * @param params
     * @return
     */
    List<BaseCourseListExcel> courseListExport(Map<String, Object> params);

    TrainBaseCourse selectByCourseId(@Param("id") Long courseId);

    List<Map<String,Object>> selectList(Map<String, Object> params);

    List<TrainBaseCourse> allCourseList();

    /**
     * 导出课程可授课讲师
     * @param params
     * @return
     */
    List<AbleTeacherExcel> ableTeacherList(Map<String, Object> params);
}