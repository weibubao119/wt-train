package com.dyys.hr.service.train.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.train.TrainDemandCollectMapper;
import com.dyys.hr.dao.train.TrainDemandFeedbackMapper;
import com.dyys.hr.dto.train.FileDTO;
import com.dyys.hr.dto.train.TrainDemandCollectDTO;
import com.dyys.hr.dto.train.TrainDemandFeedbackDTO;
import com.dyys.hr.entity.train.*;
import com.dyys.hr.service.staff.IStaffUserInfoService;
import com.dyys.hr.service.train.TrainDemandCollectService;
import com.dyys.hr.service.train.TrainDemandFeedbackLogService;
import com.dyys.hr.service.train.TrainDemandFeedbackService;
import com.dyys.hr.service.train.TrainNoticeService;
import com.dyys.hr.vo.common.PsPersionVO;
import com.dyys.hr.vo.train.*;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class TrainDemandCollectServiceImpl extends AbstractCrudService<TrainDemandCollect, Long> implements TrainDemandCollectService {
    @Autowired
    private TrainDemandCollectMapper trainDemandCollectMapper;
    @Autowired
    private TrainDemandFeedbackMapper trainDemandFeedbackMapper;
    @Autowired
    private TrainDemandFeedbackService trainDemandFeedbackService;
    @Autowired
    private IStaffUserInfoService iStaffUserInfoService;
    @Autowired
    private TrainDemandFeedbackLogService trainDemandFeedbackLogService;
    @Autowired
    private TrainNoticeService trainNoticeService;

    @Value("${portal-config.domain}")
    private String jumpDomain;

    @Override
    public PageInfo<TrainDemandCollectVO> pageList(Map<String, Object> params){
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        List<TrainDemandCollectVO> voList = trainDemandCollectMapper.pageList(params);
        return new PageInfo<>(voList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(TrainDemandCollectDTO dto,String loginUserId){
        //新增需求
        TrainDemandCollect demandEntity = new TrainDemandCollect();
        BeanUtils.copyProperties(dto,demandEntity);
        demandEntity.setCreateTime(System.currentTimeMillis()/1000);
        demandEntity.setCreateUser(loginUserId);
        List<FileDTO> fileList = dto.getFileList();
        demandEntity.setFileList(JSONUtil.toJsonStr(fileList));
        Long demandId = this.insertSelective(demandEntity);
        if(demandId > 0){
            //新增需求反馈人
            List<TrainDemandFeedbackDTO> userList = dto.getFeedbackUserList();
            if(userList != null && !userList.isEmpty()){
                List<TrainDemandFeedback> feedbackList = new ArrayList<>();
                HashMap<String, Object> map = new HashMap<>();
                String loginUserName = iStaffUserInfoService.getUserInfoById(loginUserId).getEmplName();
                for (TrainDemandFeedbackDTO  userDTO : userList){
                    if(map.get(userDTO.getFeedbackUserId()) == null){
                        map.put(userDTO.getFeedbackUserId(),1);
                        TrainDemandFeedback userEntity = new TrainDemandFeedback();
                        BeanUtils.copyProperties(userDTO,userEntity);
                        userEntity.setDemandId(demandId);
                        userEntity.setStatus(0);
                        userEntity.setCreateTime(System.currentTimeMillis()/1000);
                        userEntity.setCreateUser(loginUserId);
                        feedbackList.add(userEntity);

                        //自助平台插入代办
                        trainNoticeService.insertHcmPortalMessage("培训系统","需求反馈",userDTO.getFeedbackUserId(),
                                1,jumpDomain + "/kn-front/demand-management", DateTime.now(), dto.getDemandName() + "待反馈需求",
                                0,loginUserId,loginUserName);

                    }
                }
                trainDemandFeedbackService.insertBatchSelective(feedbackList);
            }
        }
        return demandId;
    }

    @Override
    public TrainDemandCollectDetailVO selectByDemandId(Long demandId){
        TrainDemandCollect entity = trainDemandCollectMapper.selectByDemandId(demandId);
        TrainDemandCollectDetailVO vo = new TrainDemandCollectDetailVO();
        if(entity != null){
            PsPersionVO userInfoById = iStaffUserInfoService.getUserInfoById(entity.getInitiator());
            BeanUtils.copyProperties(entity,vo);
            if (!ObjectUtil.isEmpty(userInfoById)){
                vo.setInitiatorName(userInfoById.getEmplName());
            }
            JSONArray objects = JSONUtil.parseArray(entity.getFileList());
            vo.setFileList(JSONUtil.toList(objects,FileDTO.class));
            Map<String, Object> params = new HashMap<>();
            params.put("demand_id",vo.getId());
            List<TrainDemandFeedbackVO> feedbackList = trainDemandFeedbackMapper.feedbackList(params);
            if(!CollectionUtil.isEmpty(feedbackList)){
                for (TrainDemandFeedbackVO demandFeedbackVO : feedbackList) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = null;
                    if (!ObjectUtil.isEmpty(demandFeedbackVO.getFeedbackTime())){
                        date = new Date(demandFeedbackVO.getFeedbackTime()*1000);
                        demandFeedbackVO.setFeedbackDate(simpleDateFormat.format(date));
                    }
                    //查询反馈操作日志列表
                    params.put("feedbackId",demandFeedbackVO.getId());
                    demandFeedbackVO.setFeedbackLogList(trainDemandFeedbackLogService.getList(params));
                }
            }
            vo.setFeedbackUserList(feedbackList);

        }
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer update(TrainDemandCollectDTO dto){
        return null;
    }

    @Override
    public Integer deleteByDemandId(Long demandId){
        return trainDemandCollectMapper.deleteByDemandId(demandId);
    }

    @Override
    public Integer closeCollect(Long demandId){
        return trainDemandCollectMapper.closeCollect(demandId);
    }

    @Override
    public PageInfo<TrainRelateDemandCollectVO> relateDemandList(Map<String, Object> params){
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        List<TrainRelateDemandCollectVO> voList = trainDemandCollectMapper.relateDemandList(params);
        return new PageInfo<>(voList);
    }
}