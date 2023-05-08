package com.dyys.hr.dao.train;

import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.train.TrainCost;
import com.dyys.hr.vo.train.TrainCostVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TrainCostMapper extends ICrudMapper<TrainCost> {
    List<TrainCostVO> pageList(Map<String, Object> params);

    /**
     * 已完成培训费用统计(包括按组织、按年份、按培训形式)
     * @param params
     * @return
     */
    Float getSumCostByQuery(Map<String, Object> params);

    /**
     * 获取费用对应的项目参训人员的职等分组数据
     * @param params
     * @return
     * level_code 职等编码
     * level_descr 职等名称
     * programs_id 培训项目ID
     * amount 项目对应总费用
     * total_participants_num 项目总确认参训人数
     * level_participants_num 对应职等的项目总确认参训人数
     */
    List<Map<String,Object>> getParticipantsLevelGroupList(Map<String, Object> params);

    /**
     * 获取费用对应的项目参训人员的职级分组数据
     * @param params
     * @return
     * grade_code 职级编码
     * grade_descr 职级名称
     * programs_id 培训项目ID
     * amount 项目对应总费用
     * total_participants_num 项目总确认参训人数
     * level_participants_num 对应职等的项目总确认参训人数
     */
    List<Map<String,Object>> getParticipantsGradeGroupList(Map<String, Object> params);

    /**
     * 获取项目培训总费用
     * @param programsId
     * @return
     */
    Float getTotalFeeByProgramsId(Long programsId);

    /**
     * 获取项目培训各科目费用列表
     * @param programsId
     * @return
     */
    List<Map<String,Object>> subjectAmountList(Long programsId);

    /**
     * 获取某科目的培训费用
     * @param params
     * @return
     */
    Float getSubjectAmount(Map<String, Object> params);
}