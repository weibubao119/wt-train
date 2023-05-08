package com.dyys.hr.dao.exam;

import com.dagongma.kernel.core.entity.PageQuery;
import com.dagongma.kernel.core.entity.PageView;
import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.exam.ExamQuestion;
import com.dyys.hr.vo.exam.ExamQuDetialsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 试题信息 Mapper 接口
 * </p>
 *
 * @author lidaan
 * @since 2022-04-20
 */
@Mapper
public interface ExamQuestionMapper extends ICrudMapper<ExamQuestion> {
    /**
     * 分页获取
     *
     * @param condition 查询条件
     * @return 分页结果
     */
    List<ExamQuestion> listExamQuestion(Map<String, Object> condition);

    /**
     * 根据id获取试题详情
     * @param id 试题主键id
     * @return 试题详情
     */
    ExamQuDetialsVO getExamQuestion(@Param("id") Long id);
}
