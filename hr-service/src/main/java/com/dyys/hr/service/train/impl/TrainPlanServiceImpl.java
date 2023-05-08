package com.dyys.hr.service.train.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.dagongma.kernel.commons.enums.ResultCode;
import com.dagongma.kernel.commons.exceptions.BusinessException;
import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.train.TrainPlanDetailMapper;
import com.dyys.hr.dao.train.TrainPlanMapper;
import com.dyys.hr.dao.train.TrainPlanParticipantsMapper;
import com.dyys.hr.dto.train.*;
import com.dyys.hr.entity.train.*;
import com.dyys.hr.service.train.*;
import com.dyys.hr.vo.common.OrgDeptVO;
import com.dyys.hr.vo.train.*;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
@Slf4j
public class TrainPlanServiceImpl extends AbstractCrudService<TrainPlan, Long> implements TrainPlanService {
    @Autowired
    private TrainPlanMapper trainPlanMapper;
    @Autowired
    private TrainPlanDetailMapper trainPlanDetailMapper;
    @Autowired
    private TrainPlanParticipantsMapper trainPlanParticipantsMapper;
    @Autowired
    private TrainPlanDetailService trainPlanDetailService;
    @Autowired
    private TrainPlanParticipantsService trainPlanParticipantsService;
    @Autowired
    private TrainApproveFlowNodeService trainApproveFlowNodeService;
    @Autowired
    private TrainNoticeService trainNoticeService;
    @Autowired
    private TrainApproveService trainApproveService;

    @Override
    public PageInfo<TrainPlanVO> pageList(Map<String, Object> params){
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        List<TrainPlanVO> voList = trainPlanMapper.pageList(params);
        return new PageInfo<>(voList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(TrainPlanDTO dto, String loginUserId){
        //插入计划
        TrainPlan planEntity = new TrainPlan();
        BeanUtils.copyProperties(dto,planEntity);
        planEntity.setStatus(0);
        planEntity.setCreateUser(loginUserId);
        planEntity.setCreateTime(System.currentTimeMillis()/1000);
        List<FileDTO> fileList = dto.getFileList();
        planEntity.setFileList(JSONUtil.toJsonStr(fileList));
        Long planId = this.insertSelective(planEntity);
        //循环插入计划详情
        List<TrainPlanDetailDTO> detailDTOS = dto.getPlanDetailList();
        if(detailDTOS != null && !detailDTOS.isEmpty()){
            for (TrainPlanDetailDTO detailDTO : detailDTOS){
                TrainPlanDetail detailEntity = new TrainPlanDetail();
                BeanUtils.copyProperties(detailDTO,detailEntity);
                List<OrgDeptVO> coOrganizerList = detailDTO.getCoOrganizerList();
                detailEntity.setCoOrganizer(JSONUtil.toJsonStr(coOrganizerList));
                detailEntity.setDemandType(0); // 创建计划的需求类型都默认为 计划培训
                detailEntity.setPlanId(planId);
                detailEntity.setCreateUser(loginUserId);
                detailEntity.setCreateTime(System.currentTimeMillis()/1000);
                Long detailId = trainPlanDetailService.insertSelective(detailEntity);
                //循环插入参训人员
                List<TrainPlanParticipantsDTO> participantsDTOS = detailDTO.getParticipantsList();
                if (participantsDTOS != null && !participantsDTOS.isEmpty()) {
                    List<TrainPlanParticipants> participantsList = new ArrayList<>();
                    HashMap<String, Object> map = new HashMap<>();
                    for (TrainPlanParticipantsDTO participantsDTO : participantsDTOS){
                        if(map.get(participantsDTO.getUserId()) == null){
                            map.put(participantsDTO.getUserId(),1);
                            TrainPlanParticipants participantsEntity = new TrainPlanParticipants();
                            BeanUtils.copyProperties(participantsDTO,participantsEntity);
                            participantsEntity.setPlanDetailId(detailId);
                            participantsEntity.setCreateUser(loginUserId);
                            participantsEntity.setCreateTime(System.currentTimeMillis()/1000);
                            participantsList.add(participantsEntity);
                        }
                    }
                    trainPlanParticipantsService.insertBatchSelective(participantsList);
                }
            }
        }
//        //处理审批流
//        Integer approveFlowId = dto.getApproveFlowId();
//        Map<String, Object> query = new HashMap<>();
//        query.put("flowId",approveFlowId);
//        List<TrainApproveFlowNode> nodeList = trainApproveFlowNodeService.getNodeListByQuery(query);
//        for (TrainApproveFlowNode node : nodeList){
//            //初始化审批记录表
//            TrainApprove trainApprove = new TrainApprove();
//            trainApprove.setTypeId(planId);
//            trainApprove.setType(1);
//            trainApprove.setApproveEmplid(node.getEmplId());
//            trainApprove.setNodeName(node.getName());
//            trainApprove.setSortNum(node.getSort());
//            trainApprove.setStatus(1);
//            trainApprove.setIsHistory(0);
//            trainApprove.setCreateUser(loginUserId);
//            trainApprove.setCreateTime(System.currentTimeMillis()/1000);
//            trainApproveService.insertSelective(trainApprove);
//        }
//        //给节点第一人发送代办
//        TrainNotice trainNotice = new TrainNotice();
//        trainNotice.setTypeId(planId);
//        trainNotice.setUserId(nodeList.get(0).getEmplId());
//        trainNotice.setType(11);
//        trainNotice.setStatus(0);
//        trainNotice.setCreateUser(loginUserId);
//        trainNotice.setCreateTime(System.currentTimeMillis()/1000);
//        trainNoticeService.insertSelective(trainNotice);
        return planId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long storage(TrainPlanDTO dto, String loginUserId){
        //判读是首次暂存的话则新增,否则更新
        Long planId;
        if(dto.getId() != null){
            planId = dto.getId();
            Map<String, Object> params = new HashMap<>();
            params.put("planId",planId);
            List<TrainPlanDetailVO> detailVos = trainPlanDetailMapper.getDetailList(params);
            for (TrainPlanDetailVO detail : detailVos){
                params.put("planDetailId",detail.getId());
                trainPlanParticipantsMapper.deleteByParams(params);
            }
            trainPlanDetailMapper.deleteByParams(params);

            //循环插入计划详情
            List<TrainPlanDetailDTO> detailDTOS = dto.getPlanDetailList();
            if(detailDTOS != null && !detailDTOS.isEmpty()){
                for (TrainPlanDetailDTO detailDTO : detailDTOS){
                    TrainPlanDetail detailEntity = new TrainPlanDetail();
                    BeanUtils.copyProperties(detailDTO,detailEntity);
                    List<OrgDeptVO> coOrganizerList = detailDTO.getCoOrganizerList();
                    detailEntity.setCoOrganizer(JSONUtil.toJsonStr(coOrganizerList));
                    detailEntity.setDemandType(0); // 创建计划的需求类型都默认为 计划培训
                    detailEntity.setPlanId(planId);
                    detailEntity.setCreateUser(loginUserId);
                    detailEntity.setCreateTime(System.currentTimeMillis()/1000);
                    Long detailId = trainPlanDetailService.insertSelective(detailEntity);
                    //循环插入参训人员
                    List<TrainPlanParticipantsDTO> participantsDTOS = detailDTO.getParticipantsList();
                    if (participantsDTOS != null && !participantsDTOS.isEmpty()) {
                        List<TrainPlanParticipants> participantsList = new ArrayList<>();
                        HashMap<String, Object> map = new HashMap<>();
                        for (TrainPlanParticipantsDTO participantsDTO : participantsDTOS){
                            if(map.get(participantsDTO.getUserId()) == null){
                                map.put(participantsDTO.getUserId(),1);
                                TrainPlanParticipants participantsEntity = new TrainPlanParticipants();
                                BeanUtils.copyProperties(participantsDTO,participantsEntity);
                                participantsEntity.setPlanDetailId(detailId);
                                participantsEntity.setCreateUser(loginUserId);
                                participantsEntity.setCreateTime(System.currentTimeMillis()/1000);
                                participantsList.add(participantsEntity);
                            }
                        }
                        trainPlanParticipantsService.insertBatchSelective(participantsList);
                    }
                }
            }

            //更新计划
            TrainPlan updatePlanEntity = new TrainPlan();
            BeanUtils.copyProperties(dto,updatePlanEntity);
            updatePlanEntity.setStatus(4);
            updatePlanEntity.setUpdateUser(loginUserId);
            updatePlanEntity.setUpdateTime(System.currentTimeMillis()/1000);
            List<FileDTO> fileList = dto.getFileList();
            updatePlanEntity.setFileList(JSONUtil.toJsonStr(fileList));
            this.updateSelective(updatePlanEntity);
        }
        else{
            //插入计划
            TrainPlan planEntity = new TrainPlan();
            BeanUtils.copyProperties(dto,planEntity);
            planEntity.setStatus(4);
            planEntity.setCreateUser(loginUserId);
            planEntity.setCreateTime(System.currentTimeMillis()/1000);
            List<FileDTO> fileList = dto.getFileList();
            planEntity.setFileList(JSONUtil.toJsonStr(fileList));
            planId = this.insertSelective(planEntity);
            //循环插入计划详情
            List<TrainPlanDetailDTO> detailDTOS = dto.getPlanDetailList();
            if(detailDTOS != null && !detailDTOS.isEmpty()){
                for (TrainPlanDetailDTO detailDTO : detailDTOS){
                    TrainPlanDetail detailEntity = new TrainPlanDetail();
                    BeanUtils.copyProperties(detailDTO,detailEntity);
                    List<OrgDeptVO> coOrganizerList = detailDTO.getCoOrganizerList();
                    detailEntity.setCoOrganizer(JSONUtil.toJsonStr(coOrganizerList));
                    detailEntity.setDemandType(0); // 创建计划的需求类型都默认为 计划培训
                    detailEntity.setPlanId(planId);
                    detailEntity.setCreateUser(loginUserId);
                    detailEntity.setCreateTime(System.currentTimeMillis()/1000);
                    Long detailId = trainPlanDetailService.insertSelective(detailEntity);
                    //循环插入参训人员
                    List<TrainPlanParticipantsDTO> participantsDTOS = detailDTO.getParticipantsList();
                    if (participantsDTOS != null && !participantsDTOS.isEmpty()) {
                        List<TrainPlanParticipants> participantsList = new ArrayList<>();
                        HashMap<String, Object> map = new HashMap<>();
                        for (TrainPlanParticipantsDTO participantsDTO : participantsDTOS){
                            if(map.get(participantsDTO.getUserId()) == null){
                                map.put(participantsDTO.getUserId(),1);
                                TrainPlanParticipants participantsEntity = new TrainPlanParticipants();
                                BeanUtils.copyProperties(participantsDTO,participantsEntity);
                                participantsEntity.setPlanDetailId(detailId);
                                participantsEntity.setCreateUser(loginUserId);
                                participantsEntity.setCreateTime(System.currentTimeMillis()/1000);
                                participantsList.add(participantsEntity);
                            }
                        }
                        trainPlanParticipantsService.insertBatchSelective(participantsList);
                    }
                }
            }
        }

        return planId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer update(TrainPlanDTO dto, String loginUserId){
        //判断状态
        Long planId = dto.getId();
//        TrainPlan trainPlan = this.selectById(dto.getId());
//        if(trainPlan.getStatus() != 2){
//            throw new BusinessException(ResultCode.EXCEPTION,"该计划状态不能编辑!");
//        }

        //删除旧详情和参训人员,插入新数据。先删除参训人员后删计划详情
        Map<String, Object> params = new HashMap<>();
        params.put("planId",planId);
        List<TrainPlanDetailVO> detailVos = trainPlanDetailMapper.getDetailList(params);
        for (TrainPlanDetailVO detail : detailVos){
            params.put("planDetailId",detail.getId());
            trainPlanParticipantsMapper.deleteByParams(params);
        }
        trainPlanDetailMapper.deleteByParams(params);

        //循环插入计划详情
        List<TrainPlanDetailDTO> detailDTOS = dto.getPlanDetailList();
        if(detailDTOS != null && !detailDTOS.isEmpty()){
            for (TrainPlanDetailDTO detailDTO : detailDTOS){
                TrainPlanDetail detailEntity = new TrainPlanDetail();
                BeanUtils.copyProperties(detailDTO,detailEntity);
                List<OrgDeptVO> coOrganizerList = detailDTO.getCoOrganizerList();
                detailEntity.setCoOrganizer(JSONUtil.toJsonStr(coOrganizerList));
                detailEntity.setDemandType(0); // 创建计划的需求类型都默认为 计划培训
                detailEntity.setPlanId(planId);
                detailEntity.setCreateUser(loginUserId);
                detailEntity.setCreateTime(System.currentTimeMillis()/1000);
                Long detailId = trainPlanDetailService.insertSelective(detailEntity);
                //循环插入参训人员
                List<TrainPlanParticipantsDTO> participantsDTOS = detailDTO.getParticipantsList();
                if (participantsDTOS != null && !participantsDTOS.isEmpty()) {
                    List<TrainPlanParticipants> participantsList = new ArrayList<>();
                    HashMap<String, Object> map = new HashMap<>();
                    for (TrainPlanParticipantsDTO participantsDTO : participantsDTOS){
                        if(map.get(participantsDTO.getUserId()) == null){
                            map.put(participantsDTO.getUserId(),1);
                            TrainPlanParticipants participantsEntity = new TrainPlanParticipants();
                            BeanUtils.copyProperties(participantsDTO,participantsEntity);
                            participantsEntity.setPlanDetailId(detailId);
                            participantsEntity.setCreateUser(loginUserId);
                            participantsEntity.setCreateTime(System.currentTimeMillis()/1000);
                            participantsList.add(participantsEntity);
                        }
                    }
                    trainPlanParticipantsService.insertBatchSelective(participantsList);
                }
            }
        }

        //更新计划
        TrainPlan updatePlanEntity = new TrainPlan();
        BeanUtils.copyProperties(dto,updatePlanEntity);
        updatePlanEntity.setStatus(0);
        updatePlanEntity.setUpdateUser(loginUserId);
        updatePlanEntity.setUpdateTime(System.currentTimeMillis()/1000);
        List<FileDTO> fileList = dto.getFileList();
        updatePlanEntity.setFileList(JSONUtil.toJsonStr(fileList));
        Integer updateRes = this.updateSelective(updatePlanEntity);

//        //处理审批流
//        Integer approveFlowId = dto.getApproveFlowId();
//        Map<String, Object> query = new HashMap<>();
//        query.put("flowId",approveFlowId);
//        List<TrainApproveFlowNode> nodeList = trainApproveFlowNodeService.getNodeListByQuery(query);
//        for (TrainApproveFlowNode node : nodeList){
//            //初始化审批记录表
//            TrainApprove trainApprove = new TrainApprove();
//            trainApprove.setTypeId(planId);
//            trainApprove.setType(1);
//            trainApprove.setApproveEmplid(node.getEmplId());
//            trainApprove.setNodeName(node.getName());
//            trainApprove.setSortNum(node.getSort());
//            trainApprove.setStatus(1);
//            trainApprove.setIsHistory(0);
//            trainApprove.setCreateUser(loginUserId);
//            trainApprove.setCreateTime(System.currentTimeMillis()/1000);
//            trainApproveService.insertSelective(trainApprove);
//        }
//        //给节点第一人发送代办
//        TrainNotice trainNotice = new TrainNotice();
//        trainNotice.setTypeId(planId);
//        trainNotice.setUserId(nodeList.get(0).getEmplId());
//        trainNotice.setType(11);
//        trainNotice.setStatus(0);
//        trainNotice.setCreateUser(loginUserId);
//        trainNotice.setCreateTime(System.currentTimeMillis()/1000);
//        trainNoticeService.insertSelective(trainNotice);
        return updateRes;
    }

    @Override
    public TrainPlanFormVO getDetail(Long planId){
        Map<String, Object> params = new HashMap<>();
        params.put("id",planId);
        TrainPlanFormVO plan = trainPlanMapper.getDetailByQuery(params);
        if(plan != null){
            TrainPlan entity = this.selectById(planId);
            BeanUtils.copyProperties(entity,plan);
            JSONArray objects = JSONUtil.parseArray(entity.getFileList());
            plan.setFileList(JSONUtil.toList(objects,FileDTO.class));
            Map<String, Object> dQuery = new HashMap<>();
            dQuery.put("plan_id",planId);
            List<TrainPlanDetailVO> detailVos = trainPlanDetailMapper.getDetailList(dQuery);
            for (TrainPlanDetailVO detail : detailVos){
                TrainPlanDetail detailEntity = trainPlanDetailService.selectById(detail.getId());
                JSONArray coObject = JSONUtil.parseArray(detailEntity.getCoOrganizer());
                detail.setCoOrganizerList(JSONUtil.toList(coObject,OrgDeptVO.class));
                Map<String, Object> query = new HashMap<>();
                query.put("plan_detail_id",detail.getId());
                detail.setParticipantsList(trainPlanParticipantsMapper.getList(query));
            }
            plan.setPlanDetailList(detailVos);

//            //获取审批进度
//            Map<String, Object> map = new HashMap<>();
//            map.put("type",1);
//            map.put("typeId",planId);
//            plan.setApproveList(trainApproveService.getListByQuery(map));
        }
        return plan;
    }

    @Override
    public PageInfo<Map<String,Object>> selectList(Map<String, Object> params){
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        List<Map<String,Object>> voList = trainPlanMapper.selectList(params);
        return new PageInfo<>(voList);
    }

}