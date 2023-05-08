package com.dyys.hr.service.exam;

import com.dagongma.kernel.core.entity.PageQuery;
import com.dagongma.kernel.core.entity.PageView;
import com.dagongma.mybatis.core.service.ICrudService;
import com.dyys.hr.entity.exam.ExamExamination;

import java.util.Map;

/**
 * <p>
 * 考试信息 服务类
 * </p>
 *
 * @author lidaan
 * @since 2022-04-21
 */
public interface IExamExaminationService extends ICrudService<ExamExamination, Long> {
    /**
     * 分页获取考试信息
     * @param pageQuery 查询条件
     * @return 分页结果
     */
    PageView<ExamExamination> pageExamination(PageQuery<Map<String, Object>> pageQuery);
}
