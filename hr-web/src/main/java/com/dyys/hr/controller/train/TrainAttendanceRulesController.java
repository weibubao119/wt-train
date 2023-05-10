package com.dyys.hr.controller.train;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.dagongma.kernel.annotation.ResponseResult;
import com.dagongma.kernel.commons.enums.ResultCode;
import com.dagongma.kernel.commons.exceptions.BusinessException;
import com.dyys.hr.constants.Constant;
import com.dyys.hr.dto.train.IdDTO;
import com.dyys.hr.dto.train.TrainAttendancePersonDTO;
import com.dyys.hr.dto.train.TrainAttendanceRulesDTO;
import com.dyys.hr.entity.train.excel.TrainAttendanceRecordExcel;
import com.dyys.hr.entity.train.excel.TrainAttendanceRecordImportExcel;
import com.dyys.hr.entity.train.excel.TrainAttendanceRulesExcel;
import com.dyys.hr.examUtils.EasyExcelListener;
import com.dyys.hr.helper.UserHelper;
import com.dyys.hr.service.train.TrainAttendancePersonService;
import com.dyys.hr.service.train.TrainAttendanceRecordService;
import com.dyys.hr.service.train.TrainAttendanceRulesService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 培训项目考勤接口
 *
 * @author WSJ
 * @date 2022/06/06
 */
@ResponseResult
@Slf4j
@RestController
@RequestMapping("/train/attendance")
@Api(value = "培训项目考勤接口", tags = {"【培训】【过程管理】培训项目-考勤管理-已完成"})
public class TrainAttendanceRulesController {
    @Autowired
    private TrainAttendanceRulesService trainAttendanceRulesService;
    @Autowired
    private TrainAttendancePersonService trainAttendancePersonService;
    @Autowired
    private TrainAttendanceRecordService trainAttendanceRecordService;
    @Autowired
    private UserHelper userHelper;

    @GetMapping("pageList")
    @ApiOperation(value = "考勤管理列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
            @ApiImplicitParam(name = "programsId", value = "项目id", paramType = "query", required = true,dataType="int"),
            @ApiImplicitParam(name = "courseName", value = "课程名称", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "startTime", value = "考勤开始日期", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "endTime", value = "考勤结束日期", paramType = "query",dataType="String"),
    })
    public PageInfo<TrainAttendanceRulesVO> pageList(@ApiIgnore @RequestParam Map<String, Object> params){
        return trainAttendanceRulesService.pageList(params);
    }

    @GetMapping("ruleListExport")
    @ApiOperation(value = "考勤管理列表导出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "programsId", value = "项目id", paramType = "query", required = true,dataType="int"),
            @ApiImplicitParam(name = "courseName", value = "课程名称", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "startTime", value = "考勤开始日期", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "endTime", value = "考勤结束日期", paramType = "query",dataType="String"),
    })
    public void ruleListExport(HttpServletResponse response,@ApiIgnore @RequestParam Map<String, Object> params) throws IOException {
        List<TrainAttendanceRulesExcel> excelList = trainAttendanceRulesService.ruleListExport(params);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyExcel没有关系
        String fileName = URLEncoder.encode("考勤管理列表导出表", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

        // 绕过了创建临时文件，直接将数据读到流中传递至客户端
        EasyExcelFactory.write(response.getOutputStream(), TrainAttendanceRulesExcel.class).sheet("考勤列表").doWrite(excelList);
    }

    @GetMapping("recordPageList")
    @ApiOperation(value = "考勤明细列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
            @ApiImplicitParam(name = "programsId", value = "项目id", paramType = "query", required = true,dataType="int"),
            @ApiImplicitParam(name = "emplName", value = "学员姓名", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "courseName", value = "课程名称", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "startTime", value = "考勤开始日期", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "endTime", value = "考勤结束日期", paramType = "query",dataType="String"),
    })
    public PageInfo<TrainAttendanceRecordPageVO> recordPageList(@ApiIgnore @RequestParam Map<String, Object> params){
        return trainAttendanceRecordService.recordPageList(params);
    }

    @GetMapping("/recordExport")
    @ApiOperation(value = "考勤明细导出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
            @ApiImplicitParam(name = "programsId", value = "项目id", paramType = "query", required = true,dataType="int"),
            @ApiImplicitParam(name = "emplName", value = "学员姓名", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "courseName", value = "课程名称", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "startTime", value = "考勤开始日期", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "endTime", value = "考勤结束日期", paramType = "query",dataType="String"),
    })
    public void recordExport(HttpServletResponse response,@ApiIgnore @RequestParam Map<String, Object> params) throws IOException {
        List<TrainAttendanceRecordExcel> excelList = trainAttendanceRecordService.recordExportList(params);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyExcel没有关系
        String fileName = URLEncoder.encode("考勤明细导出表", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

        // 绕过了创建临时文件，直接将数据读到流中传递至客户端
        EasyExcelFactory.write(response.getOutputStream(), TrainAttendanceRecordExcel.class).sheet("记录列表").doWrite(excelList);
    }

    @PostMapping("save")
    @ApiOperation(value = "创建考勤")
    public Long save(@RequestBody @Validated(TrainAttendanceRulesDTO.Insert.class) TrainAttendanceRulesDTO dto) {
        String resultMsg = trainAttendanceRulesService.checkData(dto);
        if (!resultMsg.equals("")) {
            throw new BusinessException(ResultCode.EXCEPTION, resultMsg);
        }
        return trainAttendanceRulesService.save(dto,userHelper.getLoginEmplId());
    }

    @GetMapping("find")
    @ApiOperation(value = "考勤详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "考勤ID", paramType = "path", required = true, dataType="int") ,
    })
    public TrainAttendanceRulesDetailVO find(@ApiIgnore @RequestParam Long id) {
        return trainAttendanceRulesService.getDetail(id);
    }

    @PostMapping("update")
    @ApiOperation(value = "考勤修改")
    public Integer update(@RequestBody @Validated(TrainAttendanceRulesDTO.Update.class) TrainAttendanceRulesDTO dto) {
        String resultMsg = trainAttendanceRulesService.checkData(dto);
        if (!resultMsg.equals("")) {
            throw new BusinessException(ResultCode.EXCEPTION, resultMsg);
        }
        return trainAttendanceRulesService.update(dto,userHelper.getLoginEmplId());
    }

    @PostMapping("addStudents")
    @ApiOperation(value = "添加学员")
    public Boolean addStudents(@RequestBody List<TrainAttendancePersonDTO> dtoList) {
        return trainAttendancePersonService.addStudents(dtoList,userHelper.getLoginEmplId());
    }

    @PostMapping("removeStudents")
    @ApiOperation(value = "移除学员")
    public Integer removeStudents(@RequestBody @Validated(IdDTO.Insert.class) List<IdDTO> dtoList) {
        return trainAttendancePersonService.removeStudents(dtoList,userHelper.getLoginEmplId());
    }


    @GetMapping("exportAttendanceRecordTml")
    @ApiOperation(value = "下载考勤结果导入模板")
    public void exportAttendanceRecordTml(HttpServletResponse response) throws IOException {
        List<TrainAttendanceRecordImportExcel> excelList = new ArrayList<>();
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("培训班考勤结果导入模板", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ExcelTypeEnum.XLS.getValue());

        // 列下拉框数据
        Map<Integer, List<String>> selectMap = trainAttendanceRecordService.excelSelectMap();

        // 绕过了创建临时文件，直接将数据读到流中传递至客户端
        EasyExcelFactory.write(response.getOutputStream(), TrainAttendanceRecordImportExcel.class)
                .registerWriteHandler(new SelectSheetWriteHandler(selectMap))
                .excelType(ExcelTypeEnum.XLS)
                .sheet("培训班考勤结果导入模板")
                .doWrite(excelList);
    }


    @PostMapping(value = "importAttendanceRecord", headers = "content-type=multipart/form-data")
    @ApiOperation(value = "考勤结果导入")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "考勤结果excel", dataTypeClass = MultipartFile.class, required = true, allowMultiple = true),
            @ApiImplicitParam(name = "id", value = "考勤规则id", paramType = "query", required = true,dataType="Long")
    })
    public Result<TrainAttendanceRecordImportExcelVO> importAttendanceRecord(@RequestBody MultipartFile file,Long id) throws IOException {
        EasyExcelListener<TrainAttendanceRecordImportExcel> listener = new EasyExcelListener<>();
        List<TrainAttendanceRecordImportExcel> excelList = new ArrayList<>();
        try {
            excelList = EasyExcelFactory.read(file.getInputStream(), TrainAttendanceRecordImportExcel.class, listener)
                    .sheet(0)
                    .doReadSync();
        } catch (Exception e) {
            return new Result<TrainAttendanceRecordImportExcelVO>().error("文件识别异常：" + e.getMessage());
        }
        if (excelList.isEmpty()) {
            return new Result<TrainAttendanceRecordImportExcelVO>().error("考勤数据不能为空");
        }
        TrainAttendanceRecordImportExcelVO excelVO = trainAttendanceRecordService.handleAttendanceImportExcel(excelList,id,userHelper.getLoginEmplId());
        if (!excelVO.getErrorList().isEmpty()) {
            return new Result<TrainAttendanceRecordImportExcelVO>().error("数据校验不通过", excelVO);
        }
        return new Result<TrainAttendanceRecordImportExcelVO>().success("导入成功", excelVO);
    }
}