package com.dyys.hr.service.train.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.train.TrainDemandFeedbackDetailMapper;
import com.dyys.hr.dao.train.TrainDemandFeedbackMapper;
import com.dyys.hr.dto.train.TrainDemandAddFeedbackDTO;
import com.dyys.hr.entity.train.TrainDemandFeedback;
import com.dyys.hr.entity.train.TrainDemandFeedbackLog;
import com.dyys.hr.service.train.TrainDemandFeedbackLogService;
import com.dyys.hr.service.train.TrainDemandFeedbackService;
import com.dyys.hr.vo.train.TrainDemandFeedbackVO;
import com.dyys.hr.vo.train.TrainDemandUserFeedbackVO;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class TrainDemandFeedbackServiceImpl extends AbstractCrudService<TrainDemandFeedback, Long> implements TrainDemandFeedbackService {
    @Autowired
    private TrainDemandFeedbackMapper trainDemandFeedbackMapper;
    @Autowired
    private TrainDemandFeedbackDetailMapper trainDemandFeedbackDetailMapper;
    @Autowired
    private TrainDemandFeedbackService trainDemandFeedbackService;
    @Autowired
    private TrainDemandFeedbackLogService trainDemandFeedbackLogService;

    @Override
    public PageInfo<TrainDemandUserFeedbackVO> userFeedBackPageList(Map<String, Object> params){
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        //如果状态是查看待反馈,则只关联正在进行中的收集需求
        if(params.get("status").toString().equals("0")){
            params.put("collectStatus",1);
        }
        List<TrainDemandUserFeedbackVO> voList = trainDemandFeedbackMapper.userFeedBackPageList(params);
        return new PageInfo<>(voList);
    }

    public List<TrainDemandFeedbackVO> feedbackList(Map<String, Object> params){
        return trainDemandFeedbackMapper.feedbackList(params);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addFeedBackUser(List<TrainDemandAddFeedbackDTO> dtoList, String loginUserId){

        //新增需求
        if(dtoList != null && !dtoList.isEmpty()){
            Map map = new HashMap();
            map.put("demand_id",dtoList.get(0).getDemandId());
            List<TrainDemandFeedbackVO> demandFeedbackList = trainDemandFeedbackMapper.feedbackList(map);
            List<String> feedbackUserIdS = new ArrayList<>();

            if (!CollectionUtil.isEmpty(demandFeedbackList)){
                for (TrainDemandFeedbackVO demandFeedbackVO : demandFeedbackList) {
                    feedbackUserIdS.add(demandFeedbackVO.getFeedbackUserId());
                }
            }

            List<TrainDemandFeedback> feedbackList = new ArrayList<>();
            for (TrainDemandAddFeedbackDTO dto : dtoList){

                if (feedbackUserIdS.contains(dto.getFeedbackUserId())){
                    continue;
                }

                TrainDemandFeedback entity = new TrainDemandFeedback();
                BeanUtils.copyProperties(dto,entity);
                entity.setStatus(0);
                entity.setCreateTime(System.currentTimeMillis()/1000);
                entity.setCreateUser(loginUserId);
                feedbackList.add(entity);
            }
            if (!CollectionUtil.isEmpty(feedbackList)){
                return this.insertBatchSelective(feedbackList);
            }
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer operateFeedBackData(Map<String, Object> params){
        int type = Integer.parseInt(params.get("type").toString());
        String id = params.get("id").toString();
        Integer result = null;
        //1.取消反馈 2.退回反馈
        if(type == 1){
            //清除反馈和反馈详情表
            Map<String, Object> query = new HashMap<>();
            query.put("id",id);
            TrainDemandFeedbackVO vo = trainDemandFeedbackMapper.findOneByQuery(query);
            Map<String, Object> queryParam = new HashMap<>();
            queryParam.put("demand_id",vo.getDemandId());
            queryParam.put("feedback_user_id",vo.getFeedbackUserId());
            result = trainDemandFeedbackDetailMapper.deleteByQuery(queryParam);

            trainDemandFeedbackMapper.cancelFeedBackById(id);
        }
        if(type == 2){
            //修改状态为待反馈
            result = trainDemandFeedbackMapper.rollbackFeedBackById(id);
            //判断操作成功记录日志
            if(result != null){
                TrainDemandFeedbackLog feedbackLog = new TrainDemandFeedbackLog();
                feedbackLog.setFeedbackId(id);
                feedbackLog.setType(type);
                feedbackLog.setReason(params.get("reason").toString());
                feedbackLog.setCreateUser(params.get("operate_user_id").toString());
                feedbackLog.setCreateTime(System.currentTimeMillis());
                trainDemandFeedbackLogService.insertSelective(feedbackLog);
            }
        }

        return result;
    }

    @Override
    public void deleteFeedBackByQuery(Map<String, Object> params){
        trainDemandFeedbackMapper.deleteFeedBackByQuery(params);
    }

}