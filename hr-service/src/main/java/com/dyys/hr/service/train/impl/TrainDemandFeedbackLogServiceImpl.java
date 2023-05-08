package com.dyys.hr.service.train.impl;
import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.train.TrainDemandFeedbackLogMapper;
import com.dyys.hr.entity.train.*;
import com.dyys.hr.service.train.*;
import com.dyys.hr.vo.train.TrainDemandFeedbackLogVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class TrainDemandFeedbackLogServiceImpl extends AbstractCrudService<TrainDemandFeedbackLog, Long> implements TrainDemandFeedbackLogService {
    @Autowired
    private TrainDemandFeedbackLogMapper trainDemandFeedbackLogMapper;
    @Override
    public List<TrainDemandFeedbackLogVO> getList(Map<String, Object> params){
        return trainDemandFeedbackLogMapper.getList(params);
    }
}