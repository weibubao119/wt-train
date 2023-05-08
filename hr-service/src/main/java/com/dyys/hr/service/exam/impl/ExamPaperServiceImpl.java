package com.dyys.hr.service.exam.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Assert;
import com.dagongma.kernel.commons.enums.ResultCode;
import com.dagongma.kernel.commons.exceptions.BusinessException;
import com.dagongma.kernel.core.entity.PageQuery;
import com.dagongma.kernel.core.entity.PageView;
import com.dagongma.mybatis.core.service.impl.AbstractCrudService;
import com.dyys.hr.constants.Constant;
import com.dyys.hr.dao.exam.ExamPaperMapper;
import com.dyys.hr.dao.exam.ExamPaperQuestionMapper;
import com.dyys.hr.dao.exam.ExamQuestionAnswerMapper;
import com.dyys.hr.entity.exam.ExamPaper;
import com.dyys.hr.entity.exam.ExamPaperQuestion;
import com.dyys.hr.exception.RenException;
import com.dyys.hr.service.exam.IExamPaperService;
import com.dyys.hr.vo.exam.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 试卷信息 服务实现类
 * </p>
 *
 * @author lidaan
 * @since 2022-04-20
 */
@Service
@Slf4j
public class ExamPaperServiceImpl extends AbstractCrudService<ExamPaper, Long> implements IExamPaperService {
    //试卷mapper
    @Resource
    private ExamPaperMapper examPaperMapper;

    //试题mapper
    @Resource
    private ExamPaperQuestionMapper questionMapper;

    //试题选项mapper
    @Resource
    private ExamQuestionAnswerMapper questionAnswerMapper;

    @Override
    public PageView<ExamPaperPageVO> pageExamPaper(PageQuery<Map<String, Object>> pageQuery) {
        Assert.notNull(pageQuery, "params is not null");
        Page page = PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize());

        try {
            examPaperMapper.listExamPaper(pageQuery.getCondition());
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new BusinessException(ResultCode.INTERFACE_INNER_INVOKE_ERROR);
        } finally {
            page.close();
        }

        return PageView.build(page);
    }

    /**
     * 根据试卷得到详情
     *
     * @param id 主键id
     * @return 试卷详情
     */
    @Override
    public ExamPaperResp getExamPaper(Long id) {
        ExamPaperResp examPaperVO = examPaperMapper.getExamPaper(id);
        List<ExamPaperQuestionVO> examPaperQuestionVOS = questionMapper.listPaperQuestion(id);
        if (!CollUtil.isEmpty(examPaperQuestionVOS)) {
            for (ExamPaperQuestionVO examPaperQuestion : examPaperQuestionVOS){
                List<ExamQuestionAnswerVO> examQuestionAnswerVOS = questionAnswerMapper.listExamQuestionAnswer(examPaperQuestion.getId());
                if (!CollUtil.isEmpty(examQuestionAnswerVOS)) {
                    examPaperQuestion.setAnswerList(examQuestionAnswerVOS);
                }
            }
            examPaperVO.setQuestions(examPaperQuestionVOS);

        }
        return examPaperVO;
    }


    @Override
    public PageInfo<Map<String, Object>> selectList(Map<String, Object> params) {
        int page = Convert.toInt(params.get("page"));
        int limit = Convert.toInt(params.get("limit"));
        PageMethod.startPage(page, limit);
        List<Map<String, Object>> voList = examPaperMapper.selectList(params);
        return new PageInfo<>(voList);
    }

    /**
     * 复制试卷
     *
     * @param paperVO 试卷实体
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void copyPaper(ExamPaperVO paperVO) {
        ExamPaper examPaper = selectById(paperVO.getId());
        if (examPaper == null) {
            throw new BusinessException(ResultCode.EXCEPTION,"未找到该试卷!");
        }

        List<ExamPaperQuestionVO> examPaperQuestionList = questionMapper.listPaperQuestion(paperVO.getId());

        examPaper.setId(null);
        examPaper.setName(paperVO.getName());
        examPaper.setTotle(Float.valueOf(paperVO.getTotle()));
        examPaper.setContent(paperVO.getContent());
        examPaper.setCompanyCode(paperVO.getCompanyCode());
        examPaper.setDeptId(paperVO.getDeptId());
        examPaper.setStatus(0);

        Long pk = this.insert(examPaper);

        List<ExamPaperQuestion> paperQuestionList = new ArrayList<>();

        for (ExamPaperQuestionVO epQue : examPaperQuestionList) {
            ExamPaperQuestion question = new ExamPaperQuestion();
            question.setPaperId(pk.toString());
            question.setQuId(epQue.getId().toString());
            question.setSort(epQue.getSort());
            paperQuestionList.add(question);
        }

        //有试题的试卷则保存试卷试题
        if (!paperQuestionList.isEmpty()) {
            questionMapper.insertBatch(paperQuestionList);
        }
    }

    /**
     * 获取试卷结果
     *
     * @param examId 考试ID
     * @return 试卷详情
     */
    @Override
    public ExamPaperDetailsVO getPaperDetails(String examId) {
        return examPaperMapper.getPaperDetails(examId);
    }


    /**
     * 获取试卷试题总分
     * @param paperId
     * @return
     */
    @Override
    public Integer paperQuestionTotalScore(Long paperId){
        return examPaperMapper.paperQuestionTotalScore(paperId);
    }

    /**
     * 删除试卷
     * @param paperId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePaper(Long paperId){
        ExamPaper examPaper = new ExamPaper();
        examPaper.setId(paperId);
        examPaper.setIsDeleted(1);
        this.updateSelective(examPaper);
    }
}
