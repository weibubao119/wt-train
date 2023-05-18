package com.dyys.hr.service.train.impl;

import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.train.TrainBaseCourseMaterialsMapper;
import com.dyys.hr.dao.train.TrainMaterialsLearningRecordMapper;
import com.dyys.hr.entity.train.TrainMaterialsLearningRecord;
import com.dyys.hr.entity.train.TrainNotice;
import com.dyys.hr.service.train.TrainBaseCourseMaterialsService;
import com.dyys.hr.service.train.TrainMaterialsLearningRecordService;
import com.dyys.hr.service.train.TrainMaterialsService;
import com.dyys.hr.service.train.TrainNoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    private TrainMaterialsLearningRecordMapper trainMaterialsLearningRecordMapper;
    @Autowired
    private TrainNoticeService trainNoticeService;

    /**
     * 材料学习记录
     * @param params
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer materialsLearningRecord(Map<String,Object> params){
        Long materialsId = Long.valueOf(params.get("id").toString());
        int type = Integer.parseInt(params.get("type").toString());
        //材料类型 1.视频 2.音频 3.其他
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
            if(materialsType != 3  && !Objects.equals(duration, finishDuration)){
                status = 0;
            }
            queryEntity.setMaterialsType(materialsType);
            queryEntity.setStatus(status);
            queryEntity.setLastDuration(duration);
            queryEntity.setCreateUser(userId);
            queryEntity.setCreateTime(System.currentTimeMillis()/1000);
            this.insertSelective(queryEntity).intValue();
            return status;
        }
        else{
            TrainMaterialsLearningRecord updateEntity = new TrainMaterialsLearningRecord();
            updateEntity.setId(selectOne.getId());
            //判断资料类型，根据学习时长处理学习状态
            if(selectOne.getStatus() != 1 && materialsType != 3 && !Objects.equals(duration, finishDuration)){
                status = 0;
            }
            updateEntity.setStatus(status);
            updateEntity.setLastDuration(duration);
            updateEntity.setUpdateUser(userId);
            updateEntity.setUpdateTime(System.currentTimeMillis()/1000);
            this.updateSelective(updateEntity);
            //判断type=2 时，该材料对应的培训班材料学习如果全部完成则更新notice通知表状态
            if(type == 2){
                Long programsId = trainMaterialsService.selectById(materialsId).getProgramsId();
                int unFinishNum = trainMaterialsLearningRecordMapper.getUnFinishNumByProgramsId(userId,programsId);
                if(unFinishNum == 0){
                    TrainNotice queryNotice = new TrainNotice();
                    queryNotice.setType(12);
                    queryNotice.setTypeId(programsId);
                    queryNotice.setUserId(userId);
                    queryNotice.setStatus(0);
                    TrainNotice noticeOne = trainNoticeService.selectOne(queryNotice);
                    if(noticeOne != null){
                        TrainNotice updateNotice = new TrainNotice();
                        updateNotice.setId(noticeOne.getId());
                        updateNotice.setStatus(1);
                        updateNotice.setUpdateUser(userId);
                        updateNotice.setUpdateTime(System.currentTimeMillis()/1000);
                        trainNoticeService.updateSelective(updateNotice);
                    }
                }
            }
            return status;
        }
    }
}