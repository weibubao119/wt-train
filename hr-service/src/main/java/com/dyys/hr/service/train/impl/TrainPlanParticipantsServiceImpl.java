package com.dyys.hr.service.train.impl;

import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.train.TrainPlanDetailMapper;
import com.dyys.hr.entity.train.TrainPlanDetail;
import com.dyys.hr.entity.train.TrainPlanParticipants;
import com.dyys.hr.service.train.TrainPlanDetailService;
import com.dyys.hr.service.train.TrainPlanParticipantsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TrainPlanParticipantsServiceImpl extends AbstractCrudService<TrainPlanParticipants, Long> implements TrainPlanParticipantsService {
}