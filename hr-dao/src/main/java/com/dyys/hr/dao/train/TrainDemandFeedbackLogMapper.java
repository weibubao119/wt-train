package com.dyys.hr.dao.train;

import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.train.TrainDemandFeedbackLog;
import com.dyys.hr.vo.train.TrainDemandFeedbackLogVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TrainDemandFeedbackLogMapper extends ICrudMapper<TrainDemandFeedbackLog> {
    List<TrainDemandFeedbackLogVO> getList(Map<String, Object> params);
}