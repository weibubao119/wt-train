package com.dyys.hr.service.train;

import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.entity.train.TrainMaterialsLearningRecord;

import java.util.Map;

public interface TrainMaterialsLearningRecordService extends ICrudService<TrainMaterialsLearningRecord, Long> {
    /**
     * 材料学习记录
     * @param params
     * @return
     */
    Integer materialsLearningRecord(Map<String,Object> params);
}