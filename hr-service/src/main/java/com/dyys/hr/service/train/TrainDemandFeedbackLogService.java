package com.dyys.hr.service.train;


import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.entity.train.TrainDemandFeedbackLog;
import com.dyys.hr.vo.train.TrainDemandFeedbackLogVO;

import java.util.List;
import java.util.Map;

public interface TrainDemandFeedbackLogService extends ICrudService<TrainDemandFeedbackLog, Long> {
    /**
     * 获取操作日志列表
     * @param params
     * @return
     */
    List<TrainDemandFeedbackLogVO> getList(Map<String, Object> params);
}