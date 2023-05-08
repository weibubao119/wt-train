package com.dyys.hr.dao.train;

import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.train.TrainDemandFeedback;
import com.dyys.hr.vo.train.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TrainDemandFeedbackMapper extends ICrudMapper<TrainDemandFeedback> {
    List<TrainDemandFeedbackVO> feedbackList(Map<String, Object> params);

    Integer cancelFeedBackById(@Param("id") String feedbackId);

    Integer rollbackFeedBackById(@Param("id") String feedbackId);

    Integer finishFeedBackByQuery(Map<String, Object> params);

    List<TrainDemandUserFeedbackVO> userFeedBackPageList(Map<String, Object> params);

    TrainDemandFeedbackVO findOneByQuery(Map<String, Object> params);

    void deleteFeedBackByQuery(Map<String, Object> params);
}