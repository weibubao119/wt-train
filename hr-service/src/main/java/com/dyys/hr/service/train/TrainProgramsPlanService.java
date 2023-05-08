package com.dyys.hr.service.train;

import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.entity.train.TrainProgramsPlan;
import com.dyys.hr.vo.train.TrainInstitutionHistoryServiceVO;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface TrainProgramsPlanService extends ICrudService<TrainProgramsPlan, Long> {
    void deleteByParams(Map<String,Object> params);

    PageInfo<Map<String,Object>> selectList(Map<String, Object> params);

    /**
     * 已完成培训总时长统计
     * @param params
     * @return
     */
    Float getProgramsCourseHoursCountByQuery(Map<String, Object> params);

    /**
     * 查询某个培训机构参与的培训项目历史分页列表
     * @param params
     * @return
     */
    List<TrainInstitutionHistoryServiceVO> ProgramsListByInstitutionId(Map<String, Object> params);

    /**
     * 根据条件获取机构服务过的培训项目ID集
     * @param params
     * @return
     */
    List<Long> selectProgramsIds(Map<String, Object> params);

    /**
     * 查询某个机构服务过的培训项目课程ID集
     * @param params
     * @return
     */
    List<Long> selectCourseIds(Map<String, Object> params);

    /**
     * 根据培训项目ID、课程ID和数据来源查询培训计划记录
     * @param programsId
     * @param courseId
     * @param isImport
     * @return
     */
    TrainProgramsPlan getPlanInfo(Long programsId, Long courseId, Integer isImport);
}