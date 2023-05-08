package com.dyys.hr.dao.exam;

import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.exam.ExamPaperQuestion;
import com.dyys.hr.vo.exam.ExamPaperQuestionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 试卷试题 Mapper 接口
 * </p>
 *
 * @author lidaan
 * @since 2022-04-21
 */
@Mapper
public interface ExamPaperQuestionMapper extends ICrudMapper<ExamPaperQuestion> {
    /**
     * 根据试卷id删除试卷试题
     * @param paperId
     */
    void deleteByPaper(@Param("paperId") Long paperId);

    /**
     * 删除试卷试题
     * @param quesId 试题ID
     */
    void deletePaperQuestion(@Param("quesId") String quesId);

    /**
     * 根据试卷得到试题列表
     * @param paperId 试卷id
     * @return 试题列表
     */
    List<ExamPaperQuestionVO> listPaperQuestion(@Param("paperId") Long paperId);

    /**
     * 根据试卷得到试题列表
     * @param paperId 试卷id
     * @return 试题列表
     */
    List<ExamPaperQuestionVO> listQuestionDetails(@Param("paperId") Long paperId);
}
