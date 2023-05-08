package com.dyys.hr.controller.train;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.dagongma.kernel.annotation.ResponseResult;
import com.dyys.hr.constants.Constant;
import com.dyys.hr.dto.train.TrainDemandFeedbackDetailDTO;
import com.dyys.hr.entity.train.excel.DemandFeedbackExcel;
import com.dyys.hr.entity.train.excel.DemandFeedbackImportExcel;
import com.dyys.hr.examUtils.EasyExcelListener;
import com.dyys.hr.helper.UserHelper;
import com.dyys.hr.service.train.TrainDemandFeedbackDetailService;
import com.dyys.hr.utils.Result;
import com.dyys.hr.utils.SelectSheetWriteHandler;
import com.dyys.hr.utils.ValidatorUtils;
import com.dyys.hr.vo.train.TrainDemandFeedbackDetailExcelVO;
import com.dyys.hr.vo.train.TrainDemandFeedbackDetailVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

/**
 * 反馈详情数据接口
 *
 * @author WSJ
 */
@Slf4j
@RestController
@RequestMapping("/train/feedbackDetail")
@Api(value = "反馈详情", tags = {"【培训】【需求管理】反馈详情-已完成"})
public class TrainDemandFeedbackDetailController {
    @Autowired
    private TrainDemandFeedbackDetailService trainDemandFeedbackDetailService;
    @Autowired
    private UserHelper userHelper;

    @ResponseResult
    @GetMapping("collectPageList")
    @ApiOperation(value = "需求汇总页面数据列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
            @ApiImplicitParam(name = "demandId", value = "需求Id 多选以,隔开", paramType = "query",required = true, dataType="string"),
            @ApiImplicitParam(name = "deptId", value = "部门编码", paramType = "query", dataType="string"),
            @ApiImplicitParam(name = "feedbackUserId", value = "反馈人", paramType = "query",dataType="int") ,
            @ApiImplicitParam(name = "courseType", value = "课程类别", paramType = "query",dataType="int") ,
            @ApiImplicitParam(name = "trainName", value = "培训名称", paramType = "query",dataType="string") ,
            @ApiImplicitParam(name = "trainShape", value = "培训形式", paramType = "query",dataType="int")
    })
    public PageInfo<TrainDemandFeedbackDetailVO> collectPageList(@ApiIgnore @RequestParam Map<String, Object> params){
        return trainDemandFeedbackDetailService.pageList(params);
    }

    @ResponseResult
    @PostMapping("collectSave")
    @ApiOperation(value = "下发人新增反馈")
    public Long save(@RequestBody @Validated(TrainDemandFeedbackDetailDTO.Insert.class) TrainDemandFeedbackDetailDTO dto) {
        return trainDemandFeedbackDetailService.save(dto,userHelper.getLoginEmplId());
    }

    @ResponseResult
    @GetMapping("find")
    @ApiOperation(value = "反馈详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "反馈详情ID", paramType = "path", required = true, dataType="int") ,
    })
    public TrainDemandFeedbackDetailVO find(@ApiIgnore @RequestParam Long id) {
        return trainDemandFeedbackDetailService.selectByDetailId(id);
    }

    @ResponseResult
    @PutMapping("update")
    @ApiOperation(value = "更新反馈")
    public Integer update(@RequestBody @Validated(TrainDemandFeedbackDetailDTO.Update.class) TrainDemandFeedbackDetailDTO dto) {
        return trainDemandFeedbackDetailService.update(dto,userHelper.getLoginEmplId());
    }

    @ResponseResult
    @DeleteMapping("delete")
    @ApiOperation(value = "反馈删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "反馈详情ID", paramType = "path", required = true, dataType="int") ,
    })
    public Integer delete(@ApiIgnore @RequestParam Long id) {
        return trainDemandFeedbackDetailService.deleteByDetailId(id);
    }

    @ResponseResult
    @GetMapping("feedbackPageList")
    @ApiOperation(value = "需求已反馈/待反馈页面数据列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
            @ApiImplicitParam(name = "demandId", value = "需求Id", paramType = "query",required = true, dataType="string"),
    })
    public PageInfo<TrainDemandFeedbackDetailVO> feedbackPageList(@ApiIgnore @RequestParam Map<String, Object> params){
        params.put("feedbackUserId",userHelper.getLoginEmplId());
        return trainDemandFeedbackDetailService.pageList(params);
    }

    @ResponseResult
    @PostMapping("feedbackUserSave")
    @ApiOperation(value = "反馈人新增反馈")
    public Boolean feedbackUserSave(@RequestBody @Validated(TrainDemandFeedbackDetailDTO.Insert.class) List<TrainDemandFeedbackDetailDTO> dtoList) {
        return trainDemandFeedbackDetailService.feedbackUserSave(dtoList,userHelper.getLoginEmplId());
    }

    /*@PostMapping("feedbackUserSave")
    @ApiOperation(value = "反馈人新增反馈")
    public Result<Boolean> feedbackUserSave1(@RequestBody List<TrainDemandFeedbackDetailDTO> dtoList) {
        for (TrainDemandFeedbackDetailDTO dto : dtoList) {
            ValidatorUtils.validateEntity(dto, TrainDemandFeedbackDetailDTO.Insert.class);
        }
        boolean res = trainDemandFeedbackDetailService.feedbackUserSave(dtoList,userHelper.getLoginEmplId());
        if (res) {
            return new Result<Boolean>().success("保存成功");
        }
        return new Result<Boolean>().error("保存失败");
    }*/

    @GetMapping("/downloadTemplate")
    @ApiOperation(value = "下载需求导入模板")
    public void downloadTemplate(HttpServletResponse response) throws IOException {
        List<DemandFeedbackImportExcel> detailList = new ArrayList<>();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyExcel没有关系
        String fileName = URLEncoder.encode("需求反馈导入表", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ExcelTypeEnum.XLSX.getValue());

        // 列下拉框数据
        Map<Integer, List<String>> selectMap = trainDemandFeedbackDetailService.excelSelectMap();

        // 绕过了创建临时文件，直接将数据读到流中传递至客户端
        EasyExcelFactory.write(response.getOutputStream(), DemandFeedbackImportExcel.class)
                .registerWriteHandler(new SelectSheetWriteHandler(selectMap))
                .excelType(ExcelTypeEnum.XLSX)
                .sheet("需求反馈表")
                .doWrite(detailList);
    }

    @PostMapping(value = "import", headers = "content-type=multipart/form-data")
    @ApiOperation(value = "需求汇总-反馈批量导入")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "demandId", value = "需求ID", paramType = "query", dataType="long", required = true),
            @ApiImplicitParam(name = "file", value = "导入数据excel", dataTypeClass = MultipartFile.class, required = true, allowMultiple = true)
    })
    @Transactional(rollbackFor = Exception.class)
    public Result<List<DemandFeedbackImportExcel>> excelImport(@RequestBody MultipartFile file, Long demandId) throws IOException {
        if (file == null) {
            return new Result<List<DemandFeedbackImportExcel>>().error("请上传要导入的文件");
        }
        //定义最终入库的数据集合
        EasyExcelListener<DemandFeedbackImportExcel> listener = new EasyExcelListener<>();
        List<DemandFeedbackImportExcel> excelList =
                EasyExcelFactory.read(file.getInputStream(), DemandFeedbackImportExcel.class, listener)
                        .sheet(0).doReadSync();
        if (excelList.isEmpty()) {
            return new Result<List<DemandFeedbackImportExcel>>().error("导入文件数据不能为空");
        }
        List<DemandFeedbackImportExcel> errorList = trainDemandFeedbackDetailService.importExl(excelList, demandId, userHelper.getLoginEmplId());
        if (errorList.isEmpty()) {
            return new Result<List<DemandFeedbackImportExcel>>().success("导入成功");
        }
        return new Result<List<DemandFeedbackImportExcel>>().error("数据校验不通过", errorList);
    }

    @PostMapping(value = "feedbackUserImport", headers = "content-type=multipart/form-data")
    @ApiOperation(value = "用户反馈-批量导入")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "demandId", value = "需求ID", paramType = "query", dataType="int", required = true),
            @ApiImplicitParam(name = "file", value = "导入数据excel", dataTypeClass = MultipartFile.class, required = true, allowMultiple = true)
    })
    public Result<TrainDemandFeedbackDetailExcelVO> feedbackUserImport(@RequestBody MultipartFile file, Long demandId) throws IOException {
        //定义最终入库的数据集合
        EasyExcelListener<DemandFeedbackImportExcel> listener = new EasyExcelListener<>();
        List<DemandFeedbackImportExcel> excelList =
                EasyExcelFactory.read(file.getInputStream(), DemandFeedbackImportExcel.class, listener)
                        .sheet(0).doReadSync();
        if (excelList.isEmpty()) {
            return new Result<TrainDemandFeedbackDetailExcelVO>().error("需求反馈数据不能为空");
        }
        TrainDemandFeedbackDetailExcelVO excelVO = trainDemandFeedbackDetailService.checkOwnImportDetail(excelList, demandId, userHelper.getLoginEmplId());
        if (!excelVO.getErrorList().isEmpty()) {
            return new Result<TrainDemandFeedbackDetailExcelVO>().error("数据校验不通过", excelVO);
        }
        return new Result<TrainDemandFeedbackDetailExcelVO>().success("数据校验通过", excelVO);
    }

    @GetMapping("/export")
    @ApiOperation(value = "需求汇总-导出反馈列表")
    public void export(HttpServletResponse response,String demandId) throws IOException {
        List<DemandFeedbackExcel> detailList = trainDemandFeedbackDetailService.exportList(demandId);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyExcel没有关系
        String fileName = URLEncoder.encode("需求汇总导出表", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ExcelTypeEnum.XLSX.getValue());

        // 绕过了创建临时文件，直接将数据读到流中传递至客户端
        EasyExcelFactory.write(response.getOutputStream(), DemandFeedbackExcel.class).sheet("需求汇总").doWrite(detailList);
    }

}