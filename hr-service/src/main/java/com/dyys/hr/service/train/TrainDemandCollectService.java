package com.dyys.hr.service.train;

import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.dto.train.TrainDemandCollectDTO;
import com.dyys.hr.entity.train.TrainDemandCollect;
import com.dyys.hr.vo.train.*;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface TrainDemandCollectService extends ICrudService<TrainDemandCollect, Long> {
    PageInfo<TrainDemandCollectVO> pageList(Map<String, Object> params);

    Long save(TrainDemandCollectDTO dto,String loginUserId);

    TrainDemandCollectDetailVO selectByDemandId(Long demandId);

    Integer update(TrainDemandCollectDTO dto);

    Integer deleteByDemandId(Long demandId);

    Integer closeCollect(Long demandId);

    PageInfo<TrainRelateDemandCollectVO> relateDemandList(Map<String, Object> params);



}