package com.dyys.hr.service.exam;

import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.entity.exam.ExamQuestionAnswer;

import java.util.List;

/**
 * <p>
 * 问题答案选项 服务类
 * </p>
 *
 * @author lidaan
 * @since 2022-04-20
 */
public interface IExamQuestionAnswerService extends ICrudService<ExamQuestionAnswer, Long> {
    /**
     * 根据问题删除答案
     * @param quId
     */
    void deleteByQuestion(Long quId);

    /**
     * 根据试题ID得到所有正确答案
     * @param quId 试题id
     * @return 正确答案列表
     */
    List<ExamQuestionAnswer> listRightAnswers(String quId);
}
