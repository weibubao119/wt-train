package com.dyys.hr.service.train;

import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.dto.train.TrainPlanDTO;
import com.dyys.hr.entity.train.TrainPlan;
import com.dyys.hr.vo.train.TrainPlanFormVO;
import com.dyys.hr.vo.train.TrainPlanVO;
import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface TrainPlanService extends ICrudService<TrainPlan, Long> {
    PageInfo<TrainPlanVO> pageList(Map<String, Object> params);

    Long save(TrainPlanDTO dto, String loginUserId);

    Long storage(TrainPlanDTO dto, String loginUserId);

    Integer update(TrainPlanDTO dto, String loginUserId);

    TrainPlanFormVO getDetail(Long demandId);

    PageInfo<Map<String,Object>> selectList(Map<String, Object> params);
}