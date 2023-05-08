package com.dyys.hr.dao.train;

import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.train.TrainDemandCollect;
import com.dyys.hr.vo.train.TrainDemandCollectVO;
import com.dyys.hr.vo.train.TrainRelateDemandCollectVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface TrainDemandCollectMapper extends ICrudMapper<TrainDemandCollect> {
    List<TrainDemandCollectVO> pageList(Map<String, Object> params);

    TrainDemandCollect selectByDemandId(@Param("id") Long demandId);

    Integer deleteByDemandId(@Param("id") Long demandId);

    Integer closeCollect(@Param("id") Long demandId);

    List<TrainRelateDemandCollectVO> relateDemandList(Map<String, Object> params);
}