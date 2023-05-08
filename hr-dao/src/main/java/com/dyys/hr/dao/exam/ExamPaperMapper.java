package com.dyys.hr.dao.exam;

import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.exam.ExamPaper;
import com.dyys.hr.vo.exam.ExamPaperDetailsVO;
import com.dyys.hr.vo.exam.ExamPaperPageVO;
import com.dyys.hr.vo.exam.ExamPaperResp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 试卷信息 Mapper 接口
 * </p>
 *
 * @author lidaan
 * @since 2022-04-20
 */
@Mapper
public interface ExamPaperMapper extends ICrudMapper<ExamPaper> {
    /**
     * 根据条件查询列表
     * @param condition
     * @return
     */
    List<ExamPaperPageVO> listExamPaper(Map<String, Object> condition);

    /**
     * 根据试卷id得到试卷详情
     * @param paperId  试卷id
     * @return 试卷详情
     */
    ExamPaperResp getExamPaper(@Param("paperId") Long paperId);

    List<Map<String,Object>> selectList(Map<String, Object> params);

    /**
     * 得到试卷详情
     * @param examId 考试id
     * @return 试卷详情
     */
    ExamPaperDetailsVO getPaperDetails(@Param("examId") String examId);

    /**
     * 获取试卷试题总分
     * @param paperId
     * @return
     */
    Integer paperQuestionTotalScore(Long paperId);
}
