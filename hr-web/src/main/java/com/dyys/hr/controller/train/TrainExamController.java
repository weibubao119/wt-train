package com.dyys.hr.controller.train;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.dagongma.kernel.annotation.ResponseResult;
import com.dagongma.kernel.commons.enums.ResultCode;
import com.dagongma.kernel.commons.exceptions.BusinessException;
import com.dyys.hr.constants.Constant;
import com.dyys.hr.dto.train.*;
import com.dyys.hr.entity.train.TrainExam;
import com.dyys.hr.entity.train.TrainExaminer;
import com.dyys.hr.entity.train.excel.*;
import com.dyys.hr.examUtils.EasyExcelListener;
import com.dyys.hr.helper.UserHelper;
import com.dyys.hr.service.exam.IExamUserAnswerService;
import com.dyys.hr.service.train.TrainExamService;
import com.dyys.hr.service.train.TrainExaminerDetailService;
import com.dyys.hr.service.train.TrainExaminerService;
import com.dyys.hr.utils.Result;
import com.dyys.hr.utils.SelectSheetWriteHandler;
import com.dyys.hr.vo.train.*;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.*;

/**
 * 培训项目考试接口
 *
 * @author WSJ
 * @date 2022/05/20
 */
@Slf4j
@RestController
@RequestMapping("/train/exam")
@Api(value = "培训项目考试接口", tags = {"【培训】【过程管理】培训项目-考试管理-已完成"})
public class TrainExamController {
    @Autowired
    private TrainExamService trainExamService;
    @Autowired
    private TrainExaminerService trainExaminerService;
    @Autowired
    private TrainExaminerDetailService trainExaminerDetailService;
    @Autowired
    private UserHelper userHelper;
    @Autowired
    private IExamUserAnswerService iExamUserAnswerService;

    @ResponseResult
    @GetMapping("pageList")
    @ApiOperation(value = "考试列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
            @ApiImplicitParam(name = "programsId", value = "项目id", paramType = "query", required = true,dataType="int"),
            @ApiImplicitParam(name = "title", value = "考试标题", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "courseId", value = "课程id", paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "status", value = "状态 0.未开始 1.正在进行 2.结束", paramType = "query",dataType="int")
    })
    public PageInfo<TrainExamVO> pageList(@ApiIgnore @RequestParam Map<String, Object> params){
        return trainExamService.pageList(params);
    }

    @ResponseResult
    @PostMapping("save")
    @ApiOperation(value = "创建考试")
    public Long save(@RequestBody @Validated(TrainExamDTO.Insert.class) TrainExamDTO dto) {
        //线上考试增加开始时间校验
        if(dto.getType() == 1 && dto.getStartTime().before(new Date())){
            throw new BusinessException(ResultCode.EXCEPTION,"考试开始时间不能小于当前时间!");
        }

        if(dto.getType() == 1 && dto.getEndTime().before(dto.getStartTime())){
            throw new BusinessException(ResultCode.EXCEPTION,"考试结束时间不能小于开始时间!");
        }
        //线上考试校验同考试试卷模板唯一
        TrainExam query = new TrainExam();
        query.setProgramsId(dto.getProgramsId());
        query.setCourseId(dto.getCourseId());
        query.setPaperId(dto.getPaperId());
        query.setType(dto.getType());
        TrainExam selectOne = trainExamService.selectOne(query);
        if(selectOne != null){
            throw new BusinessException(ResultCode.EXCEPTION,"同类型该关联课程已发起相同考试-"+selectOne.getTitle()+",请勿重复!");
        }

        return trainExamService.save(dto,userHelper.getLoginEmplId());
    }

    @ResponseResult
    @GetMapping("find")
    @ApiOperation(value = "考试详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "考试ID", paramType = "path", required = true, dataType="int") ,
    })
    public TrainExamDetailVO find(@ApiIgnore @RequestParam Long id) {
        return trainExamService.getDetail(id);
    }

    @ResponseResult
    @PostMapping("update")
    @ApiOperation(value = "考试修改")
    public Integer update(@RequestBody @Validated(TrainExamDTO.Update.class) TrainExamDTO dto) {
        if(dto.getEndTime().before(dto.getStartTime())){
            throw new BusinessException(ResultCode.EXCEPTION,"考试结束时间不能小于开始时间!");
        }
        //已结束的考试不能修改
        TrainExam trainExam = trainExamService.selectById(dto.getId());
        if(trainExam.getStatus() > 0){
            throw new BusinessException(ResultCode.EXCEPTION,"考试正在进行或已结束无法修改!");
        }

        return trainExamService.update(dto,userHelper.getLoginEmplId());
    }

    @ResponseResult
    @GetMapping("examinerDetail")
    @ApiOperation(value = "查看-参考人员-考试明细")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "examinerId", value = "参考记录id", paramType = "path", required = true, dataType="int") ,
    })
    public List<TrainExaminerDetailVO> examinerDetail(@ApiIgnore @RequestParam Long examinerId) {
        Map<String, Object> query = new HashMap<>();
        query.put("examiner_id",examinerId);
        return trainExaminerDetailService.getListByQuery(query);
    }

    @ResponseResult
    @PostMapping("restExam")
    @ApiOperation(value = "退回重考")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "examinerId", value = "人员参考记录id", paramType = "path", required = true, dataType="int") ,
            @ApiImplicitParam(name = "reason", value = "退回重考原因", paramType = "path", dataType="String")
    })
    public Integer remove(@ApiIgnore @RequestBody Map<String, Object> params) {
        params.put("userId",userHelper.getLoginEmplId());
        return trainExaminerService.restExam(params);
    }

    @ResponseResult
    @GetMapping("checkExamDetailsList")
    @ApiOperation(value = "查看项目考试明细列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
            @ApiImplicitParam(name = "programsId", value = "项目id", paramType = "query", required = true,dataType="int"),
            @ApiImplicitParam(name = "title", value = "考试标题", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "courseId", value = "课程id", paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "type", value = "考试类型 1:线上考试，2:线下考试)", paramType = "query",dataType="int"),
            @ApiImplicitParam(name = "status", value = "状态 0.未开始 1.正在进行 2.结束", paramType = "query",dataType="int"),
            @ApiImplicitParam(name = "emplId", value = "学员工号", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "emplName", value = "学员姓名", paramType = "query",dataType="String"),
    })
    public PageInfo<TrainExamCheckDetailListVO> checkExamDetailsList(@ApiIgnore @RequestParam Map<String, Object> params){
        return trainExamService.checkExamDetailsList(params);
    }

    @ResponseResult
    @GetMapping("/exportExamDetailsList")
    @ApiOperation(value = "导出项目考试明细列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "programsId", value = "项目id", paramType = "query", required = true,dataType="int"),
            @ApiImplicitParam(name = "title", value = "考试标题", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "courseId", value = "课程id", paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "type", value = "考试类型 1:线上考试，2:线下考试)", paramType = "query",dataType="int"),
            @ApiImplicitParam(name = "status", value = "状态 0.未开始 1.正在进行 2.结束", paramType = "query",dataType="int"),
            @ApiImplicitParam(name = "emplId", value = "学员工号", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "emplName", value = "学员姓名", paramType = "query",dataType="String"),
    })
    public void exportExamDetailsList(HttpServletResponse response, @ApiIgnore @RequestParam Map<String, Object> params) throws IOException{
        List<ExamDetailListExcel> excelList = trainExamService.exportExamDetailsList(params);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("项目考试明细数据", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

        // 绕过了创建临时文件，直接将数据读到流中传递至客户端
        EasyExcelFactory.write(response.getOutputStream(), ExamDetailListExcel.class).sheet("明细数据").doWrite(excelList);
    }

    @ResponseResult
    @GetMapping("/exportThisExamDetailsList")
    @ApiOperation(value = "导出当前考试明细列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "examId", value = "考试id", paramType = "query", required = true,dataType="int"),
    })
    public void exportThisExamDetailsList(HttpServletResponse response, @ApiIgnore @RequestParam Map<String, Object> params) throws IOException{
        List<ExamDetailListExcel> excelList = trainExamService.exportThisExamDetailsList(params);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("考试明细数据", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

        // 绕过了创建临时文件，直接将数据读到流中传递至客户端
        EasyExcelFactory.write(response.getOutputStream(), ExamDetailListExcel.class).sheet("明细数据").doWrite(excelList);
    }



    @GetMapping("/exportUserExamDetail")
    @ApiOperation(value = "下载用户试卷详情")
    public void exportUserExamDetail(HttpServletResponse response,String detailsId) throws IOException{
        //获取对应答题记录列表
        List<UserExamDetailExcel> examQuestions = iExamUserAnswerService.listUserExamDetail(detailsId);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("试卷详情", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

        // 绕过了创建临时文件，直接将数据读到流中传递至客户端
        EasyExcelFactory.write(response.getOutputStream(), UserExamDetailExcel.class).sheet("试卷详情").doWrite(examQuestions);
    }

    @GetMapping("/exportOfflineExamResultsTml")
    @ApiOperation(value = "下载线下考试成绩导入模板")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "examId", value = "项目考试id", paramType = "query", dataType="Long", required = true)
    })
    public void exportOfflineExamResultsTml(HttpServletResponse response, @RequestParam Long examId) throws IOException{
        //获取对应考试人员列表
        List<OfflineExamResultsExcel> excelList = trainExaminerService.getExaminerListTml(examId);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("考试成绩导入模板", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ExcelTypeEnum.XLSX.getValue());

        // 列下拉框数据
        Map<Integer, List<String>> selectMap = trainExamService.excelSelectMap();

        // 绕过了创建临时文件，直接将数据读到流中传递至客户端
        EasyExcelFactory.write(response.getOutputStream(), OfflineExamResultsExcel.class)
                .registerWriteHandler(new SelectSheetWriteHandler(selectMap))
                .excelType(ExcelTypeEnum.XLSX)
                .sheet("成绩详情")
                .doWrite(excelList);
    }

    @PostMapping(value = "/importOfflineExamResults", headers = "content-type=multipart/form-data")
    @ApiOperation(value = "导入线下考试成绩")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "examId", value = "项目考试id", paramType = "query", dataType="int", required = true),
            @ApiImplicitParam(name = "file", value = "导入数据excel", dataTypeClass = MultipartFile.class, required = true, allowMultiple = true)
    })
    public Result<List<OfflineExamResultsExcel>> importOfflineExamResults(@RequestBody MultipartFile file, Long examId) throws IOException {
        if (file == null) {
            return new Result<List<OfflineExamResultsExcel>>().error("请上传要导入的文件");
        }
        if (examId == null) {
            return new Result<List<OfflineExamResultsExcel>>().error("缺少参数考试记录ID");
        }
        TrainExam trainExam = trainExamService.selectById(examId);
        if(trainExam == null){
            return new Result<List<OfflineExamResultsExcel>>().error("考试信息不存在");
        }
        if(trainExam.getType() != 2){
            return new Result<List<OfflineExamResultsExcel>>().error("非线下考试，不可导入成绩");
        }
        //定义最终入库的数据集合
        EasyExcelListener<OfflineExamResultsExcel> listener = new EasyExcelListener<>();
        List<OfflineExamResultsExcel> excelList =
                EasyExcelFactory.read(file.getInputStream(), OfflineExamResultsExcel.class, listener)
                        .sheet().doReadSync();
        if (excelList.isEmpty()) {
            return new Result<List<OfflineExamResultsExcel>>().error("导入文件数据不能为空");
        }
        List<OfflineExamResultsExcel> errorList = trainExaminerService.importExl(excelList, examId, userHelper.getLoginEmplId());
        if (errorList.isEmpty()) {
            return new Result<List<OfflineExamResultsExcel>>().success("考试成绩导入成功");
        }
        return new Result<List<OfflineExamResultsExcel>>().error("考试成绩数据校验不通过", errorList);
    }

    @ResponseResult
    @PostMapping("editOfflineResults")
    @ApiOperation(value = "线下考试成绩修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "examinerId", value = "人员参考记录id", paramType = "path", required = true, dataType="int") ,
            @ApiImplicitParam(name = "highestScore", value = "最高分", paramType = "path", dataType="string") ,
            @ApiImplicitParam(name = "status", value = "是否通过 1.是 0.否", paramType = "path", dataType="int")
    })
    public Integer editOfflineResults(@ApiIgnore @RequestBody Map<String, Object> params) {
        TrainExaminer trainExaminer = new TrainExaminer();
        trainExaminer.setId(Long.valueOf(params.get("examinerId").toString()));
        trainExaminer.setHighestScore(params.get("highestScore").toString());
        trainExaminer.setStatus(Integer.valueOf(params.get("status").toString()));
        trainExaminer.setUpdateUser(userHelper.getLoginEmplId());
        trainExaminer.setUpdateTime(System.currentTimeMillis() / 1000);
        return trainExaminerService.updateSelective(trainExaminer);
    }

    @PostMapping("batchExamNotice")
    @ApiOperation(value = "批量考试通知提醒")
    public Boolean batchExamNotice(@RequestBody @Validated(IdDTO.Insert.class) List<IdDTO> dtoList) {
        return trainExaminerService.batchExamNotice(dtoList,userHelper.getLoginEmplId());
    }

    @GetMapping("exportExamResultTml")
    @ApiOperation(value = "下载考试结果导入模板")
    public void exportExamResultTml(HttpServletResponse response) throws IOException {
        List<TrainExamResultImportExcel> excelList = new ArrayList<>();
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("培训班考试结果导入模板", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ExcelTypeEnum.XLS.getValue());

        // 绕过了创建临时文件，直接将数据读到流中传递至客户端
        EasyExcelFactory.write(response.getOutputStream(), TrainExamResultImportExcel.class)
                .excelType(ExcelTypeEnum.XLS)
                .sheet("培训班考试结果导入模板")
                .doWrite(excelList);
    }


    @PostMapping(value = "importExamResult", headers = "content-type=multipart/form-data")
    @ApiOperation(value = "考试结果导入")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "考试结果excel", dataTypeClass = MultipartFile.class, required = true, allowMultiple = true),
            @ApiImplicitParam(name = "id", value = "考试id", paramType = "query", required = true,dataType="Long")
    })
    public Result<TrainExamResultImportExcelVO> importExamResult(@RequestBody MultipartFile file,Long id) throws IOException, ParseException {
        EasyExcelListener<TrainExamResultImportExcel> listener = new EasyExcelListener<>();
        List<TrainExamResultImportExcel> excelList = new ArrayList<>();
        try {
            excelList = EasyExcelFactory.read(file.getInputStream(), TrainExamResultImportExcel.class, listener)
                    .sheet(0)
                    .doReadSync();
        } catch (Exception e) {
            return new Result<TrainExamResultImportExcelVO>().error("文件识别异常：" + e.getMessage());
        }
        if (excelList.isEmpty()) {
            return new Result<TrainExamResultImportExcelVO>().error("考试结果数据不能为空");
        }
        TrainExamResultImportExcelVO excelVO = trainExaminerService.handleResultImportExcel(excelList,id,userHelper.getLoginEmplId());
        if (!excelVO.getErrorList().isEmpty()) {
            return new Result<TrainExamResultImportExcelVO>().error("数据校验不通过", excelVO);
        }
        return new Result<TrainExamResultImportExcelVO>().success("导入成功", excelVO);
    }
}