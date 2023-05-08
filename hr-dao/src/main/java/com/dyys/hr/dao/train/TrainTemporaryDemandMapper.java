package com.dyys.hr.dao.train;

import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.train.TrainDemandCollect;
import com.dyys.hr.entity.train.TrainTemporaryDemand;
import com.dyys.hr.vo.train.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface TrainTemporaryDemandMapper extends ICrudMapper<TrainTemporaryDemand> {
    List<TrainTemporaryDemandPageVO> pageList(Map<String, Object> params);

    TrainTemporaryDemandDetailVO getDetailByQuery(Map<String, Object> params);

    List<AdminTemporaryDemandListVO> adminTemporaryDemandList(Map<String, Object> params);
}