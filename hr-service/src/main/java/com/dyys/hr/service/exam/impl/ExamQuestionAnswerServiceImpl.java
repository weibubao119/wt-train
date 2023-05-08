package com.dyys.hr.service.exam.impl;

import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.exam.ExamQuestionAnswerMapper;
import com.dyys.hr.entity.exam.ExamQuestionAnswer;
import com.dyys.hr.service.exam.IExamQuestionAnswerService;
import com.dyys.hr.vo.exam.ExamQuestionAnswerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 问题答案选项 服务实现类
 * </p>
 *
 * @author lidaan
 * @since 2022-04-20
 */
@Service
public class ExamQuestionAnswerServiceImpl extends AbstractCrudService<ExamQuestionAnswer, Long> implements IExamQuestionAnswerService {
    @Autowired
    private ExamQuestionAnswerMapper examQuestionAnswerMapper;

    /**
     * 根据问题删除答案
     *
     * @param quId
     */
    @Override
    public void deleteByQuestion(Long quId) {
        examQuestionAnswerMapper.deleteByQuestion(quId);
    }

    /**
     * 根据试题ID得到所有正确答案
     * @param quId 试题id
     * @return 正确答案列表
     */
    @Override
    public List<ExamQuestionAnswer> listRightAnswers(String quId) {
        ExamQuestionAnswer examAnswer = new ExamQuestionAnswer();
        examAnswer.setQuId(quId);
        examAnswer.setIsRight(1);

        return examQuestionAnswerMapper.select(examAnswer);
    }

}
