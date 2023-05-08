package com.dyys.hr.service.train.impl;

import cn.hutool.core.convert.Convert;
import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.train.TrainProgramsCourseMapper;
import com.dyys.hr.entity.train.TrainProgramsCourse;
import com.dyys.hr.service.train.TrainProgramsCourseService;
import com.dyys.hr.vo.train.TeacherHaveTaughtRecordsVO;
import com.dyys.hr.vo.train.TrainBaseSiteUsageVO;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class TrainProgramsCourseServiceImpl extends AbstractCrudService<TrainProgramsCourse, Long> implements TrainProgramsCourseService {
    @Autowired
    private TrainProgramsCourseMapper trainProgramsCourseMapper;

    @Override
    public void deleteByParams(Map<String,Object> params){
        trainProgramsCourseMapper.deleteByParams(params);
    }

    /**
     * 根据项目ID和课程ID获取授课讲师姓名
     * @param programsId
     * @param courseId
     * @return
     */
    @Override
    public List<String> getTeacherNameList(Long programsId, Long courseId) {
        return trainProgramsCourseMapper.getTeacherNameList(programsId, courseId);
    }

    /**
     * 场地详情-场地使用情况
     * @param params
     * @return
     */
    @Override
    public PageInfo<TrainBaseSiteUsageVO> siteUsageList(Map<String, Object> params) {
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        List<TrainBaseSiteUsageVO> usageList = trainProgramsCourseMapper.usagePageList(params);
        return new PageInfo<>(usageList);
    }

    /**
     * 获取某个培训项目的讲师已授课程
     * @param programsId
     * @return
     */
    @Override
    public List<TrainProgramsCourse> teacherCourseList(Long programsId) {
        return trainProgramsCourseMapper.teacherCourseList(programsId);
    }
}