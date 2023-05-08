package com.dyys.hr.dao.train;

import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.train.TrainDemandFeedbackDetail;
import com.dyys.hr.entity.train.excel.DemandFeedbackExcel;
import com.dyys.hr.vo.train.TrainDemandFeedbackDetailVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TrainDemandFeedbackDetailMapper extends ICrudMapper<TrainDemandFeedbackDetail> {
    /**
     * 需求汇总页面数据列表
     * @param params
     * @return
     */
    List<TrainDemandFeedbackDetailVO> pageList(Map<String, Object> params);

    TrainDemandFeedbackDetailVO selectByDetailId(@Param("id") Long detailId);

    Integer deleteByDetailId(@Param("id") Long detailId);

    Integer deleteByQuery(Map<String, Object> params);

    List<TrainDemandFeedbackDetail> selectByQuery(Map<String, Object> params);

    /**
     * 需求汇总-反馈列表导出
     * @param demandId
     * @return
     */
    List<DemandFeedbackExcel> exportList(@Param("demandId") String demandId);
}