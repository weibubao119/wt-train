package com.dyys.hr.service.train.impl;

import cn.hutool.core.convert.Convert;
import com.dagongma.kernel.core.entity.PageView;
import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.train.QuestionnaireUserMapper;
import com.dyys.hr.dao.train.TrainInstitutionAssessmentMapper;
import com.dyys.hr.dto.train.TrainInstitutionAssessmentDTO;
import com.dyys.hr.entity.train.QuestionnaireUser;
import com.dyys.hr.entity.train.TrainInstitutionAssessment;
import com.dyys.hr.entity.train.TrainInstitutionAssessmentStaff;
import com.dyys.hr.entity.train.TrainNotice;
import com.dyys.hr.service.train.QuestionnaireUserService;
import com.dyys.hr.service.train.TrainInstitutionAssessmentService;
import com.dyys.hr.service.train.TrainInstitutionAssessmentStaffService;
import com.dyys.hr.service.train.TrainNoticeService;
import com.dyys.hr.vo.train.TrainInstitutionAssessmentStaffVO;
import com.dyys.hr.vo.train.TrainInstitutionAssessmentVO;
import com.github.pagehelper.page.PageMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 培训机构评估记录 服务实现类
 * </p>
 *
 * @author yangye
 * @since 2022-05-11
 */
@Service
@Slf4j
public class TrainInstitutionAssessmentServiceImpl extends AbstractCrudService<TrainInstitutionAssessment, Long> implements TrainInstitutionAssessmentService {
    @Autowired
    private TrainInstitutionAssessmentMapper trainInstitutionAssessmentMapper;

    @Autowired
    private TrainInstitutionAssessmentStaffService trainInstitutionAssessmentStaffService;

    @Autowired
    private QuestionnaireUserService questionnaireUserService;

    @Autowired
    private TrainNoticeService trainNoticeService;

    /**
     * 机构评估记录分页列表
     * @param params
     * @return
     */
    @Override
    public PageView<TrainInstitutionAssessmentVO> assessmentPageList(Map<String, Object> params) {
        PageMethod.startPage(Convert.toInt(params.get("page")), Convert.toInt(params.get("limit")));
        List<TrainInstitutionAssessmentVO> list = trainInstitutionAssessmentMapper.assessmentListByInstitutionId(Convert.toLong(params.get("institutionId")));
        return PageView.build(list);
    }

    /**
     * 机构评估创建保存
     * @param assessmentDTO
     * @param loginEmplId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long assessmentAdd(TrainInstitutionAssessmentDTO assessmentDTO, String loginEmplId) {
        Long stamp = System.currentTimeMillis()/1000; // 系统当前时间戳
        // 保存评估记录
        Date date = new Date();
        Long currentStamp = date.getTime(); // 当前日期时间戳
        Long startTimeStamp = assessmentDTO.getStartTime().getTime(); // 评估开始时间时间戳
        int status = startTimeStamp <= currentStamp ? 2 : 1;
        TrainInstitutionAssessment assessmentEntity = new TrainInstitutionAssessment();
        BeanUtils.copyProperties(assessmentDTO, assessmentEntity);
        assessmentEntity.setScore(new BigDecimal(0));
        assessmentEntity.setStatus(status);
        assessmentEntity.setCreateUser(loginEmplId);
        assessmentEntity.setCreateTime(stamp);
        assessmentEntity.setUpdateUser(loginEmplId);
        assessmentEntity.setUpdateTime(stamp);
        Long assessmentId = this.insertSelective(assessmentEntity);

        // 保存参评人员数据和通知消息
        List<TrainInstitutionAssessmentStaffVO> staffList = assessmentDTO.getStaffList();
        if (!staffList.isEmpty()) {
            // 处理参评人员相关数据
            this.handleStaffData(assessmentId, assessmentDTO.getQuestionnaireId(), staffList, loginEmplId);
        }
        return assessmentId;
    }

    /**
     * 评估更新保存
     * @param assessmentDTO
     * @param loginEmplId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer assessmentUpdate(TrainInstitutionAssessmentDTO assessmentDTO, String loginEmplId) {
        Long stamp = System.currentTimeMillis()/1000; // 系统当前时间戳
        Date date = new Date();
        Long currentStamp = date.getTime(); // 当前日期时间戳
        Long startTimeStamp = assessmentDTO.getStartTime().getTime(); // 评估开始时间时间戳
        int status = startTimeStamp <= currentStamp ? 2 : 1;
        TrainInstitutionAssessment assessmentEntity = new TrainInstitutionAssessment();
        BeanUtils.copyProperties(assessmentDTO, assessmentEntity);
        assessmentEntity.setStatus(status);
        assessmentEntity.setUpdateUser(loginEmplId);
        assessmentEntity.setUpdateTime(stamp);
        int updateRes = this.updateSelective(assessmentEntity);
        if (updateRes > 0) {
            trainInstitutionAssessmentStaffService.delByCondition(assessmentDTO.getId()); // 删除原有的参评人员
            questionnaireUserService.delByCondition(assessmentDTO.getId(), 2); // 删除原有问卷评估用户表数据
            trainNoticeService.delByCondition(assessmentDTO.getId(), 4); // 删除原有参评用户消息记录
            // 保存参评人员数据和通知消息
            List<TrainInstitutionAssessmentStaffVO> staffList = assessmentDTO.getStaffList();
            if (!staffList.isEmpty()) {
                // 处理参评人员相关数据
                this.handleStaffData(assessmentDTO.getId(), assessmentDTO.getQuestionnaireId(), staffList, loginEmplId);
            }
        }
        return updateRes;
    }

    /**
     * 处理参评人员相关数据
     * @param assessmentId
     * @param questionnaireId
     * @param staffList
     * @param loginEmplId
     */
    private void handleStaffData(Long assessmentId, Long questionnaireId, List<TrainInstitutionAssessmentStaffVO> staffList, String loginEmplId) {
        Long stamp = System.currentTimeMillis()/1000; // 系统当前时间戳
        List<TrainInstitutionAssessmentStaff> staffEntityList = new ArrayList<>();
        List<QuestionnaireUser> questionnaireUsers = new ArrayList<>();
        List<TrainNotice> noticeEntityList = new ArrayList<>();
        for (TrainInstitutionAssessmentStaffVO staffVO : staffList) {
            // 参评人员数据
            TrainInstitutionAssessmentStaff staffEntity = new TrainInstitutionAssessmentStaff();
            BeanUtils.copyProperties(staffVO, staffEntity);
            staffEntity.setAssessmentId(assessmentId);
            staffEntity.setQuestionnaireId(questionnaireId);
            staffEntity.setIsFinished(0);
            staffEntity.setIsDelete(0);
            staffEntity.setCreateUser(loginEmplId);
            staffEntity.setUpdateUser(loginEmplId);
            staffEntity.setCreateTime(stamp);
            staffEntity.setUpdateTime(stamp);
            staffEntityList.add(staffEntity);

            // 为了和问卷提交关联，所以需要向培训项目评估的用户表中插入数据
            QuestionnaireUser questionnaireUser = new QuestionnaireUser();
            questionnaireUser.setType(2); // 机构评估
            questionnaireUser.setUserId(staffVO.getEvaluatorEmplId()); // 参评人员工号
            questionnaireUser.setQuestionnaireId(questionnaireId);
            questionnaireUser.setTrainAppraiseId(assessmentId); // 机构评估记录ID
            questionnaireUser.setStatus(0);
            questionnaireUser.setCreateUser(loginEmplId);
            questionnaireUser.setUpdateUser(loginEmplId);
            questionnaireUser.setCreateTime(stamp);
            questionnaireUser.setUpdateTime(stamp);
            questionnaireUsers.add(questionnaireUser);


            // 通知消息数据
            TrainNotice notice = new TrainNotice();
            notice.setTypeId(assessmentId);
            notice.setUserId(staffVO.getEvaluatorEmplId());
            notice.setType(4);
            notice.setStatus(0);
            notice.setCreateUser(loginEmplId);
            notice.setUpdateUser(loginEmplId);
            notice.setCreateTime(stamp);
            notice.setUpdateTime(stamp);
            noticeEntityList.add(notice);
        }
        trainInstitutionAssessmentStaffService.insertBatchSelective(staffEntityList);
        questionnaireUserService.insertBatchSelective(questionnaireUsers);
        trainNoticeService.insertBatchSelective(noticeEntityList);
    }

    /**
     * 机构评估记录详情
     * @param id
     * @return
     */
    @Override
    public TrainInstitutionAssessmentVO assessmentInfo(Long id) {
        return trainInstitutionAssessmentMapper.queryInfoById(id);
    }

    /**
     * 机构评估取消
     * @param id
     * @param loginEmplId
     * @return
     */
    @Override
    public Integer assessmentCancel(Long id, String loginEmplId) {
        TrainInstitutionAssessment assessment = new TrainInstitutionAssessment();
        assessment.setId(id);
        assessment.setStatus(4);
        assessment.setUpdateUser(loginEmplId);
        assessment.setUpdateTime(System.currentTimeMillis()/1000);
        return updateSelective(assessment);
    }

    /**
     * 获取即将更新结束的评估数据列表
     * @return
     */
    @Override
    public List<TrainInstitutionAssessment> finishedList(){
        return trainInstitutionAssessmentMapper.finishedList();
    }

    /**
     * 校验评估数据唯一
     * @param assessmentDTO
     * @return
     */
    @Override
    public Boolean checkUniqueData(TrainInstitutionAssessmentDTO assessmentDTO) {
        Condition condition = new Condition(TrainInstitutionAssessment.class);
        if (assessmentDTO.getId() == null) {
            condition.createCriteria().andEqualTo("institutionId", assessmentDTO.getInstitutionId())
                    .andEqualTo("title", assessmentDTO.getTitle())
                    .andEqualTo("questionnaireId", assessmentDTO.getQuestionnaireId())
                    .andEqualTo("yearly", assessmentDTO.getYearly());
        } else {
            condition.createCriteria().andNotEqualTo("id", assessmentDTO.getId())
                    .andEqualTo("institutionId", assessmentDTO.getInstitutionId())
                    .andEqualTo("title", assessmentDTO.getTitle())
                    .andEqualTo("questionnaireId", assessmentDTO.getQuestionnaireId())
                    .andEqualTo("yearly", assessmentDTO.getYearly());
        }
        int res = trainInstitutionAssessmentMapper.selectCountByCondition(condition);
        return res > 0;
    }
}
