package com.dyys.hr.service.exam;

import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.entity.exam.ExamPaperQuestion;

/**
 * <p>
 * 试卷试题 服务类
 * </p>
 *
 * @author lidaan
 * @since 2022-04-21
 */
public interface IExamPaperQuestionService extends ICrudService<ExamPaperQuestion, Long> {
    /**
     * 根据试卷id删除试卷试题
     *
     * @param paperId
     */
    void deleteByPaper(Long paperId);

    /**
     * 删除试试卷试题
     *
     * @param quId    试题id
     */
    void deleteExam(String quId);
}
