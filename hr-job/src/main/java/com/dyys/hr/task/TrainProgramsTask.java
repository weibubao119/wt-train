package com.dyys.hr.task;

import com.dagongma.kernel.annotation.ResponseResult;
import com.dyys.hr.entity.train.QuestionnaireUser;
import com.dyys.hr.entity.train.TrainAppraise;
import com.dyys.hr.entity.train.TrainExam;
import com.dyys.hr.entity.train.TrainInstitutionAssessment;
import com.dyys.hr.service.train.QuestionnaireUserService;
import com.dyys.hr.service.train.TrainAppraiseService;
import com.dyys.hr.service.train.TrainExamService;
import com.dyys.hr.service.train.TrainInstitutionAssessmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Condition;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目培训数据处理脚本
 *
 * @author wsj
 * @date 2022/05/26
 */
@ResponseResult
@Slf4j
@RestController
@Component
public class TrainProgramsTask {
    @Autowired
    private TrainExamService trainExamService;
    @Autowired
    private TrainAppraiseService trainAppraiseService;
    @Autowired
    private TrainInstitutionAssessmentService trainInstitutionAssessmentService;
    @Autowired
    private QuestionnaireUserService questionnaireUserService;

    @Transactional(rollbackFor = Exception.class)
    @Scheduled(cron = "0 0/1 * * * ?")
    public void trainTask() {
        Date now =new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("program start------" + df.format(now));
        //处理培训项目考试
        TrainExam updateExam = new TrainExam();
        updateExam.setStatus(1);
        Condition eCondition = new Condition(TrainExam.class);
        eCondition.createCriteria().andCondition("status = 0 and start_time <= '" + df.format(now) + "' and end_time > '" + df.format(now) + "'");
        trainExamService.updateByConditionSelective(updateExam,eCondition);

        updateExam.setStatus(2);
        Condition ecCondition = new Condition(TrainExam.class);
        ecCondition.createCriteria().andCondition("status = 1 and end_time <= '" + df.format(now) + "'");
        trainExamService.updateByConditionSelective(updateExam,ecCondition);

        //处理培训项目评估
        TrainAppraise updateAppraise = new TrainAppraise();
        updateAppraise.setStatus(1);
        Condition aCondition = new Condition(TrainAppraise.class);
        aCondition.createCriteria().andCondition("status = 0 and start_time <= '" + df.format(now) + "' and end_time > '" + df.format(now) + "'");
        trainAppraiseService.updateByConditionSelective(updateAppraise,aCondition);

        //处理培训评估各项均分计算
        List<TrainAppraise> appraiseList = trainAppraiseService.finishedList();
        for (TrainAppraise appraise : appraiseList){
            Map<String, Object> query = new HashMap<>();
            query.put("trainAppraiseId",appraise.getId());
            query.put("type",1);
            //获取评估用户列表
            List<QuestionnaireUser> userList = questionnaireUserService.getList(query);
            if(!userList.isEmpty()){
                BigDecimal userNum = new BigDecimal(userList.size());
                String userIds = "(";
                for (QuestionnaireUser user :userList){
                    userIds = userIds.concat(user.getId() + ",");
                }
                userIds = userIds.substring(0, userIds.length() - 1). concat(")");
                //分别查询1.课程2.讲师3.组织方
                TrainAppraise entity = new TrainAppraise();
                entity.setId(appraise.getId());
                Map<String, Object> map = new HashMap<>();
                map.put("questionnaireUserIds",userIds);
                map.put("scoreObject",1);
                BigDecimal totalCourseScore = questionnaireUserService.getObjectTotalScore(map);
                if(totalCourseScore != null){
                    entity.setCourseScore(totalCourseScore.divide(userNum,2, RoundingMode.HALF_UP));
                }
                map.put("scoreObject",2);
                BigDecimal totalTeacherScore = questionnaireUserService.getObjectTotalScore(map);
                if(totalTeacherScore != null){
                    entity.setTeacherScore(totalTeacherScore.divide(userNum,2, RoundingMode.HALF_UP));
                }
                map.put("scoreObject",3);
                BigDecimal totalCompanyScore = questionnaireUserService.getObjectTotalScore(map);
                if(totalCompanyScore != null){
                    entity.setCompanyScore(totalCompanyScore.divide(userNum,2, RoundingMode.HALF_UP));
                }
                trainAppraiseService.updateSelective(entity);
            }
        }

        updateAppraise.setStatus(2);
        Condition acCondition = new Condition(TrainAppraise.class);
        acCondition.createCriteria().andCondition("status = 1 and end_time <= '" + df.format(now) + "'");
        trainAppraiseService.updateByConditionSelective(updateAppraise,acCondition);


        //处理机构评估
        TrainInstitutionAssessment updateInstitution = new TrainInstitutionAssessment();
        updateInstitution.setStatus(2);
        Condition iCondition = new Condition(TrainInstitutionAssessment.class);
        iCondition.createCriteria().andCondition("status = 1 and start_time <= '" + df.format(now) + "' and end_time > '" + df.format(now) + "'");
        trainInstitutionAssessmentService.updateByConditionSelective(updateInstitution,iCondition);

        //处理机构评估各项均分计算
        List<TrainInstitutionAssessment> assessmentList = trainInstitutionAssessmentService.finishedList();
        for (TrainInstitutionAssessment assessment : assessmentList){
            Map<String, Object> query = new HashMap<>();
            query.put("trainAppraiseId",assessment.getId());
            query.put("type",2);
            //获取评估用户列表
            List<QuestionnaireUser> userList = questionnaireUserService.getList(query);
            if(!userList.isEmpty()){
                BigDecimal userNum = new BigDecimal(userList.size());
                String userIds = "(";
                for (QuestionnaireUser user :userList){
                    userIds = userIds.concat(user.getId() + ",");
                }
                userIds = userIds.substring(0, userIds.length() - 1). concat(")");
                Map<String, Object> map = new HashMap<>();
                map.put("questionnaireUserIds",userIds);
                map.put("scoreObject",4);
                BigDecimal totalScore = questionnaireUserService.getObjectTotalScore(map);
                if(totalScore != null){
                    TrainInstitutionAssessment entity = new TrainInstitutionAssessment();
                    entity.setId(assessment.getId());
                    entity.setScore(totalScore.divide(userNum,2, RoundingMode.HALF_UP));
                    trainInstitutionAssessmentService.updateSelective(entity);
                }
            }
        }

        updateInstitution.setStatus(3);
        Condition icCondition = new Condition(TrainInstitutionAssessment.class);
        icCondition.createCriteria().andCondition("status = 2 and end_time <= '" + df.format(now) + "'");
        trainInstitutionAssessmentService.updateByConditionSelective(updateInstitution,icCondition);

        Date end =new Date();
        SimpleDateFormat de = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("program end------" + de.format(end));
    }
}
