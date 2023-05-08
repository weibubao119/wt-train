package com.dyys.hr.service.train.impl;

import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.train.TrainAdminSummaryMapper;
import com.dyys.hr.dto.train.TrainAdminSummaryDTO;
import com.dyys.hr.entity.train.TrainAdminSummary;
import com.dyys.hr.service.train.TrainAdminSummaryService;
import com.dyys.hr.service.train.TrainTraineeSummaryService;
import com.dyys.hr.vo.train.TrainAdminSummaryDetailVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
@Slf4j
public class TrainAdminSummaryServiceImpl extends AbstractCrudService<TrainAdminSummary, Long> implements TrainAdminSummaryService {
    @Autowired
    private TrainAdminSummaryMapper trainAdminSummaryMapper;
    @Autowired
    private TrainTraineeSummaryService trainTraineeSummaryService;

    @Override
    public TrainAdminSummaryDetailVO getDetail(Long programsId){
        Map<String,Object> query = new HashMap<>();
        query.put("programsId",programsId);
        TrainAdminSummaryDetailVO detailVO = trainAdminSummaryMapper.getDetail(query);
        if(detailVO != null){
            //计算实际平均成绩
            detailVO.setActualResults(trainTraineeSummaryService.getProgramsAverageResult(query));
        }
        return detailVO;
    }

    @Override
    public Integer update(TrainAdminSummaryDTO dto,String loginUserId){
        Map<String,Object> query = new HashMap<>();
        query.put("programsId",dto.getProgramsId());
        TrainAdminSummaryDetailVO detailVO = trainAdminSummaryMapper.getDetail(query);
        //判断总结是否存在，是更新否新增
        TrainAdminSummary summaryEntity = new TrainAdminSummary();
        BeanUtils.copyProperties(dto,summaryEntity);
        if(detailVO == null){
            summaryEntity.setCreateTime(System.currentTimeMillis()/1000);
            summaryEntity.setCreateUser(loginUserId);
            this.insertSelective(summaryEntity);
            return 1;
        }
        else{
            summaryEntity.setId(detailVO.getId());
            summaryEntity.setUpdateTime(System.currentTimeMillis()/1000);
            summaryEntity.setUpdateUser(loginUserId);
            return this.updateSelective(summaryEntity);
        }
    }
}