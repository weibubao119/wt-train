package com.dyys.hr.dao.train;

import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.train.TrainAdminSummary;
import com.dyys.hr.vo.train.TrainAdminSummaryDetailVO;
import org.springframework.stereotype.Repository;

import java.util.Map;


@Repository
public interface TrainAdminSummaryMapper extends ICrudMapper<TrainAdminSummary> {
    TrainAdminSummaryDetailVO getDetail(Map<String, Object> params);
}