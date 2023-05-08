package com.dyys.hr.service.exam.impl;

import cn.hutool.core.lang.Assert;
import com.dagongma.kernel.commons.enums.ResultCode;
import com.dagongma.kernel.commons.exceptions.BusinessException;
import com.dagongma.kernel.core.entity.PageQuery;
import com.dagongma.kernel.core.entity.PageView;
import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.dao.exam.ExamExaminationMapper;
import com.dyys.hr.entity.exam.ExamExamination;
import com.dyys.hr.service.exam.IExamExaminationService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 考试信息 服务实现类
 * </p>
 *
 * @author lidaan
 * @since 2022-04-21
 */
@Service
@Slf4j
public class ExamExaminationServiceImpl extends AbstractCrudService<ExamExamination, Long> implements IExamExaminationService {
    @Autowired
    private ExamExaminationMapper examExaminationMapper;

    /**
     * 分页获取考试信息
     *
     * @param pageQuery 查询条件
     * @return 分页结果
     */
    @Override
    public PageView<ExamExamination> pageExamination(PageQuery<Map<String, Object>> pageQuery) {
        Assert.notNull(pageQuery, "pageQuery is not null");
        Page page = PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize());
        try {
            examExaminationMapper.listExamination(pageQuery.getCondition());
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new BusinessException(ResultCode.INTERFACE_INNER_INVOKE_ERROR);
        } finally {
            page.close();
        }

        return PageView.build(page);
    }
}
