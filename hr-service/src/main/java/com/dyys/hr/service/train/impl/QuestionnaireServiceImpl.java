package com.dyys.hr.service.train.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import com.dagongma.kernel.commons.enums.ResultCode;
import com.dagongma.kernel.commons.exceptions.BusinessException;
import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.train.*;
import com.dyys.hr.dto.train.*;
import com.dyys.hr.entity.train.*;
import com.dyys.hr.service.train.*;
import com.dyys.hr.vo.train.QuestionnaireTemplatePageVo;
import com.dyys.hr.vo.train.QuestionnaireUserPageVo;
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
public class QuestionnaireServiceImpl extends AbstractCrudService<Questionnaire, Long> implements QuestionnaireService {
    @Autowired
    private QuestionnaireMapper questionnaireMapper;
    @Autowired
    private QuestionnaireUserMapper questionnaireUserMapper;
    @Autowired
    private QuestionnaireTextService questionnaireTextService;
    @Autowired
    private QuestionnaireInstructionsService questionnaireInstructionsService;
    @Autowired
    private QuestionnaireScoreSheetsService questionnaireScoreSheetsService;
    @Autowired
    private QuestionnaireScoreSheetsDetailService questionnaireScoreSheetsDetailService;
    @Autowired
    private QuestionnaireUserService questionnaireUserService;
    @Autowired
    private TrainNoticeService trainNoticeService;
    @Autowired
    private TrainAppraisePersonService trainAppraisePersonService;
    @Autowired
    private TrainInstitutionAssessmentStaffService trainInstitutionAssessmentStaffService;
    @Autowired
    private QuestionnaireCheckBoxService questionnaireCheckBoxService;
    @Autowired
    private QuestionnaireCheckBoxDetailService questionnaireCheckBoxDetailService;

    @Override
    public PageInfo<QuestionnaireTemplatePageVo> pageList(Map<String, Object> params){
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        List<QuestionnaireTemplatePageVo> voList = questionnaireMapper.pageList(params);
        return new PageInfo<>(voList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(QuestionnaireDTO dto, String loginUserId){
        //问卷模板主表新增数据
        Questionnaire entity = new Questionnaire();
        BeanUtils.copyProperties(dto,entity);
        String code = DateUtil.format(new Date(), "yyyyMMddHHmmssSSS");
        entity.setCode(code);
        entity.setCreateUser(loginUserId);
        entity.setCreateTime(System.currentTimeMillis()/1000);
        return this.insertSelective(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer update(QuestionnaireDTO dto, String loginUserId){
        //问卷模板主表新增数据
        Questionnaire questionnaire = this.selectById(dto.getId());
        if(questionnaire == null){
            throw new BusinessException(ResultCode.EXCEPTION,"问卷不存在");
        }
        if(questionnaire.getStatus() == 1){
            throw new BusinessException(ResultCode.EXCEPTION,"问卷已发布,不能修改");
        }
        Questionnaire entity = new Questionnaire();
        BeanUtils.copyProperties(dto,entity);
        entity.setUpdateUser(loginUserId);
        entity.setUpdateTime(System.currentTimeMillis()/1000);
        return this.updateSelective(entity);
    }

    @Override
    public QuestionnaireDTO getDetail(Map<String, Object> params){
        QuestionnaireDTO questionnaire = questionnaireMapper.getDetailByQuery(params);
//        if(questionnaire != null){
//            //查询问卷文本列表
//            Map<String, Object> dQuery = new HashMap<>();
//            dQuery.put("questionnaire_id",questionnaireId);
//            questionnaire.setQuestionnaireTextList(questionnaireTextMapper.getListByQuery(dQuery));
//            questionnaire.setQuestionnaireInstructionsList(questionnaireInstructionsMapper.getListByQuery(dQuery));
//            List<QuestionnaireScoreSheetsDTO> sheetsDTOS = questionnaireScoreSheetsMapper.getListByQuery(dQuery);
//            for (QuestionnaireScoreSheetsDTO sheetsDTO : sheetsDTOS){
//                QuestionnaireScoreSheets entity = questionnaireScoreSheetsService.selectById(sheetsDTO.getId());
//                JSONArray scoreItems = JSONUtil.parseArray(entity.getScoreItems());
//                sheetsDTO.setScoreItemsList(JSONUtil.toList(scoreItems, ScoreItemsDTO.class));
//                JSONArray gradeLine = JSONUtil.parseArray(entity.getGradeLine());
//                sheetsDTO.setGradeLineList(JSONUtil.toList(gradeLine, GradeLineDTO.class));
//            }
//            questionnaire.setQuestionnaireScoreSheetsList(sheetsDTOS);
//        }
        return questionnaire;
    }

    @Override
    public PageInfo<QuestionnaireUserPageVo> userFillPageList(Map<String, Object> params){
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        List<QuestionnaireUserPageVo> voList = questionnaireUserMapper.userFillPageList(params);
        return new PageInfo<>(voList);
    }

    @Override
    public PageInfo<Map<String,Object>> selectList(Map<String, Object> params){
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        List<Map<String,Object>> voList = questionnaireMapper.selectList(params);
        return new PageInfo<>(voList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer userSubmit(QuestionnaireUserSubmitDTO dto, String loginUserId){
        long stamp = System.currentTimeMillis()/1000;
        //获取用户评估记录数据
        Map<String, Object> query = new HashMap<>();
        query.put("type", dto.getType()); // 评估类型：1培训评估，2机构评估
        query.put("trainAppraiseId",dto.getTrainAppraiseId());
        query.put("userId",loginUserId);
        QuestionnaireUser userEntity = questionnaireUserMapper.getByQuery(query);
        if(userEntity == null){
            throw new BusinessException(ResultCode.EXCEPTION,"用户评估问卷记录不存在!");
        }
        if(userEntity.getStatus() == 1){
            throw new BusinessException(ResultCode.EXCEPTION,"不能重复填写问卷!");
        }
        //问卷文本框模块数据批量存储
        List<QuestionnaireTextDTO> textDTOS = dto.getQuestionnaireTextList();
        if(textDTOS != null && !textDTOS.isEmpty()){
            List<QuestionnaireText> textEntityList = new ArrayList<>();
            for (QuestionnaireTextDTO textDTO : textDTOS){
                QuestionnaireText textEntity = new QuestionnaireText();
                BeanUtils.copyProperties(textDTO,textEntity);
                textEntity.setQuestionnaireUserId(userEntity.getId());
                textEntity.setCreateUser(loginUserId);
                textEntity.setCreateTime(stamp);
                textEntityList.add(textEntity);
            }
            questionnaireTextService.insertBatchSelective(textEntityList);
        }
        //问卷评分表模块数据批量存储
        List<QuestionnaireScoreSheetsDTO> sheetsDTOS = dto.getQuestionnaireScoreSheetsList();
        if(sheetsDTOS != null && !sheetsDTOS.isEmpty()){
            for (QuestionnaireScoreSheetsDTO sheetsDTO : sheetsDTOS){
                QuestionnaireScoreSheets sheetsEntity  = new QuestionnaireScoreSheets();
                BeanUtils.copyProperties(sheetsDTO,sheetsEntity);
                sheetsEntity.setQuestionnaireUserId(userEntity.getId());
                sheetsEntity.setCreateUser(loginUserId);
                sheetsEntity.setCreateTime(stamp);
                Long sheetsId = questionnaireScoreSheetsService.insertSelective(sheetsEntity);
                List<QuestionnaireScoreSheetsDetailDTO> detailDTOS = sheetsDTO.getSheetsDetailList();
                //评分表数据详情批量存储
                if(detailDTOS != null && !detailDTOS.isEmpty()){
                    List<QuestionnaireScoreSheetsDetail> detailEntityList = new ArrayList<>();
                    for (QuestionnaireScoreSheetsDetailDTO detailDTO : detailDTOS){
                        QuestionnaireScoreSheetsDetail detailEntity = new QuestionnaireScoreSheetsDetail();
                        BeanUtils.copyProperties(detailDTO,detailEntity);
                        detailEntity.setSheetsId(sheetsId);
                        detailEntity.setCreateUser(loginUserId);
                        detailEntity.setCreateTime(stamp);
                        detailEntityList.add(detailEntity);
                    }
                    questionnaireScoreSheetsDetailService.insertBatchSelective(detailEntityList);
                }
            }
        }
        //问卷说明模块数据批量存储
        List<QuestionnaireInstructionsDTO> instructionsDTOS = dto.getQuestionnaireInstructionsList();
        if(instructionsDTOS != null && !instructionsDTOS.isEmpty()){
            List<QuestionnaireInstructions> instructionsEntityList = new ArrayList<>();
            for (QuestionnaireInstructionsDTO instructionsDTO : instructionsDTOS){
                QuestionnaireInstructions instructionsEntity  = new QuestionnaireInstructions();
                BeanUtils.copyProperties(instructionsDTO,instructionsEntity);
                instructionsEntity.setQuestionnaireUserId(userEntity.getId());
                instructionsEntity.setCreateUser(loginUserId);
                instructionsEntity.setCreateTime(stamp);
                instructionsEntityList.add(instructionsEntity);
            }
            questionnaireInstructionsService.insertBatchSelective(instructionsEntityList);
        }

        //问卷选择框模块数据批量存储
        List<QuestionnaireCheckBoxDTO> checkBoxList = dto.getQuestionnaireCheckBoxList();
        if(checkBoxList != null && !checkBoxList.isEmpty()){
            for (QuestionnaireCheckBoxDTO checkBoxDTO : checkBoxList){
                QuestionnaireCheckBox checkBoxEntity = new QuestionnaireCheckBox();
                BeanUtils.copyProperties(checkBoxDTO,checkBoxEntity);
                checkBoxEntity.setQuestionnaireUserId(userEntity.getId());
                checkBoxEntity.setCreateUser(loginUserId);
                checkBoxEntity.setCreateTime(stamp);
                Long checkBoxId = questionnaireCheckBoxService.insertSelective(checkBoxEntity);
                List<QuestionnaireCheckBoxDetailDTO> detailDTOS = checkBoxDTO.getCheckBoxDetailList();
                //选择框数据详情批量存储
                if(detailDTOS != null && !detailDTOS.isEmpty()){
                    List<QuestionnaireCheckBoxDetail> detailEntityList = new ArrayList<>();
                    for (QuestionnaireCheckBoxDetailDTO detailDTO : detailDTOS){
                        QuestionnaireCheckBoxDetail detailEntity = new QuestionnaireCheckBoxDetail();
                        BeanUtils.copyProperties(detailDTO,detailEntity);
                        detailEntity.setCheckBoxId(checkBoxId);
                        detailEntity.setCreateUser(loginUserId);
                        detailEntity.setCreateTime(stamp);
                        detailEntityList.add(detailEntity);
                    }
                    questionnaireCheckBoxDetailService.insertBatchSelective(detailEntityList);
                }
            }
        }

        //更新用户评估记录状态为已填写
        QuestionnaireUser user = new QuestionnaireUser();
        user.setId(userEntity.getId());
        user.setStatus(1);
        user.setUpdateUser(loginUserId);
        user.setUpdateTime(stamp);
        questionnaireUserService.updateSelective(user);

        //获取代办通知记录,更新状态为已处理
        Map<String, Object> params = new HashMap<>();
        params.put("typeId",dto.getTrainAppraiseId());
        params.put("userId",loginUserId);
        params.put("type",dto.getType() == 1 ? 3 : 4);
        params.put("status",0);
        TrainNotice noticeRecord = trainNoticeService.getByQuery(params);
        if(noticeRecord != null){
            TrainNotice notice = new TrainNotice();
            notice.setId(noticeRecord.getId());
            notice.setStatus(1);
            notice.setUpdateUser(loginUserId);
            notice.setUpdateTime(stamp);
            trainNoticeService.updateSelective(notice);
        }

        if (dto.getType().equals(1)) {
            // 培训评估参评人员记录更新
            Map<String, Object> tQuery = new HashMap<>();
            tQuery.put("appraiseId",dto.getTrainAppraiseId());
            tQuery.put("userId",loginUserId);
            tQuery.put("status",0);
            TrainAppraisePerson personRecord = trainAppraisePersonService.getByQuery(tQuery);
            if(personRecord != null){
                TrainAppraisePerson person = new TrainAppraisePerson();
                person.setId(personRecord.getId());
                person.setStatus(1); // 更新状态为已提交
                person.setUpdateUser(loginUserId);
                person.setUpdateTime(stamp);
                trainAppraisePersonService.updateSelective(person);
            }
        } else {
            // 机构评估参评人员记录更新
            TrainInstitutionAssessmentStaff assessmentStaff = new TrainInstitutionAssessmentStaff();
            assessmentStaff.setAssessmentId(dto.getTrainAppraiseId());
            assessmentStaff.setIsFinished(0);
            assessmentStaff.setEvaluatorEmplId(loginUserId);
            TrainInstitutionAssessmentStaff staffRecord = trainInstitutionAssessmentStaffService.selectOne(assessmentStaff);
            if (staffRecord != null) {
                TrainInstitutionAssessmentStaff staff = new TrainInstitutionAssessmentStaff();
                staff.setId(staffRecord.getId());
                staff.setIsFinished(1); // 更新状态为已完成
                staff.setUpdateUser(loginUserId);
                staff.setUpdateTime(stamp);
                trainInstitutionAssessmentStaffService.updateSelective(staff);
            }
        }

        return 1;
    }

    @Override
    public QuestionnaireUserSubmitDTO getUserSubmitDetail(Map<String, Object> params){
        //查询用户问卷记录表
        QuestionnaireUser questionnaireUser = questionnaireUserService.selectById(Long.valueOf(params.get("id").toString()));
        if(questionnaireUser == null){
            throw new BusinessException(ResultCode.EXCEPTION,"用户评估问卷记录不存在!");
        }
        if(questionnaireUser.getStatus() == 0){
            throw new BusinessException(ResultCode.EXCEPTION,"用户未提交问卷!");
        }
        QuestionnaireUserSubmitDTO dto = new QuestionnaireUserSubmitDTO();

        Map<String, Object> query = new HashMap<>();
        query.put("questionnaireUserId",params.get("id"));
        //获取问卷文本模块数据
        dto.setQuestionnaireTextList(questionnaireTextService.getListByQuery(query));
        //获取问卷评分表模块数据
        dto.setQuestionnaireScoreSheetsList(questionnaireScoreSheetsService.getListByQuery(query));
        //获取问卷说明模块数据
        dto.setQuestionnaireInstructionsList(questionnaireInstructionsService.getListByQuery(query));
        //获取问卷选择框模块数据
        dto.setQuestionnaireCheckBoxList(questionnaireCheckBoxService.getListByQuery(query));
        return dto;
    }
}