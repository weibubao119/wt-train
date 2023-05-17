package com.dyys.hr.dao.train;

import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.train.TrainMaterialsLearningRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainMaterialsLearningRecordMapper extends ICrudMapper<TrainMaterialsLearningRecord> {
    /**
     * 获取未学完数据统计
     * @param userId
     * @param programsId
     * @return
     */
    int getUnFinishNumByProgramsId(String userId,Long programsId);

    List<String> getHaveFinishDurationList(String userId, String trainNum);
}