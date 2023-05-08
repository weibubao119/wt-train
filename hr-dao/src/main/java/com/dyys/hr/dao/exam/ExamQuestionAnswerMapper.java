package com.dyys.hr.dao.exam;

import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.exam.ExamQuestionAnswer;
import com.dyys.hr.vo.exam.ExamQuestionAnswerVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 问题答案选项 Mapper 接口
 * </p>
 *
 * @author lidaan
 * @since 2022-04-20
 */
@Mapper
public interface ExamQuestionAnswerMapper extends ICrudMapper<ExamQuestionAnswer> {
    /**
     * 根据问题id删除答案
     * @param quId
     */
    void deleteByQuestion(@Param("quId") Long quId);

    /**
     * 根据问题id得到答案列表
     * @param quId 问题id
     * @return 答案列表
     */
    List<ExamQuestionAnswerVO> listExamQuestionAnswer(@Param("quId") Long quId);
}
