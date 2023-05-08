package com.dyys.hr.dao.train;

import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.train.TrainBaseTeacher;
import com.dyys.hr.entity.train.excel.AbleCourseExcel;
import com.dyys.hr.entity.train.excel.BaseTeacherListExcel;
import com.dyys.hr.entity.train.excel.BaseTeacherProgressExcel;
import com.dyys.hr.entity.train.excel.TaughtCourseExcel;
import com.dyys.hr.vo.train.TrainBaseTeacherDetailVO;
import com.dyys.hr.vo.train.TrainBaseTeacherVO;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TrainBaseTeacherMapper extends ICrudMapper<TrainBaseTeacher> {
    List<TrainBaseTeacherVO> pageList(Map<String, Object> params);

    TrainBaseTeacherDetailVO selectByTeacherId(@Param("id") Long teacherId);

    String getAvatarByTeacherId(@Param("id") Long teacherId);

    List<Map<String,Object>> selectList(Map<String, Object> params);

    List<BaseTeacherListExcel> teacherListExport(Map<String, Object> params);

    /**
     * 导出讲师成长历程数据
     * @param params
     * @return
     */
    List<BaseTeacherProgressExcel> progressListExport(Map<String, Object> params);

    /**
     * 导出讲师可授课程
     * @param params
     * @return
     */
    List<AbleCourseExcel> ableCourseListExport(Map<String, Object> params);

    /**
     * 导出讲师已授课程
     * @param params
     * @return
     */
    List<TaughtCourseExcel> taughtCourseListExport(Map<String, Object> params);
}