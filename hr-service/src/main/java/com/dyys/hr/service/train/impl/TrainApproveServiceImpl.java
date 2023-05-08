package com.dyys.hr.service.train.impl;

import com.dagongma.kernel.commons.enums.ResultCode;
import com.dagongma.kernel.commons.exceptions.BusinessException;
import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.train.TrainApproveMapper;
import com.dyys.hr.entity.train.*;
import com.dyys.hr.service.train.*;
import com.dyys.hr.vo.train.TrainApproveVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 培训-审批记录表
 *
 * @author sie sie
 * @since 1.0.0 2023-02-17
 */
@Service
public class TrainApproveServiceImpl extends AbstractCrudService<TrainApprove, Long> implements TrainApproveService {
    @Autowired
    private TrainApproveMapper trainApproveMapper;
    @Autowired
    private TrainTemporaryDemandService trainTemporaryDemandService;
    @Autowired
    private TrainPlanDetailService trainPlanDetailService;
    @Autowired
    private TrainNoticeService trainNoticeService;
    @Autowired
    private TrainPlanService trainPlanService;

    /**
     * 审批记录列表
     * @param params
     * @return
     */
    @Override
    public List<TrainApproveVO> getListByQuery(Map<String, Object> params){
        return trainApproveMapper.getListByQuery(params);
    }

    /**
     * 进行审批
     * @param params
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer doApprove(Map<String, Object> params){
        TrainApprove approveQuery = new TrainApprove();
        int type = Integer.parseInt(params.get("type").toString());
        Long typeId = Long.valueOf(params.get("typeId").toString());
        String approveEmplid = params.get("approveEmplid").toString();
        int status = Integer.parseInt(params.get("status").toString());
        String reason = params.get("reason").toString();
        approveQuery.setType(type);
        approveQuery.setTypeId(typeId);
        approveQuery.setApproveEmplid(approveEmplid);
        approveQuery.setStatus(1);
        approveQuery.setIsHistory(0);
        TrainApprove selectOne = this.selectOne(approveQuery);
        //查找审批记录
        String message = "";
        if(selectOne == null){
            message = "审批记录不存在！";
            throw new BusinessException(ResultCode.EXCEPTION,message);
        }
        approveQuery.setId(selectOne.getId());
        approveQuery.setStatus(status);
        approveQuery.setReason(reason);
        approveQuery.setUpdateUser(approveEmplid);
        approveQuery.setUpdateTime(System.currentTimeMillis()/1000);
        int updateRes = this.updateSelective(approveQuery);
        if(updateRes <= 0){
            message = "审批操作更新失败！";
            throw new BusinessException(ResultCode.EXCEPTION,message);
        }
        //更新不同类型计划审批状态 1.计划审批 2.临时需求审批
        if(type == 1){
            //计划审批更新
            TrainPlan plan = trainPlanService.selectById(typeId);
            if(plan.getStatus() == 0){
                //更新当前节点审批代办通知状态
                TrainNotice noticeQuery = new TrainNotice();
                noticeQuery.setTypeId(typeId);
                noticeQuery.setType(11);
                noticeQuery.setUserId(approveEmplid);
                noticeQuery.setStatus(0);
                TrainNotice noticeEntity = trainNoticeService.selectOne(noticeQuery);
                if(noticeEntity != null){
                    noticeEntity.setStatus(1);
                    noticeEntity.setUpdateUser(approveEmplid);
                    noticeEntity.setUpdateTime(System.currentTimeMillis()/1000);
                    trainNoticeService.updateSelective(noticeEntity);
                }

                //判断状态 2.同意 3.驳回
                if(status == 2){
                    //判断节点逻辑,如果不是最后审批,则向下一节点发送代办,反之更新计划审批状态
                    Map<String, Object> map = new HashMap<>();
                    map.put("type",1);
                    map.put("typeId",typeId);
                    map.put("isHistory",0);
                    List<TrainApprove> approveList = trainApproveMapper.getThisApproveListByQuery(map);
                    if(!approveList.isEmpty()){
                        String nextApproveEmplid = approveList.get(0).getApproveEmplid();
                        TrainNotice nextNoticeEntity = new TrainNotice();
                        nextNoticeEntity.setTypeId(typeId);
                        nextNoticeEntity.setUserId(nextApproveEmplid);
                        nextNoticeEntity.setType(11);
                        nextNoticeEntity.setStatus(0);
                        nextNoticeEntity.setCreateUser(approveEmplid);
                        nextNoticeEntity.setCreateTime(System.currentTimeMillis()/1000);
                        trainNoticeService.insertSelective(nextNoticeEntity);
                    }else {
                        //计划状态值与审批状态值设定差1
                        plan.setStatus(status - 1);
                        plan.setUpdateUser(approveEmplid);
                        plan.setUpdateTime(System.currentTimeMillis()/1000);
                        trainPlanService.updateSelective(plan);
                    }
                }

                //处理节点驳回情况,更新该计划审批状态和所有审批流下审批记录为旧申请
                if(status == 3){
                    plan.setStatus(status - 1);
                    plan.setUpdateUser(approveEmplid);
                    plan.setUpdateTime(System.currentTimeMillis()/1000);
                    trainPlanService.updateSelective(plan);

                    //获取改计划审批流最大历史版本序号,将当前的审批更新为历史版本
                    Map<String, Object> map = new HashMap<>();
                    map.put("type",1);
                    map.put("typeId",typeId);
                    Integer maxHistoryCode = trainApproveMapper.getMaxHistoryCode(map);
                    TrainApprove updateApprove = new TrainApprove();
                    updateApprove.setIsHistory(maxHistoryCode + 1);
                    updateApprove.setUpdateUser(approveEmplid);
                    updateApprove.setUpdateTime(System.currentTimeMillis()/1000);
                    Condition aCondition = new Condition(TrainApprove.class);
                    aCondition.createCriteria().andCondition("type = 1 and is_history = 0 and type_id = " + typeId);
                    this.updateByConditionSelective(updateApprove,aCondition);
                }
            }
        }
        if(type == 2){
            //临时需求审批更新
            TrainTemporaryDemand temporaryOne = trainTemporaryDemandService.selectById(typeId);
            if(temporaryOne.getStatus() == 1){
                TrainTemporaryDemand temporaryQuery = new TrainTemporaryDemand();
                temporaryQuery.setId(temporaryOne.getId());
                temporaryQuery.setStatus(status);
                temporaryQuery.setUpdateUser(approveEmplid);
                temporaryQuery.setUpdateTime(System.currentTimeMillis()/1000);
                trainTemporaryDemandService.updateSelective(temporaryQuery);

                //判断状态 2.同意 3.驳回
                if(status == 2){
                    //更新计划详情显示状态
                    TrainPlanDetail planDetailQuery = new TrainPlanDetail();
                    planDetailQuery.setId(temporaryOne.getPlanDetailId());
                    planDetailQuery.setIsShow(1);
                    planDetailQuery.setUpdateUser(approveEmplid);
                    planDetailQuery.setUpdateTime(System.currentTimeMillis()/1000);
                    trainPlanDetailService.updateSelective(planDetailQuery);
                }
                if(status == 3){
                    //获取临时需求审批流最大历史版本序号,将当前的审批更新为历史版本
                    Map<String, Object> map = new HashMap<>();
                    map.put("type",2);
                    map.put("typeId",typeId);
                    Integer maxHistoryCode = trainApproveMapper.getMaxHistoryCode(map);
                    TrainApprove updateApprove = new TrainApprove();
                    updateApprove.setIsHistory(maxHistoryCode + 1);
                    updateApprove.setUpdateUser(approveEmplid);
                    updateApprove.setUpdateTime(System.currentTimeMillis()/1000);
                    Condition aCondition = new Condition(TrainApprove.class);
                    aCondition.createCriteria().andCondition("type = 2 and is_history = 0 and type_id = " + typeId);
                    this.updateByConditionSelective(updateApprove,aCondition);
                }

                //更新审批代办通知状态
                TrainNotice noticeQuery = new TrainNotice();
                noticeQuery.setTypeId(typeId);
                noticeQuery.setType(10);
                noticeQuery.setUserId(approveEmplid);
                noticeQuery.setStatus(0);
                TrainNotice trainNotice = trainNoticeService.selectOne(noticeQuery);
                if(trainNotice != null){
                    trainNotice.setStatus(1);
                    trainNotice.setUpdateUser(approveEmplid);
                    trainNotice.setUpdateTime(System.currentTimeMillis()/1000);
                    trainNoticeService.updateSelective(trainNotice);
                }
            }
        }

        return 1;
    }
}