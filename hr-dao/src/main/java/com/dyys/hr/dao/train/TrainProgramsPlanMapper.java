package com.dyys.hr.dao.train;

import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.train.TrainProgramsPlan;
import com.dyys.hr.vo.train.TrainInstitutionHistoryServiceVO;
import com.dyys.hr.vo.train.TrainProgramsPlanDetailVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Repository
public interface TrainProgramsPlanMapper extends ICrudMapper<TrainProgramsPlan> {
    List<TrainProgramsPlanDetailVO> getDetailList(Map<String, Object> params);

    void deleteByParams(Map<String,Object> params);

    List<Map<String,Object>> selectList(Map<String, Object> params);

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
     * 根据课程分类查询统计
     * @param params
     * @return
     */
    Integer getCountByCourseCategory(Map<String, Object> params);

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
}