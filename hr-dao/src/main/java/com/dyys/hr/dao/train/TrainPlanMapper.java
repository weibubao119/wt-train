package com.dyys.hr.dao.train;

import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.train.TrainPlan;
import com.dyys.hr.vo.train.TrainPlanFormVO;
import com.dyys.hr.vo.train.TrainPlanVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TrainPlanMapper extends ICrudMapper<TrainPlan> {
    List<TrainPlanVO> pageList(Map<String, Object> params);

    TrainPlanFormVO getDetailByQuery(Map<String, Object> params);

    List<Map<String,Object>> selectList(Map<String, Object> params);
}