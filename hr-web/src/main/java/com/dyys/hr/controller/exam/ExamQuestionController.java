package com.dyys.hr.controller.exam;

import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.dagongma.kernel.core.entity.PageQuery;
import com.dagongma.kernel.core.entity.PageView;
import com.dyys.hr.constant.exam.ExamContant;
import com.dyys.hr.entity.exam.ExamPaperQuestion;
import com.dyys.hr.entity.exam.ExamQuestion;
import com.dyys.hr.entity.exam.ExamQuestionAnswer;
import com.dyys.hr.entity.exam.ExamQuestionExcel;
import com.dyys.hr.examUtils.EasyExcelListener;
import com.dyys.hr.helper.UserHelper;
import com.dyys.hr.service.exam.IExamPaperQuestionService;
import com.dyys.hr.service.exam.IExamQuestionAnswerService;
import com.dyys.hr.service.exam.IExamQuestionService;
import com.dyys.hr.utils.Result;
import com.dyys.hr.utils.SelectSheetWriteHandler;
import com.dyys.hr.vo.exam.ExamQuDetialsVO;
import com.dyys.hr.vo.exam.ExamQuestionVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;
import tk.mybatis.mapper.util.StringUtil;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

/**
 * @Author: Daan li
 * @Date: 2022/4/20 17:03
 */
@RestController
@RequestMapping("/exam/question")
@Api(value = "试题管理", tags = {"【试题接口】"})
public class ExamQuestionController {
    @Autowired
    private IExamQuestionService examQuestionService;
    @Autowired
    private IExamQuestionAnswerService examQuestionAnswerService;
    @Autowired
    private IExamPaperQuestionService examPaperQuestionService;
    @Autowired
    private UserHelper userHelper;

    @PostMapping("/add")
    @ApiOperation(value = "新增试题")
    @Transactional(rollbackFor = Exception.class)  //事务处理
    public Result<ExamQuestion> addExamQuestion(@RequestBody ExamQuestionVO question) {
        boolean res = examQuestionService.checkUniqueData(question);
        if (res) {
            return new Result<ExamQuestion>().error("同类型题目内容(标题)已存在当前试卷中");
        }
        //保存题目
        ExamQuestion examQuestion = Convert.convert(ExamQuestion.class, question);
        examQuestion.setCreateDate(new Date());
        examQuestion.setCreator(userHelper.getLoginEmplId());
        examQuestion.setIsDeleted(false);

        examQuestionService.insertSelective(examQuestion);
        //正确答案
        String rightAnswer = "";

        //判断题和主观题不需要传答案选项。
        //判断题答案放在question.rightAnswer字段中，1为正确,0为错误
        if (question.getQuType() != ExamContant.QUEST_TYPE_JUDGE) {
            //保存答案
            for (int i = 0; i < question.getAnswers().size(); i++) {
                ExamQuestionAnswer answer = question.getAnswers().get(i);
                answer.setSort(i + 1);
                if (answer.getIsRight() == 1) {
                    if (!StringUtil.isEmpty(rightAnswer)) {
                        rightAnswer += "、";
                    }

                    rightAnswer += answer.getItemIndex();
                }
                answer.setQuId(examQuestion.getId().toString());
                examQuestionAnswerService.insertSelective(answer);
            }
        } else {
            //为保证结构的完整性，判断题的答案在答案表中增加一条记录保存
            ExamQuestionAnswer answer = new ExamQuestionAnswer();
            answer.setSort(1);
            answer.setIsRight(Integer.valueOf(question.getRightAnswer()));
            answer.setQuId(examQuestion.getId().toString());
            examQuestionAnswerService.insertSelective(answer);
            rightAnswer = question.getRightAnswer();
        }

        //保存正确答案
        examQuestion.setRightAnswer(rightAnswer);
        examQuestionService.update(examQuestion);

        //保存试卷试题
        ExamPaperQuestion examPaperQuestion = new ExamPaperQuestion();
        examPaperQuestion.setPaperId(question.getPaperId());
        examPaperQuestion.setQuId(examQuestion.getId().toString());
        examPaperQuestion.setSort(question.getSort());
        examPaperQuestionService.insertSelective(examPaperQuestion);

        return new Result<ExamQuestion>().ok(examQuestion);
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改试题")
    @Transactional(rollbackFor = Exception.class)  //事务处理
    public Result<ExamQuestion> updateExamQuestion(@RequestBody ExamQuestionVO question) {
        boolean res = examQuestionService.checkUniqueData(question);
        if (res) {
            return new Result<ExamQuestion>().error("同类型题目内容(标题)已存在当前试卷中");
        }
        //保存题目
        ExamQuestion examQuestion = Convert.convert(ExamQuestion.class, question);
        examQuestion.setIsDeleted(false);

        //删除答案
        examQuestionAnswerService.deleteByQuestion(examQuestion.getId());

        //正确答案
        String rightAnswer = "";

        //保存答案
        for (int i = 0; i < question.getAnswers().size(); i++) {
            ExamQuestionAnswer answer = question.getAnswers().get(i);
            answer.setQuId(examQuestion.getId().toString());
            answer.setSort(i + 1);
            if (answer.getIsRight() == 1) {
                if (!StringUtil.isEmpty(rightAnswer)) {
                    rightAnswer += "、";
                }

                rightAnswer += answer.getItemIndex();
            }
            examQuestionAnswerService.insertSelective(answer);
        }

        //修改试题
//        examQuestion.setCreator(userHelper.getLoginEmplId());
//        examQuestion.setCreateDate(new Date());
        examQuestion.setRightAnswer(rightAnswer);
        examQuestionService.updateSelective(examQuestion);
        return new Result<ExamQuestion>().ok(examQuestion);
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除试题")
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> deleteExamQuestion(@RequestBody @NotNull(message = "主键id不能为空") String quId) {
        Map<String, Object> params = JSONUtil.parseObj(quId);
        Long questionId = Long.valueOf((String) params.get("quId"));
        ExamQuestion examQuestion = examQuestionService.selectById(questionId);

        if (examQuestion != null) {
            //删除问题
            examQuestion.setIsDeleted(true);
            examQuestionService.updateSelective(examQuestion);
        }

        examPaperQuestionService.deleteExam(questionId.toString());
        return new Result<Boolean>().success("删除成功");
    }

    @GetMapping("/list")
    @ApiOperation(value = "获取试题列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "paperId", value = "试卷Id", paramType = "query", dataType = "int"),
    })
    public Result<List<ExamQuestion>> listExamQuestion(@ApiIgnore @RequestParam Map<String, Object> params) {
        return new Result<List<ExamQuestion>>().ok(examQuestionService.listExamQuestion(params));
    }

    @PostMapping("/page")
    @ApiOperation(value = "分页获取试题列表")
    public Result<PageView<ExamQuestion>> pageExamQuestion(@RequestBody PageQuery<Map<String, Object>> pageQuery) {
        return new Result<PageView<ExamQuestion>>().ok(examQuestionService.pageExamQuestion(pageQuery));
    }

    @GetMapping("/get")
    @ApiOperation(value = "获取试题详情")
    public Result<ExamQuDetialsVO> getExamQuestion(@RequestParam @NotNull(message = "主键id不能为空") Long quId) {
        return new Result<ExamQuDetialsVO>().ok(examQuestionService.getExamQuestion(quId));
    }

    @GetMapping("/export")
    @ApiOperation(value = "下载试卷题目模板")
    public void excelExport(HttpServletResponse response) throws IOException {
        List<ExamQuestionExcel> examQuestions = new ArrayList<>();
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("考试题模板", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ExcelTypeEnum.XLSX.getValue());

        // 列下拉框数据
        Map<Integer, List<String>> selectMap = examQuestionService.excelSelectMap();

        // 绕过了创建临时文件，直接将数据读到流中传递至客户端
        EasyExcelFactory.write(response.getOutputStream(), ExamQuestionExcel.class)
                .registerWriteHandler(new SelectSheetWriteHandler(selectMap))
                .excelType(ExcelTypeEnum.XLSX)
                .sheet("考试题")
                .doWrite(examQuestions);
    }

    /**
     * @param file 上传的文件
     * @return
     * @throws IOException
     */
    @PostMapping(value = "import", headers = "content-type=multipart/form-data")
    @ApiOperation(value = "试题批量导入")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "试题excel", dataTypeClass = MultipartFile.class, required = true, allowMultiple = true),
            @ApiImplicitParam(name = "paperId", value = "试卷ID", paramType = "query", dataType="long", required = true)
    })
    @Transactional(rollbackFor = Exception.class)
    public Result<List<ExamQuestionExcel>> excelImport(@RequestBody MultipartFile file,Long paperId) throws IOException {
        if (paperId == null) {
            return new Result<List<ExamQuestionExcel>>().error("缺少参数试卷ID");
        }
        if (file == null) {
            return new Result<List<ExamQuestionExcel>>().error("请上传要导入的文件");
        }
        //定义最终返回的题目集合
        EasyExcelListener<ExamQuestionExcel> listener = new EasyExcelListener<>();
        List<ExamQuestionExcel> excelList =
                EasyExcelFactory.read(file.getInputStream(), ExamQuestionExcel.class, listener)
                        .sheet(0).doReadSync();
        if (excelList.isEmpty()) {
            return new Result<List<ExamQuestionExcel>>().error("导入文件考试题数据不能为空");
        }
        List<ExamQuestionExcel> errorList = examQuestionService.importExl(excelList, paperId, userHelper.getLoginEmplId());
        if (errorList.isEmpty()) {
            return new Result<List<ExamQuestionExcel>>().success("考试题目导入成功");
        }
        return new Result<List<ExamQuestionExcel>>().error("考试题数据校验不通过", errorList);
    }
    /*@PostMapping(value = "import", headers = "content-type=multipart/form-data")
    @ApiOperation(value = "试题批量导入")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "试题excel", dataTypeClass = MultipartFile.class, required = true, allowMultiple = true),
            @ApiImplicitParam(name = "paperId", value = "试卷ID", paramType = "query", dataType="long", required = true)
    })
    @Transactional(rollbackFor = Exception.class)
    public Result<List<ExamQuestionVO>> excelImport(@RequestBody MultipartFile file,Long paperId) throws IOException {
        //定义最终返回的题目集合
        List<ExamQuestionVO> examQuestionVOS = new ArrayList<>();
        EasyExcelListener<ExamQuestionExcel> listener = new EasyExcelListener<>();
        List<ExamQuestionExcel> excelList =
                EasyExcelFactory.read(file.getInputStream(), ExamQuestionExcel.class, listener)
                        .sheet().doReadSync();

        //导入文档判空
        if (!CollUtil.isEmpty(excelList)) {
            //非空遍历导入题目集合
            int sort = 1;
            for (ExamQuestionExcel questionExcel : excelList) {
                //todo 确定关于题目答案选项放在表格还是导入后在页面上补充
                //转成入库集合
                ExamQuestion question = Convert.convert(ExamQuestion.class, questionExcel);
                //转成返回给前端集合
                ExamQuestionVO questionVo = Convert.convert(ExamQuestionVO.class, questionExcel);

                //每遍历一条题目，初始化答案集合
                List<ExamQuestionAnswer> questionAnswers = new ArrayList<>();

                question.setCreateDate(new Date());//入库创建日期
                question.setCreator(userHelper.getLoginEmplId());//入库创建人
                question.setIsDeleted(false);//未删除
                examQuestionService.insertSelective(question);//将题入库

                //主观题不处理答案
                if(question.getQuType() != 4){
                    String[] questArray = questionExcel.getAnswers().split("\n");
                    String[] rightAnswerArray = questionExcel.getRightAnswer().split("、");
                    List<String> answerList = Arrays.asList(questArray);
                    List<String> rightAnswerList = Arrays.asList(rightAnswerArray);
                    int itemIndex = 97;
                    for (String s : answerList) {
                        ExamQuestionAnswer answer = new ExamQuestionAnswer();
                        answer.setContent(s);//设置答案内容
                        answer.setQuId(String.valueOf(question.getId()));//设置题目id
                        answer.setItemIndex(String.valueOf((char) itemIndex));//设置abcd
                        answer.setSort(answerList.indexOf(s) + 1);//设置序号
                        answer.setIsRight(0);

                        //判断是否为正确答案
                        for (String rightAnswer : rightAnswerList) {
                            if (answer.getItemIndex().equalsIgnoreCase(rightAnswer)) {
                                answer.setIsRight(1);
                                break;
                            }
                        }
                        if(question.getQuType() == 3){
                            answer.setIsRight(1);
                        }
                        examQuestionAnswerService.insertSelective(answer);//答案入库
                        questionAnswers.add(answer);//添加至答案集合
                        itemIndex++;
                    }
                    questionVo.setAnswers(questionAnswers);//答案集合放入对应题目
                    examQuestionVOS.add(questionVo);//讲题目添加至集合
                }
                //试卷关联新增试题
                ExamPaperQuestion examPaperQuestion = new ExamPaperQuestion();
                examPaperQuestion.setPaperId(paperId.toString());
                examPaperQuestion.setQuId(String.valueOf(question.getId()));
                examPaperQuestion.setSort(sort);
                examPaperQuestionService.insertSelective(examPaperQuestion);
                sort++;
            }
        }
        return new Result<List<ExamQuestionVO>>().ok(examQuestionVOS);
    }*/
}