package com.dyys.hr.dao.train;
import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.train.TrainProgramsCourse;
import com.dyys.hr.vo.train.TeacherHaveTaughtRecordsVO;
import com.dyys.hr.vo.train.TrainBaseSiteUsageVO;
import com.dyys.hr.vo.train.TrainProgramsCourseDetailVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 过程管理-培训项目课程表
 *
 * @author sie sie
 * @since 1.0.0 2022-08-25
 */
@Mapper
public interface TrainProgramsCourseMapper extends ICrudMapper<TrainProgramsCourse> {
    List<TrainProgramsCourseDetailVO> getDetailList(Map<String, Object> params);

    void deleteByParams(Map<String,Object> params);

    /**
     * 根据项目ID和课程ID获取授课讲师姓名
     * @param programsId
     * @param courseId
     * @return
     */
    List<String> getTeacherNameList(@Param("programsId") Long programsId, @Param("courseId") Long courseId);

    /**
     * 场地详情-场地使用情况
     * @param params
     * @return
     */
    List<TrainBaseSiteUsageVO> usagePageList(Map<String, Object> params);

    /**
     * 获取某个培训项目的讲师已授课程
     * @param programsId
     * @return
     */
    List<TrainProgramsCourse> teacherCourseList(Long programsId);
}