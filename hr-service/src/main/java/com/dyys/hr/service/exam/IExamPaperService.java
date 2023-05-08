package com.dyys.hr.service.exam;


import com.dagongma.kernel.core.entity.PageQuery;
import com.dagongma.kernel.core.entity.PageView;
import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.entity.exam.ExamPaper;
import com.dyys.hr.vo.exam.ExamPaperDetailsVO;
import com.dyys.hr.vo.exam.ExamPaperPageVO;
import com.dyys.hr.vo.exam.ExamPaperResp;
import com.dyys.hr.vo.exam.ExamPaperVO;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * <p>
 * 试卷信息 服务类
 * </p>
 *
 * @author lidaan
 * @since 2022-04-20
 */
public interface IExamPaperService extends ICrudService<ExamPaper, Long> {
    PageView<ExamPaperPageVO> pageExamPaper(PageQuery<Map<String, Object>> pageQuery);

    /**
     * 根据试卷得到详情
     *
     * @param id 主键id
     * @return 试卷详情
     */
    ExamPaperResp getExamPaper(Long id);

    PageInfo<Map<String, Object>> selectList(Map<String, Object> params);

    /**
     * 复制试题
     *
     * @param paperVO 试题实体
     */
    void copyPaper(ExamPaperVO paperVO);

    /**
     * 获取试卷结果
     *
     * @param paperId 试卷ID
     * @return 试卷详情
     */
    ExamPaperDetailsVO getPaperDetails(String paperId);

    /**
     * 获取试卷试题总分
     * @param paperId
     * @return
     */
    Integer paperQuestionTotalScore(Long paperId);

    /**
     * 删除试卷
     * @param paperId
     */
    void deletePaper(Long paperId);
}
