package com.dyys.hr.service.train.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.dagongma.kernel.commons.enums.ResultCode;
import com.dagongma.kernel.commons.exceptions.BusinessException;
import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.train.*;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class TrainTemporaryDemandServiceImpl extends AbstractCrudService<TrainTemporaryDemand, Long> implements TrainTemporaryDemandService {
    @Autowired
    private TrainTemporaryDemandMapper trainTemporaryDemandMapper;
    @Autowired
    private TrainPlanDetailMapper trainPlanDetailMapper;
    @Autowired
    private TrainPlanParticipantsMapper trainPlanParticipantsMapper;
    @Autowired
    private TrainPlanDetailService trainPlanDetailService;
    @Autowired
    private TrainPlanParticipantsService trainPlanParticipantsService;
    @Autowired
    private TrainPlanService trainPlanService;
    @Autowired
    private TrainNoticeService trainNoticeService;
    @Autowired
    private TrainApproveService trainApproveService;


    @Override
    public PageInfo<TrainTemporaryDemandPageVO> pageList(Map<String, Object> params){
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        List<TrainTemporaryDemandPageVO> voList = trainTemporaryDemandMapper.pageList(params);
        return new PageInfo<>(voList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(TrainTemporaryDemandDTO dto, String loginUserId){
        Long demandId = null;
        Long stamp = System.currentTimeMillis()/1000;
        //插入计划详情
        TrainPlanDetailDTO detailDTO = dto.getPlanDetail();
        detailDTO.setPlanId(dto.getPlanId());
        detailDTO.setCompanyCode(dto.getCompanyCode());
        detailDTO.setDepartmentCode(dto.getDepartmentCode());
        detailDTO.setFeedbackUserId(dto.getInitiator());
        detailDTO.setDemandType(1); // 临时需求

        TrainPlanDetail detailEntity = new TrainPlanDetail();
        BeanUtils.copyProperties(detailDTO,detailEntity);
        List<OrgDeptVO> coOrganizerList = detailDTO.getCoOrganizerList();
        detailEntity.setCoOrganizer(JSONUtil.toJsonStr(coOrganizerList));
        detailEntity.setIsShow(0);
        detailEntity.setCreateUser(loginUserId);
        detailEntity.setCreateTime(stamp);
        detailEntity.setUpdateUser(loginUserId);
        detailEntity.setUpdateTime(stamp);
        Long detailId = trainPlanDetailService.insertSelective(detailEntity);
        //循环插入参训人员
        List<TrainPlanParticipantsDTO> participantsDTOS = detailDTO.getParticipantsList();
        if(participantsDTOS != null && !participantsDTOS.isEmpty()){
            List<TrainPlanParticipants> participantsList = new ArrayList<>();
            for (TrainPlanParticipantsDTO participantsDTO : participantsDTOS){
                TrainPlanParticipants participantsEntity = new TrainPlanParticipants();
                BeanUtils.copyProperties(participantsDTO,participantsEntity);
                participantsEntity.setPlanDetailId(detailId);
                participantsEntity.setCreateUser(loginUserId);
                participantsEntity.setCreateTime(System.currentTimeMillis()/1000);
                participantsList.add(participantsEntity);
            }
            trainPlanParticipantsService.insertBatchSelective(participantsList);
        }
        //插入临时需求，更新计划详情id
        TrainTemporaryDemand demandEntity = new TrainTemporaryDemand();
        BeanUtils.copyProperties(dto,demandEntity);
        demandEntity.setPlanDetailId(detailId);
        demandEntity.setStatus(1);
        demandEntity.setCreateUser(loginUserId);
        demandEntity.setCreateTime(System.currentTimeMillis()/1000);
        List<FileDTO> fileList = dto.getFileList();
        demandEntity.setFileList(JSONUtil.toJsonStr(fileList));
        demandId = this.insertSelective(demandEntity);

        //发送计划创建人审批代办通知
        String initiator = trainPlanService.selectById(detailDTO.getPlanId()).getInitiator();
        TrainNotice trainNotice = new TrainNotice();
        trainNotice.setTypeId(demandId);
        trainNotice.setUserId(initiator);
        trainNotice.setType(10);
        trainNotice.setStatus(0);
        trainNotice.setCreateUser(loginUserId);
        trainNotice.setCreateTime(System.currentTimeMillis()/1000);
        trainNoticeService.insertSelective(trainNotice);

        //初始化审批记录表
        TrainApprove trainApprove = new TrainApprove();
        trainApprove.setTypeId(demandId);
        trainApprove.setType(2);
        trainApprove.setApproveEmplid(initiator);
        trainApprove.setNodeName("审核人");
        trainApprove.setSortNum(0);
        trainApprove.setStatus(1);
        trainApprove.setCreateUser(loginUserId);
        trainApprove.setCreateTime(System.currentTimeMillis()/1000);
        trainApproveService.insertSelective(trainApprove);
        return demandId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(TrainTemporaryDemandDTO dto,String loginUserId){
        TrainTemporaryDemand temporaryDemand = this.selectById(dto.getId());
        if(temporaryDemand.getStatus() != 3){
            throw new BusinessException(ResultCode.EXCEPTION,"该需求状态不能编辑!");
        }
        Long stamp = System.currentTimeMillis()/1000;

        //删除旧详情数据,插入新详情
        Map<String, Object> map = new HashMap<>();
        map.put("id",temporaryDemand.getPlanDetailId());
        trainPlanDetailMapper.deleteByParams(map);

        TrainPlanDetailDTO detailDTO = dto.getPlanDetail();
        detailDTO.setPlanId(dto.getPlanId());
        detailDTO.setCompanyCode(dto.getCompanyCode());
        detailDTO.setDepartmentCode(dto.getDepartmentCode());
        detailDTO.setFeedbackUserId(dto.getInitiator());
        detailDTO.setDemandType(1); // 临时需求

        TrainPlanDetail detailEntity = new TrainPlanDetail();
        BeanUtils.copyProperties(detailDTO,detailEntity);
        List<OrgDeptVO> coOrganizerList = detailDTO.getCoOrganizerList();
        detailEntity.setCoOrganizer(JSONUtil.toJsonStr(coOrganizerList));
        detailEntity.setIsShow(0);
        detailEntity.setCreateUser(loginUserId);
        detailEntity.setCreateTime(stamp);
        detailEntity.setUpdateUser(loginUserId);
        detailEntity.setUpdateTime(stamp);
        Long newDetailId = trainPlanDetailService.insertSelective(detailEntity);

        //删除旧参训人员数据,插入新
        map.put("planDetailId",temporaryDemand.getPlanDetailId());
        trainPlanParticipantsMapper.deleteByParams(map);
        List<TrainPlanParticipantsDTO> participantsDTOS = detailDTO.getParticipantsList();
        if(participantsDTOS != null && !participantsDTOS.isEmpty()){
            List<TrainPlanParticipants> participantsList = new ArrayList<>();
            for (TrainPlanParticipantsDTO participantsDTO : participantsDTOS){
                TrainPlanParticipants participantsEntity = new TrainPlanParticipants();
                BeanUtils.copyProperties(participantsDTO,participantsEntity);
                participantsEntity.setPlanDetailId(newDetailId);
                participantsEntity.setCreateUser(loginUserId);
                participantsEntity.setCreateTime(stamp);
                participantsList.add(participantsEntity);
            }
            trainPlanParticipantsService.insertBatchSelective(participantsList);
        }

        //更新临时需求表
        TrainTemporaryDemand demandUpdateEntity = new TrainTemporaryDemand();
        BeanUtils.copyProperties(dto,demandUpdateEntity);
        demandUpdateEntity.setPlanDetailId(newDetailId);
        demandUpdateEntity.setUpdateUser(loginUserId);
        demandUpdateEntity.setUpdateTime(stamp);
        this.updateSelective(demandUpdateEntity);
        Long demandId = dto.getId();

        //发送计划创建人审批代办通知
        String initiator = trainPlanService.selectById(detailDTO.getPlanId()).getInitiator();
        TrainNotice trainNotice = new TrainNotice();
        trainNotice.setTypeId(demandId);
        trainNotice.setUserId(initiator);
        trainNotice.setType(10);
        trainNotice.setStatus(0);
        trainNotice.setCreateUser(loginUserId);
        trainNotice.setCreateTime(System.currentTimeMillis()/1000);
        trainNoticeService.insertSelective(trainNotice);

        //初始化审批记录表
        TrainApprove trainApprove = new TrainApprove();
        trainApprove.setTypeId(demandId);
        trainApprove.setType(2);
        trainApprove.setApproveEmplid(initiator);
        trainApprove.setNodeName("审核人");
        trainApprove.setSortNum(0);
        trainApprove.setStatus(1);
        trainApprove.setIsHistory(0);
        trainApprove.setCreateUser(loginUserId);
        trainApprove.setCreateTime(System.currentTimeMillis()/1000);
        trainApproveService.insertSelective(trainApprove);
        return 1;
    }

    @Override
    public TrainTemporaryDemandDetailVO getDetail(Long demandId){
        Map<String, Object> params = new HashMap<>();
        params.put("id",demandId);
        TrainTemporaryDemandDetailVO demand = trainTemporaryDemandMapper.getDetailByQuery(params);
        if(demand != null){
            TrainTemporaryDemand entity = this.selectById(demandId);
            BeanUtils.copyProperties(entity,demand);
            JSONArray objects = JSONUtil.parseArray(entity.getFileList());
            demand.setFileList(JSONUtil.toList(objects,FileDTO.class));
            Map<String, Object> dQuery = new HashMap<>();
            dQuery.put("id",demand.getPlanDetailId());
            TrainPlanDetailVO detail = trainPlanDetailMapper.getDetail(dQuery);
            TrainPlanDetail detailEntity = trainPlanDetailService.selectById(detail.getId());
            JSONArray coObject = JSONUtil.parseArray(detailEntity.getCoOrganizer());
            detail.setCoOrganizerList(JSONUtil.toList(coObject,OrgDeptVO.class));
            Map<String, Object> query = new HashMap<>();
            query.put("plan_detail_id",detail.getId());
            detail.setParticipantsList(trainPlanParticipantsMapper.getList(query));
            demand.setPlanDetail(detail);

            //获取审批进度
            Map<String, Object> map = new HashMap<>();
            map.put("type",2);
            map.put("typeId",demandId);
            demand.setApproveList(trainApproveService.getListByQuery(map));
        }
        return demand;
    }

    @Override
    public PageInfo<AdminTemporaryDemandListVO> adminTemporaryDemandList(Map<String, Object> params){
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        List<AdminTemporaryDemandListVO> voList = trainTemporaryDemandMapper.adminTemporaryDemandList(params);
        return new PageInfo<>(voList);
    }
}