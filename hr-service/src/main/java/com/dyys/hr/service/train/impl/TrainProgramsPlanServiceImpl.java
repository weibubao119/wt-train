package com.dyys.hr.service.train.impl;

import cn.hutool.core.convert.Convert;
import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.train.TrainProgramsPlanMapper;
import com.dyys.hr.entity.train.TrainProgramsPlan;
import com.dyys.hr.service.train.TrainAppraiseService;
import com.dyys.hr.service.train.TrainProgramsCourseService;
import com.dyys.hr.service.train.TrainProgramsPlanService;
import com.dyys.hr.vo.train.TrainInstitutionHistoryServiceVO;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class TrainProgramsPlanServiceImpl extends AbstractCrudService<TrainProgramsPlan, Long> implements TrainProgramsPlanService {
    @Autowired
    private TrainProgramsPlanMapper trainProgramsPlanMapper;
    @Autowired
    private TrainAppraiseService trainAppraiseService;
    @Autowired
    private TrainProgramsCourseService trainProgramsCourseService;

    @Override
    public void deleteByParams(Map<String,Object> params){
       trainProgramsPlanMapper.deleteByParams(params);
    }

    @Override
    public PageInfo<Map<String,Object>> selectList(Map<String, Object> params){
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        List<Map<String,Object>> voList = trainProgramsPlanMapper.selectList(params);
        return new PageInfo<>(voList);
    }

    /**
     * 已完成培训总时长统计
     * @param params
     * @return
     */
    @Override
    public Float getProgramsCourseHoursCountByQuery(Map<String, Object> params){
        return trainProgramsPlanMapper.getProgramsCourseHoursCountByQuery(params);
    }

    /**
     * 查询某个培训机构参与的培训项目历史分页列表
     * @param params
     * @return
     */
    @Override
    public List<TrainInstitutionHistoryServiceVO> ProgramsListByInstitutionId(Map<String, Object> params) {
        List<TrainInstitutionHistoryServiceVO> historyList = trainProgramsPlanMapper.ProgramsListByInstitutionId(params);
        for (TrainInstitutionHistoryServiceVO vo : historyList) {
            vo.setCourseScore(trainAppraiseService.getAvgCourseScore(vo.getProgramsId(), vo.getCourseId())); // 课程评分
            List<String> teacherNameList = trainProgramsCourseService.getTeacherNameList(vo.getProgramsId(), vo.getCourseId());
            vo.setTeacherName(StringUtils.join(teacherNameList.toArray(), "、"));
        }
        return historyList;
    }

    /**
     * 根据条件获取机构服务过的培训项目ID集
     * @param params
     * @return
     */
    @Override
    public List<Long> selectProgramsIds(Map<String, Object> params) {
        return trainProgramsPlanMapper.selectProgramsIds(params);
    }

    /**
     * 查询某个机构服务过的培训项目课程ID集
     * @param params
     * @return
     */
    @Override
    public List<Long> selectCourseIds(Map<String, Object> params) {
        return trainProgramsPlanMapper.selectCourseIds(params);
    }

    /**
     * 根据培训项目ID、课程ID和数据来源查询培训计划记录
     * @param programsId
     * @param courseId
     * @param isImport
     * @return
     */
    @Override
    public TrainProgramsPlan getPlanInfo(Long programsId, Long courseId, Integer isImport) {
        TrainProgramsPlan query = new TrainProgramsPlan();
        query.setProgramsId(programsId);
        query.setCourseId(courseId);
        query.setIsImport(isImport);
        return selectOne(query);
    }
}