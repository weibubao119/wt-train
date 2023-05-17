package com.dyys.hr.service.train.impl;

import cn.hutool.core.convert.Convert;
import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.train.TrainMaterialsMapper;
import com.dyys.hr.dao.train.TrainNoticeMapper;
import com.dyys.hr.dao.train.TrainProgramsParticipantsMapper;
import com.dyys.hr.dto.train.IdDTO;
import com.dyys.hr.dto.train.TrainMaterialsDTO;
import com.dyys.hr.entity.train.*;
import com.dyys.hr.service.train.*;
import com.dyys.hr.vo.train.TrainMaterialsLearnVO;
import com.dyys.hr.vo.train.TrainMaterialsVO;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class TrainMaterialsServiceImpl extends AbstractCrudService<TrainMaterials, Long> implements TrainMaterialsService {
    @Autowired
    private TrainMaterialsMapper trainMaterialsMapper;
    @Autowired
    private TrainProgramsParticipantsMapper trainProgramsParticipantsMapper;
    @Autowired
    private TrainNoticeMapper trainNoticeMapper;
    @Autowired
    private TrainNoticeService trainNoticeService;
    @Autowired
    private TrainProgramsService trainProgramsService;
    @Autowired
    private TrainMaterialsLearningRecordService trainMaterialsLearningRecordService;


    @Override
    public PageInfo<TrainMaterialsVO> pageList(Map<String, Object> params){
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        List<TrainMaterialsVO> voList = trainMaterialsMapper.pageList(params);
        return new PageInfo<>(voList);
    }

    @Override
    public Long save(TrainMaterialsDTO dto, String loginUserId){
        TrainMaterials entity = new TrainMaterials();
        BeanUtils.copyProperties(dto,entity);
        entity.setCreateUser(loginUserId);
        entity.setCreateTime(System.currentTimeMillis()/1000);
        return this.insertSelective(entity);
    }


    /**
     * 批量发布
     * @param dtoList
     * @param loginUserId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean batchChangeStatus(List<IdDTO> dtoList, String loginUserId){
        boolean result = false;
        if(dtoList != null && !dtoList.isEmpty()){
            //循环给参训人员发送通知
            String ids = "";
            for (IdDTO dto : dtoList){
                ids = ids.concat("," + dto.getId().toString());
            }
            ids = ids.substring(1);
            //批量更新材料状态为已报名
            TrainMaterials updateEntity = new TrainMaterials();
            updateEntity.setStatus(1);
            Condition condition = new Condition(TrainMaterials.class);
            condition.createCriteria().andCondition("id in (" + ids + ")");
            this.updateByConditionSelective(updateEntity,condition);
            result = true;
        }
        return result;
    }

    /**
     * 推送学习
     * @param programsId
     * @param loginUserId
     * @return
     */
    @Override
    public Boolean pushLearningNotice(Long programsId,String loginUserId){
        //获取所有已确认参训人员进行推送
        Map<String, Object> map = new HashMap<>();
        map.put("programsId",programsId);
        map.put("status",1);
        List<String> userIds = trainProgramsParticipantsMapper.getUserIdsByQuery(map);
        List<TrainNotice> noticeList = new ArrayList<>();
        if(userIds != null && !userIds.isEmpty()){
            //循环给参训人员发送通知
            Map<String, Object> dataParams = new HashMap<>();
            for (String userId : userIds){
                TrainNotice trainNotice = new TrainNotice();
                trainNotice.setTypeId(programsId);
                trainNotice.setUserId(userId);
                trainNotice.setType(12);
                trainNotice.setStatus(0);
                trainNotice.setCreateUser(loginUserId);
                trainNotice.setCreateTime(System.currentTimeMillis()/1000);
                noticeList.add(trainNotice);

                //自助平台插入代办
                dataParams.put("typeId",11);
                dataParams.put("type",11);
                dataParams.put("emplId",userId);
                dataParams.put("url","http://218.13.91.107:38000/kn-front/emp/center");
                trainNoticeMapper.createEmployeeSelfNotice(dataParams);
            }
        }
        return trainNoticeService.insertBatchSelective(noticeList);
    }

    /**
     * 材料学习页数据
     * @param programsId
     * @param loginEmplId
     * @return
     */
    @Override
    public TrainMaterialsLearnVO materialsLearningPageData(Long programsId, String loginEmplId){
        //获取培训班数据
        TrainPrograms programsEntity = trainProgramsService.selectById(programsId);
        TrainMaterialsLearnVO vo = new TrainMaterialsLearnVO();
        if(entity != null){
            vo.setProgramsId(programsId);
            vo.setTitle(programsEntity.getTrainName() + "-学习资料");
            //查询共学人数
            vo.setLearnedNum(trainMaterialsMapper.totalLearningNumByProgramsId(programsId));
            List<TrainMaterialsVO> materialsVOList = trainMaterialsMapper.getMaterialsByProgramsId(programsId);
            for (TrainMaterialsVO  materialsVO : materialsVOList){
                //获取当前用户该资料的学习完成状态
                TrainMaterialsLearningRecord queryEntity = new TrainMaterialsLearningRecord();
                queryEntity.setMaterialsId(materialsVO.getId());
                queryEntity.setType(2);
                queryEntity.setUserId(loginEmplId);
                TrainMaterialsLearningRecord selectOne = trainMaterialsLearningRecordService.selectOne(queryEntity);
                materialsVO.setLearnedStatus(0);
                if(selectOne != null){
                    materialsVO.setLearnedStatus(selectOne.getStatus());
                }
            }
            vo.setMaterialsList(materialsVOList);
        }
        return vo;
    }
}