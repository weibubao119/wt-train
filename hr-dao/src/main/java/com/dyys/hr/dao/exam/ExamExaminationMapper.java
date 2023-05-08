package com.dyys.hr.dao.exam;

import com.dagongma.kernel.core.entity.PageView;
import com.dagongma.mybatis.core.dao.ICrudMapper;
import com.dyys.hr.entity.exam.ExamExamination;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 考试信息 Mapper 接口
 * </p>
 *
 * @author lidaan
 * @since 2022-04-21
 */
@Mapper
public interface ExamExaminationMapper extends ICrudMapper<ExamExamination> {
    /**
     * 获取考试列表
     * @param condition 条件
     * @return 考试列表
     */
    List<ExamExamination> listExamination(Map<String, Object> condition);
}
