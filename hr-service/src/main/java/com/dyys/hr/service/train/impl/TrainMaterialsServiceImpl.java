package com.dyys.hr.service.train.impl;

import cn.hutool.core.convert.Convert;
import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.train.*;
import com.dyys.hr.dto.train.TrainBaseCourseMaterialsDTO;
import com.dyys.hr.dto.train.TrainMaterialsDTO;
import com.dyys.hr.entity.train.*;
import com.dyys.hr.service.train.*;
import com.dyys.hr.vo.train.TrainMaterialsLearnCategoryVO;
import com.dyys.hr.vo.train.TrainMaterialsLearnVO;
import com.dyys.hr.vo.train.TrainMaterialsVO;
import com.dyys.hr.vo.train.TrainProgramsCourseDetailVO;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
    @Autowired
    private TrainProgramsCourseMapper trainProgramsCourseMapper;
    @Autowired
    private TrainBaseCourseMaterialsMapper trainBaseCourseMaterialsMapper;

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
     * @param programsId
     * @param loginUserId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer batchChangeStatus(Long programsId, String loginUserId){
        TrainMaterials updateMaterials = new TrainMaterials();
        updateMaterials.setStatus(1);
        updateMaterials.setUpdateUser(loginUserId);
        updateMaterials.setUpdateTime(System.currentTimeMillis()/1000);
        Condition condition = new Condition(TrainMaterials.class);
        condition.createCriteria().andCondition("programs_id = " + programsId);
        return this.updateByConditionSelective(updateMaterials,condition);
    }


    /**
     * 培训班课程带出材料
     * @param programsId
     * @param loginUserId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean courseBroughtOut(Long programsId,String loginUserId){
        //获取培训班课表课程,然后取课程对应已发布材料进行录入
        Map<String, Object> map = new HashMap<>();
        map.put("programs_id",programsId);
        List<TrainProgramsCourseDetailVO> courseDetailList = trainProgramsCourseMapper.getDetailList(map);
        List<TrainMaterials> insertList = new ArrayList<>();
        //获取目前所有培训班材料名称，处理去重
        List<String> existFileList = trainMaterialsMapper.getMaterialsNamesByQuery(map);
        for (TrainProgramsCourseDetailVO courseDetail : courseDetailList){
            List<TrainBaseCourseMaterialsDTO> materialsDTOS = trainBaseCourseMaterialsMapper.getSelectByCourseId(courseDetail.getCourseId());
            for (TrainBaseCourseMaterialsDTO materials : materialsDTOS){
                if(!existFileList.contains(materials.getFilename())){
                    existFileList.add(materials.getFilename());
                    TrainMaterials entity = new TrainMaterials();
                    BeanUtils.copyProperties(materials,entity);
                    entity.setId(null);
                    entity.setProgramsId(programsId);
                    entity.setStatus(0);
                    entity.setCreateUser(loginUserId);
                    entity.setCreateTime(System.currentTimeMillis()/1000);
                    insertList.add(entity);
                }
            }
        }
        if(!insertList.isEmpty()){
            return this.insertBatchSelective(insertList);
        }
        return false;
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
        List<TrainMaterialsLearningRecord> learnRecordList = new ArrayList<>();
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

                //获取培训班所有已发布材料
                List<TrainMaterialsVO> materialsVOList = trainMaterialsMapper.getMaterialsByProgramsId(programsId);
                for (TrainMaterialsVO materialsVO : materialsVOList){
                    //往材料学习记录表初始化待学习数据
                    TrainMaterialsLearningRecord learningRecord = new TrainMaterialsLearningRecord();
                    learningRecord.setMaterialsId(materialsVO.getId());
                    learningRecord.setType(2);
                    learningRecord.setUserId(userId);
                    learningRecord.setStatus(0);
                    learningRecord.setMaterialsType(materialsVO.getType());
                    learningRecord.setLastDuration(null);
                    learningRecord.setCreateUser(loginUserId);
                    learningRecord.setCreateTime(System.currentTimeMillis()/1000);
                    learnRecordList.add(learningRecord);
                }

                //自助平台插入代办
                dataParams.put("typeId",11);
                dataParams.put("type",11);
                dataParams.put("emplId",userId);
                dataParams.put("url","http://218.13.91.107:38000/kn-front/emp/center");
                trainNoticeMapper.createEmployeeSelfNotice(dataParams);
            }
        }
        if(!learnRecordList.isEmpty()){
            trainMaterialsLearningRecordService.insertBatchSelective(learnRecordList);
        }
        return trainNoticeService.insertBatchSelective(noticeList);
    }

    /**
     * 材料学习页数据
     * @param params
     * @return
     */
    @Override
    public TrainMaterialsLearnVO materialsLearningPageData(Map<String, Object> params){
        //获取培训班数据
        Long programsId = Long.valueOf(params.get("programsId").toString());
        String userId = null;
        if (params.containsKey("userId") && !StringUtils.isEmpty(params.get("userId").toString())) {
            userId = params.get("userId").toString();
        }
        TrainPrograms programsEntity = trainProgramsService.selectById(programsId);
        TrainMaterialsLearnVO vo = new TrainMaterialsLearnVO();
        if(entity != null){
            vo.setProgramsId(programsId);
            vo.setTitle(programsEntity.getTrainName());
            //查询共学人数
            vo.setLearnedNum(trainMaterialsMapper.totalLearningNumByProgramsId(programsId));
            List<TrainMaterialsLearnCategoryVO> categoryVOList = new ArrayList<>();
            //获取当前培训班材料的分类group分组
            List<String> categories = trainMaterialsMapper.getGroupMaterialsCategory(programsId);
            Map<String, Object> query = new HashMap<>();
            query.put("programsId",programsId);
            for (String category : categories){
                query.put("category",category);
                List<TrainMaterialsVO> materialsVOList = trainMaterialsMapper.getMaterialsByQuery(query);
                for (TrainMaterialsVO  materialsVO : materialsVOList){
                    //获取当前用户该资料的学习完成状态
                    materialsVO.setLearnedStatus(0);
                    if(userId != null){
                        TrainMaterialsLearningRecord queryEntity = new TrainMaterialsLearningRecord();
                        queryEntity.setMaterialsId(materialsVO.getId());
                        queryEntity.setType(2);
                        queryEntity.setUserId(userId);
                        TrainMaterialsLearningRecord selectOne = trainMaterialsLearningRecordService.selectOne(queryEntity);
                        if(selectOne != null){
                            materialsVO.setLearnedStatus(selectOne.getStatus());
                        }
                    }
                    //处理材料标题
                    materialsVO.setFileTitle(materialsVO.getFilename().split("\\.")[0]);
                }
                TrainMaterialsLearnCategoryVO categoryVO = new TrainMaterialsLearnCategoryVO();
                categoryVO.setCategory(category);
                categoryVO.setMaterialsList(materialsVOList);
                categoryVOList.add(categoryVO);
            }
            vo.setCategoryList(categoryVOList);
        }
        return vo;
    }
}