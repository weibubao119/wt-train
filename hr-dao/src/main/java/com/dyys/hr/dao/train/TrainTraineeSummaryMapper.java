package com.dyys.hr.dao.train;

import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.train.TrainTraineeSummary;
import com.dyys.hr.vo.train.TrainTraineeSummaryVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TrainTraineeSummaryMapper extends ICrudMapper<TrainTraineeSummary> {
    List<TrainTraineeSummaryVO> traineePageList(Map<String, Object> params);

    String getProgramsAverageResult(Map<String, Object> params);
}