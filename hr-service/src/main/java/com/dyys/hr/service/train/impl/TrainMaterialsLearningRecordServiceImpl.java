package com.dyys.hr.service.train.impl;

import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.train.TrainBaseCourseMaterialsMapper;
import com.dyys.hr.entity.train.TrainMaterialsLearningRecord;
import com.dyys.hr.service.train.TrainBaseCourseMaterialsService;
import com.dyys.hr.service.train.TrainMaterialsLearningRecordService;
import com.dyys.hr.service.train.TrainMaterialsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class TrainMaterialsLearningRecordServiceImpl extends AbstractCrudService<TrainMaterialsLearningRecord, Long> implements TrainMaterialsLearningRecordService {
    @Autowired
    private TrainBaseCourseMaterialsMapper trainBaseCourseMaterialsMapper;
    @Autowired
    private TrainBaseCourseMaterialsService trainBaseCourseMaterialsService;
    @Autowired
    private TrainMaterialsService trainMaterialsService;

    /**
     * 材料学习记录
     * @param params
     * @return
     */
    @Override
    public Integer materialsLearningRecord(Map<String,Object> params){
        Long materialsId = Long.valueOf(params.get("id").toString());
        int type = Integer.parseInt(params.get("type").toString());
        int materialsType = Integer.parseInt(params.get("materialsType").toString());
        String duration = params.get("duration").toString();
        String userId = params.get("userId").toString();
        TrainMaterialsLearningRecord queryEntity = new TrainMaterialsLearningRecord();
        queryEntity.setMaterialsId(materialsId);
        queryEntity.setType(type);
        queryEntity.setUserId(userId);
        //查找学习记录数据,有则更新无则新增
        TrainMaterialsLearningRecord selectOne = this.selectOne(queryEntity);
        int status = 1;
        String finishDuration;
        //获取资料源时长
        if(type == 1){
            finishDuration = trainBaseCourseMaterialsService.selectById(materialsId).getDuration();
        }
        else{
            finishDuration = trainMaterialsService.selectById(materialsId).getDuration();
        }
        if(selectOne == null){
            //判断资料类型，根据学习时长处理学习状态
            if(materialsType == 1 && !Objects.equals(duration, finishDuration)){
                status = 0;
            }
            queryEntity.setMaterialsType(materialsType);
            queryEntity.setStatus(status);
            queryEntity.setLastDuration(duration);
            queryEntity.setCreateUser(userId);
            queryEntity.setCreateTime(System.currentTimeMillis()/1000);
            return this.insertSelective(queryEntity).intValue();
        }
        else{
            TrainMaterialsLearningRecord updateEntity = new TrainMaterialsLearningRecord();
            updateEntity.setId(selectOne.getId());
            //判断资料类型，根据学习时长处理学习状态
            if(selectOne.getStatus() != 1 && materialsType == 1 && !Objects.equals(duration, finishDuration)){
                status = 0;
            }
            updateEntity.setStatus(status);
            updateEntity.setLastDuration(duration);
            updateEntity.setUpdateUser(userId);
            updateEntity.setUpdateTime(System.currentTimeMillis()/1000);
            return this.updateSelective(updateEntity);
        }
    }
}