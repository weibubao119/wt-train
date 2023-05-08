package com.dyys.hr.controller.exam;

import com.dagongma.kernel.commons.enums.ResultCode;
import com.dagongma.kernel.commons.exceptions.BusinessException;
import com.dagongma.kernel.core.entity.PageView;
import com.dyys.hr.constants.Constant;
import com.dyys.hr.entity.train.TrainExam;
import com.dyys.hr.entity.train.TrainExaminer;
import com.dyys.hr.entity.train.TrainExaminerDetail;
import com.dyys.hr.helper.UserHelper;
import com.dyys.hr.service.exam.*;
import com.dyys.hr.service.train.TrainExamService;
import com.dyys.hr.service.train.TrainExaminerDetailService;
import com.dyys.hr.service.train.TrainExaminerService;
import com.dyys.hr.utils.Result;
import com.dyys.hr.vo.exam.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * @Author: Daan li
 * @Date: 2022/4/21 17:55
 */
@RestController
@RequestMapping("/exam/result")
@Api(value = "考试中心", tags = {"【考试中心】"})
public class ExamResultController {
    @Autowired
    private TrainExaminerService trainExaminerService;  //考试人员
    @Autowired
    private TrainExaminerDetailService trainExaminerDetailService;  //考试结果
    @Autowired
    private IExamPaperService paperService; //试卷
    @Autowired
    private IExamUserAnswerService examUserAnswerService;  //答题结果
    @Autowired
    private TrainExamService trainExamService;
    @Autowired
    private UserHelper userHelper;

    @GetMapping("pageList")
    @ApiOperation(value = "考试中心列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = "status", value = "状态 0.正在进行 1.已提交", paramType = "query", dataType = "int")
    })
    public Result<PageView<ExamCenterVO>> pageList(@ApiIgnore @RequestParam Map<String, Object> params) {
        params.put("userId", userHelper.getLoginEmplId());
        return new Result<PageView<ExamCenterVO>>().ok(trainExaminerService.pageExamCenter(params));
    }

    @PostMapping("/add")
    @ApiOperation(value = "考试结果提交")
    @Transactional(rollbackFor = Exception.class)  //事务处理
    public Result<TrainExaminerDetail> addExamResult(@RequestBody ExaminationResultVO examResult) {
        //得到当前用户
        TrainExaminer examiner = trainExaminerService.getExaminer(examResult.getExamId(), userHelper.getLoginEmplId());
        //没有找到该记录
        if (examiner == null) {
            throw new BusinessException(ResultCode.EXCEPTION,"该用户不能参与该考试!");
        }
        //校验考试次数
        TrainExam trainExam = trainExamService.selectById(examiner.getExamId());
        if(examiner.getExamNum() >= trainExam.getExamCount()){
            throw new BusinessException(ResultCode.EXCEPTION,"该用户已达考试上限次数!");
        }

        return new Result<TrainExaminerDetail>().ok(examUserAnswerService.addExamResult(examResult, examiner));
    }

    @GetMapping("/getDetails")
    @ApiOperation(value = "查看考试结果详情")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "detailsId", value = "人员考试记录id", type = "query", required = true, dataType = "string")
            }
    )
    public Result<ExamResultDetailsVO> getExamReultDetails(@RequestParam String detailsId) {
        return new Result<ExamResultDetailsVO>().ok(trainExaminerDetailService.getExamDetails(detailsId));
    }


    @GetMapping("/viewPaperDetails")
    @ApiOperation(value = "查看试卷详情(考试页面)")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "examId", value = "考试ID", type = "query", required = true, dataType = "string")
            }
    )
    public Result<ExamPaperDetailsVO> getPaperDetails(@RequestParam String examId) {
        //校验考试是否可以正常进行
        TrainExam trainExam = trainExamService.selectById(Long.valueOf(examId));
        if(trainExam.getStatus() < 1){
            throw new BusinessException(ResultCode.EXCEPTION,"考试未开始,不能作答!");
        }
        if(trainExam.getStatus() > 1){
            throw new BusinessException(ResultCode.EXCEPTION,"考试已结束,不能作答!");
        }
        return new Result<ExamPaperDetailsVO>().ok(paperService.getPaperDetails(examId));
    }
}
