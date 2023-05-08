package com.dyys.hr.service.exam.impl;

import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.exam.ExamPaperQuestionMapper;
import com.dyys.hr.entity.exam.ExamPaperQuestion;
import com.dyys.hr.service.exam.IExamPaperQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 试卷试题 服务实现类
 * </p>
 *
 * @author lidaan
 * @since 2022-04-21
 */
@Service
public class ExamPaperQuestionServiceImpl extends AbstractCrudService<ExamPaperQuestion, Long> implements IExamPaperQuestionService {
    @Resource
    private ExamPaperQuestionMapper examPaperQuestionMapper;

    /**
     * 根据试卷id删除试卷试题
     *
     * @param paperId
     */
    @Override
    public void deleteByPaper(Long paperId) {

        examPaperQuestionMapper.deleteByPaper(paperId);
    }


    /**
     * 删除试试卷试题
     *
     * @param quId    试题id
     */
    @Override
    public void deleteExam(String quId) {
        examPaperQuestionMapper.deletePaperQuestion(quId);
    }
}
