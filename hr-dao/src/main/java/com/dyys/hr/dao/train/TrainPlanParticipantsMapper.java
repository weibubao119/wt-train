package com.dyys.hr.dao.train;

import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.train.TrainPlanParticipants;
import com.dyys.hr.vo.train.TrainPlanParticipantsVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface TrainPlanParticipantsMapper extends ICrudMapper<TrainPlanParticipants> {
    List<TrainPlanParticipantsVO> getList(Map<String, Object> params);


    /**
     * 根据条件删除
     * @param params
     */
    void deleteByParams(Map<String, Object> params);
}