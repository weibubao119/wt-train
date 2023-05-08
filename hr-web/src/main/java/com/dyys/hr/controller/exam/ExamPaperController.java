package com.dyys.hr.controller.exam;

import cn.hutool.core.convert.Convert;
import com.dagongma.kernel.commons.enums.ResultCode;
import com.dagongma.kernel.commons.exceptions.BusinessException;
import com.dagongma.kernel.core.entity.PageQuery;
import com.dagongma.kernel.core.entity.PageView;
import com.dyys.hr.entity.exam.ExamPaper;
import com.dyys.hr.entity.exam.ExamPaperQuestion;
import com.dyys.hr.helper.UserHelper;
import com.dyys.hr.service.exam.IExamPaperQuestionService;
import com.dyys.hr.service.exam.IExamPaperService;
import com.dyys.hr.utils.Result;
import com.dyys.hr.vo.exam.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 问题题目 前端控制器
 * </p>
 *
 * @author lidaan
 * @since 2022-04-15
 */
@RestController
@RequestMapping("/exam/paper")
@Api(value = "试卷管理", tags = {"【试卷管理接口】"})
public class ExamPaperController {
    @Autowired
    private IExamPaperService examPaperService;
    @Autowired
    private IExamPaperQuestionService examPaperQuestionService;
    @Autowired
    private UserHelper userHelper;

    @PostMapping("/add")
    @ApiOperation(value = "新增试卷带试题")
    @Transactional(rollbackFor = Exception.class)  //事务处理
    public Result<ExamPaperVO> addExamPaper(@RequestBody ExamPaperVO paper) {
        //保存试卷试题
        int sort = 1;
        for (Long quId : paper.getQuestionList()) {
            ExamPaperQuestion examPaperQuestion = new ExamPaperQuestion();
            examPaperQuestion.setPaperId(paper.getId().toString());
            examPaperQuestion.setQuId(quId.toString());
            examPaperQuestion.setSort(sort++);
            examPaperQuestionService.insertSelective(examPaperQuestion);
        }

        return new Result<ExamPaperVO>().ok(paper);
    }

    @PostMapping("/addPaper")
    @ApiOperation(value = "新增试卷不带试题")
    @Transactional(rollbackFor = Exception.class)  //事务处理
    public Result<ExamPaperDTO> addPaper(@RequestBody ExamPaperDTO paper) {
        //校验试卷名称不重复
        ExamPaper paperQuery = new ExamPaper();
        paperQuery.setName(paper.getName());
        ExamPaper selectOne = examPaperService.selectOne(paperQuery);
        if(selectOne != null){
            throw new BusinessException(ResultCode.EXCEPTION,"相同名称试卷已存在!");
        }

        //保存试卷
        ExamPaper examPaper = Convert.convert(ExamPaper.class, paper);
        //默认待发布
        examPaper.setStatus(0);
        examPaper.setCreator(userHelper.getLoginEmplId());
        examPaper.setCreateDate(new Date());
        Long id = examPaperService.insertSelective(examPaper);
        if (id < 1) {
            throw new BusinessException(ResultCode.EXCEPTION,"试卷新增失败!");
        }
        paper.setId(id);
        return new Result<ExamPaperDTO>().ok(paper);
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改试卷")
    @Transactional(rollbackFor = Exception.class)  //事务处理
    public Result<Boolean> updateExamPaper(@RequestBody ExamPaper paper) {
        //校验发布时候总分
        if(paper.getStatus() == 1){
            Integer totalScore = examPaperService.paperQuestionTotalScore(paper.getId());
            if(Math.round(paper.getTotle()) != totalScore){
                throw new BusinessException(ResultCode.EXCEPTION,"试卷与试题总分不相同!");
            }
        }
        examPaperService.updateSelective(paper);
        return new Result<Boolean>().success("修改成功");
    }

    @PostMapping("/page")
    @ApiOperation(value = "分页获取试卷")
    public Result<PageView<ExamPaperPageVO>> pageExamPaper(@RequestBody PageQuery<Map<String, Object>> pageQuery) {
        Map<String, Object> map = pageQuery.getCondition();
        map.put("deptList",userHelper.getLoginDeptList());
        pageQuery.setCondition(map);
        return new Result<PageView<ExamPaperPageVO>>().ok(examPaperService.pageExamPaper(pageQuery));
    }

    @GetMapping("/get")
    @ApiOperation(value = "获取试卷详情")
    public Result<ExamPaperResp> getPaperExam(@RequestParam Long id) {
        return new Result<ExamPaperResp>().ok(examPaperService.getExamPaper(id));
    }

    @PostMapping("/copyPaper")
    @ApiOperation(value = "复制试卷")
    public Result<Boolean> copyPaper(@RequestBody ExamPaperVO paper) {
        //得到试卷
        examPaperService.copyPaper(paper);
        return new Result<Boolean>().success("复制成功");
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除试卷")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "paperId", value = "试卷id", paramType = "path", required = true, dataType="long") ,
    })
    public Result<Boolean> deletePaper(@ApiIgnore @RequestBody Map<String, Object> params) {
        Long paperId = Long.parseLong(params.get("paperId").toString());
        ExamPaper examPaper = examPaperService.selectById(paperId);
        if(examPaper.getStatus() == 1){
            throw new BusinessException(ResultCode.EXCEPTION,"试卷已发布不能删除!");
        }
        examPaperService.deletePaper(paperId);
        return new Result<Boolean>().success("删除成功");
    }
}
