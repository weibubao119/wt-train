package com.dyys.hr.service.exam;


import com.dagongma.kernel.core.entity.PageQuery;
import com.dagongma.kernel.core.entity.PageView;
import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.entity.exam.ExamQuestion;
import com.dyys.hr.entity.exam.ExamQuestionExcel;
import com.dyys.hr.vo.exam.ExamQuDetialsVO;
import com.dyys.hr.vo.exam.ExamQuestionVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 试题信息 服务类
 * </p>
 *
 * @author lidaan
 * @since 2022-04-20
 */
public interface IExamQuestionService extends ICrudService<ExamQuestion, Long> {
    /**
     * 分页获取试题列表
     * @param pageQuery 查询条件
     * @return 分页结果
     */
    PageView<ExamQuestion> pageExamQuestion(PageQuery<Map<String, Object>> pageQuery);

    /**
     * 获取试题列表
     * @param params 查询条件
     * @return 试题列表
     */
    List<ExamQuestion> listExamQuestion(Map<String, Object> params);

    /**
     * 获取试题详情
     * @param id 主键id
     * @return  试题详情
     */
    ExamQuDetialsVO getExamQuestion(Long id);

    /**
     * Excel题目类型下拉项
     * @return
     */
    Map<Integer, List<String>> excelSelectMap();

    /**
     * excel批量导入考试题
     * @param excelList
     * @param paperId
     * @param loginEmplId
     * @return
     */
    List<ExamQuestionExcel> importExl(List<ExamQuestionExcel> excelList, Long paperId, String loginEmplId);

    /**
     * 校验试卷中的同题目类型的题目内容是否唯一
     * @param vo
     * @return
     */
    Boolean checkUniqueData(ExamQuestionVO vo);
}
