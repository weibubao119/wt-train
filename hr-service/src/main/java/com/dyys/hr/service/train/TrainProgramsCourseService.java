package com.dyys.hr.service.train;

import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.entity.train.TrainProgramsCourse;
import com.dyys.hr.vo.train.TeacherHaveTaughtRecordsVO;
import com.dyys.hr.vo.train.TrainBaseSiteUsageVO;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface TrainProgramsCourseService extends ICrudService<TrainProgramsCourse, Long> {
    void deleteByParams(Map<String,Object> params);

    /**
     * 根据项目ID和课程ID获取授课讲师姓名
     * @param programsId
     * @param courseId
     * @return
     */
    List<String> getTeacherNameList(Long programsId, Long courseId);

    /**
     * 场地详情-场地使用情况
     * @param params
     * @return
     */
    PageInfo<TrainBaseSiteUsageVO> siteUsageList(Map<String, Object> params);

    /**
     * 获取某个培训项目的讲师已授课程
     * @param programsId
     * @return
     */
    List<TrainProgramsCourse> teacherCourseList(Long programsId);
}