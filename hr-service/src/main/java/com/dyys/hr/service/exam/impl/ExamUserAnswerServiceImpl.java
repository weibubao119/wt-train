package com.dyys.hr.service.exam.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.constant.exam.ExamContant;
import com.dyys.hr.dao.exam.ExamUserAnswerMapper;
import com.dyys.hr.entity.exam.ExamQuestion;
import com.dyys.hr.entity.exam.ExamQuestionAnswer;
import com.dyys.hr.entity.exam.ExamUserAnswer;
import com.dyys.hr.entity.train.TrainExam;
import com.dyys.hr.entity.train.TrainExaminer;
import com.dyys.hr.entity.train.TrainExaminerDetail;
import com.dyys.hr.entity.train.TrainNotice;
import com.dyys.hr.entity.train.excel.UserExamDetailExcel;
import com.dyys.hr.exception.RenException;
import com.dyys.hr.service.exam.IExamQuestionAnswerService;
import com.dyys.hr.service.exam.IExamQuestionService;
import com.dyys.hr.service.exam.IExamUserAnswerService;
import com.dyys.hr.service.train.TrainExamService;
import com.dyys.hr.service.train.TrainExaminerDetailService;
import com.dyys.hr.service.train.TrainExaminerService;
import com.dyys.hr.service.train.TrainNoticeService;
import com.dyys.hr.vo.exam.ExamQuestionResultVO;
import com.dyys.hr.vo.exam.ExaminationResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 员工答卷备选答案 服务实现类
 * </p>
 *
 * @author lidaan
 * @since 2022-04-20
 */
@Service
public class ExamUserAnswerServiceImpl extends AbstractCrudService<ExamUserAnswer, Long> implements IExamUserAnswerService {
    @Autowired
    private TrainExaminerDetailService trainExaminerDetailService;  //考试结果
    @Autowired
    private IExamQuestionService examQuestionService; //试题
    @Autowired
    private TrainExaminerService trainExaminerService;  //考试人员
    @Autowired
    private IExamQuestionAnswerService examQuestionAnswerService;
    @Autowired
    private TrainExamService trainExamService;
    @Autowired
    private TrainNoticeService trainNoticeService;
    @Autowired
    private ExamUserAnswerMapper examUserAnswerMapper;

    /**
     * 检测考试答案正确性
     *
     * @param examQuestion 试题
     * @param answers      答案(判断题(正确:1, 错误:0,选择题多个答案id以","隔开)
     * @return true:正确,false:不正确
     */
    private Boolean checkExamAnswer(ExamQuestion examQuestion, String answers) {
        List<ExamQuestionAnswer> examQuestionAnswers = examQuestionAnswerService.listRightAnswers(examQuestion.getId().toString());//根据问题id得到答案列表

        String[] answerIds = answers.split(",");
        if ((answerIds == null) || (answerIds.length == 0)) {
            return false;
        }

        boolean rignt = true;

        //判断题 answer传的是1正确,0错误
        if (examQuestion.getQuType() == ExamContant.QUEST_TYPE_JUDGE) {
            //答案如果提交的是正确
            if (answers.equals("1")) {
                if (examQuestionAnswers.isEmpty()) {  //试题答案是错误
                    rignt = false;
                }
            } else { //答案如果提交的是错误
                if (!examQuestionAnswers.isEmpty()) {  //试题答案是正确
                    rignt = false;
                }
            }
        } else {
            if (answerIds.length != examQuestionAnswers.size()) {
                return false;
            }

            //判断答案是否正确
            for (String aid : answerIds) {
                boolean findFlag = false;
                for (ExamQuestionAnswer answer : examQuestionAnswers) {
                    if (aid.equals(answer.getId().toString())) {
                        findFlag = true;
                    }
                }

                //未在正确答案中找到
                if (!findFlag) {
                    rignt = false;
                    break;
                }
            }
        }

        return rignt;
    }

    /**
     * 提交试题答案
     *
     * @param examResult 试题答案VO
     */
    @Override
    public TrainExaminerDetail addExamResult(ExaminationResultVO examResult, TrainExaminer examiner) {
        //用户提交的答卷保存
        TrainExaminerDetail examUserPaper = new TrainExaminerDetail();
        examUserPaper.setExaminerId(examiner.getId());
        examUserPaper.setExamTime(examResult.getExamTime());
        examUserPaper.setStatus(1);
        examUserPaper.setCreateUser(examiner.getUserId());
        examUserPaper.setCreateTime(DateUtil.current());
        examUserPaper.setUseTime(examResult.getUseTime());

        Long detailId = trainExaminerDetailService.insertSelective(examUserPaper);

        if (detailId == null) {
            throw new RenException("提交考试失败");
        }

        float totalScore = 0f;
        List<ExamUserAnswer> userAnswers = new ArrayList<>();

        //保存用户提交的答案
        for (ExamQuestionResultVO question : examResult.getQuestions()) {
            ExamQuestion examQuestion = examQuestionService.selectById(question.getQuId());
            ExamUserAnswer examUserAnswer = new ExamUserAnswer();
            examUserAnswer.setDetailsId(detailId.toString());
            examUserAnswer.setQuId(question.getQuId().toString());
            examUserAnswer.setUserId(examiner.getUserId());
            examUserAnswer.setPaperId(examResult.getPaperId());
            examUserAnswer.setContent(question.getContent());
            examUserAnswer.setAnswer(question.getAnswers());
            examUserAnswer.setExamId(examResult.getExamId());

            //主观题不需要检查正确答案
            if (examQuestion.getQuType() != ExamContant.QUEST_TYPE_SUBJ) {
                boolean isRight = checkExamAnswer(examQuestion, question.getAnswers());
                //如果答案正确，则记得分
                if (isRight) {
                    totalScore += examQuestion.getScore();
                    examUserAnswer.setIsRight(1);
                } else {
                    examUserAnswer.setIsRight(0);
                }
            }else{
                examUserAnswer.setIsRight(1);
            }

            userAnswers.add(examUserAnswer);
        }

        //保存答卷结果
        this.insertBatch(userAnswers);

        //修改用户得分
        examUserPaper.setScore(String.valueOf(totalScore));
        trainExaminerDetailService.updateSelective(examUserPaper);

        //更新参考人员考试次数,最高分,考试状态
        examiner.setExamNum((examiner.getExamNum() == null) ? 1 : (examiner.getExamNum() + 1));
        examiner.setHighestScore(ObjectUtil.isEmpty(examiner.getHighestScore())?"0.0":examiner.getHighestScore());
        if(totalScore > Float.parseFloat(examiner.getHighestScore())){
            examiner.setHighestScore(String.valueOf(totalScore));
        }

        //获取培训考试及格分判断是否通过
        TrainExam trainExam = trainExamService.selectById(examiner.getExamId());
        if(totalScore >= Float.parseFloat(trainExam.getPassScore())){
            examiner.setStatus(1);
        }else{
            examiner.setStatus(0);
        }

        examiner.setFinalTime(new Date());
        trainExaminerService.updateSelective(examiner);

        //获取代办通知记录,更新状态为已处理
        Map<String, Object> params = new HashMap<>();
        params.put("typeId",examiner.getExamId());
        params.put("userId",examiner.getUserId());
        params.put("type",2);
        params.put("status",0);
        TrainNotice noticeRecord = trainNoticeService.getByQuery(params);
        if(noticeRecord != null){
            TrainNotice notice = new TrainNotice();
            notice.setId(noticeRecord.getId());
            notice.setStatus(1);
            notice.setUpdateUser(examiner.getUserId());
            notice.setUpdateTime(System.currentTimeMillis()/1000);
            trainNoticeService.updateSelective(notice);
        }
        return examUserPaper;
    }

    @Override
    public List<UserExamDetailExcel> listUserExamDetail(String detailsId){
        List<UserExamDetailExcel> detailExcelList = examUserAnswerMapper.listUserExamDetail(detailsId);
        //处理用户答案显示
        for (UserExamDetailExcel detail : detailExcelList){
            //根据题目类型判断处理
            if(detail.getQuType() != null){
                if(detail.getQuType() == 1){
                    ExamQuestionAnswer questionAnswer = examQuestionAnswerService.selectById(Long.valueOf(detail.getUserAnswer()));
                    detail.setUserAnswer(questionAnswer.getItemIndex());
                }
                if(detail.getQuType() == 2){
                    String answerString = "";
                    String[] answerIds = detail.getUserAnswer().split(",");
                    for (String answerId : answerIds){
                        ExamQuestionAnswer questionAnswer = examQuestionAnswerService.selectById(Long.valueOf(answerId));
                        answerString = answerString.concat(questionAnswer.getItemIndex() + ",");
                    }
                    detail.setUserAnswer(answerString);
                }
                if(detail.getQuType() == 3){
                    if(detail.getUserAnswer().equals("1")){
                        detail.setUserAnswer("正确");
                    }else{
                        detail.setUserAnswer("错误");
                    }
                }
            }

        }
        return detailExcelList;
    }
}
