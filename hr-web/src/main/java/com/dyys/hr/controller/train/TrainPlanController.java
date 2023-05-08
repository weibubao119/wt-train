package com.dyys.hr.controller.train;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.dagongma.kernel.annotation.ResponseResult;
import com.dyys.hr.constants.Constant;
import com.dyys.hr.dto.train.TrainPlanDTO;
import com.dyys.hr.entity.train.excel.PlanDetailExcel;
import com.dyys.hr.entity.train.excel.PlanDetailExportExcel;
import com.dyys.hr.examUtils.EasyExcelListener;
import com.dyys.hr.helper.UserHelper;
import com.dyys.hr.service.train.*;
import com.dyys.hr.utils.ExcelImportUtil;
import com.dyys.hr.utils.Result;
import com.dyys.hr.utils.SelectSheetWriteHandler;
import com.dyys.hr.vo.train.*;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.*;

/**
 * 计划管理操作接口
 *
 * @author WSJ
 * @date 2022/05/16
 */
@Slf4j
@RestController
@RequestMapping("/train/plan")
@Api(value = "计划管理接口", tags = {"【培训】【计划管理】计划-已完成"})
public class TrainPlanController {
    @Autowired
    private TrainPlanService trainPlanService;
    @Autowired
    private TrainPlanDetailService trainPlanDetailService;
    @Autowired
    private TrainDemandCollectService trainDemandCollectService;
    @Autowired
    private TrainDemandFeedbackDetailService trainDemandFeedbackDetailService;
    @Autowired
    private TrainApproveService trainApproveService;
    @Autowired
    private UserHelper userHelper;

    @ResponseResult
    @GetMapping("pageList")
    @ApiOperation(value = "计划列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
            @ApiImplicitParam(name = "title", value = "计划标题", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "id", value = "计划编号", paramType = "query",dataType="int") ,
            @ApiImplicitParam(name = "deptId", value = "部门编码", paramType = "query",dataType="String") ,
            @ApiImplicitParam(name = "planYear", value = "计划年度", paramType = "query",dataType="String") ,
            @ApiImplicitParam(name = "initiator", value = "发起人工号", paramType = "query",dataType="sting"),
            @ApiImplicitParam(name = "status", value = "状态：0待审批，1已通过，2已驳回 4.暂存", paramType = "query",dataType="int")

    })
    public PageInfo<TrainPlanVO> pageList(@ApiIgnore @RequestParam Map<String, Object> params){
        params.put("deptList",userHelper.getLoginDeptList());
        return trainPlanService.pageList(params);
    }

    @ResponseResult
    @PostMapping("save")
    @ApiOperation(value = "创建计划")
    public Long save(@RequestBody @Validated(TrainPlanDTO.Insert.class) TrainPlanDTO dto) {
        return trainPlanService.save(dto,userHelper.getLoginEmplId());
    }

    @ResponseResult
    @PostMapping("storage")
    @ApiOperation(value = "暂存计划")
    public Long storage(@RequestBody @Validated(TrainPlanDTO.Insert.class) TrainPlanDTO dto) {
        return trainPlanService.storage(dto,userHelper.getLoginEmplId());
    }

    @ResponseResult
    @GetMapping("find")
    @ApiOperation(value = "计划详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "计划ID", paramType = "path", required = true, dataType="int") ,
    })
    public TrainPlanFormVO find(@ApiIgnore @RequestParam Long id) {
        return trainPlanService.getDetail(id);
    }

    @ResponseResult
    @PostMapping("update")
    @ApiOperation(value = "更新计划")
    public Integer update(@RequestBody @Validated(TrainPlanDTO.Update.class) TrainPlanDTO dto) {
        return trainPlanService.update(dto,userHelper.getLoginEmplId());
    }

    @ResponseResult
    @GetMapping("relateDemandList")
    @ApiOperation(value = "关联需求列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
            @ApiImplicitParam(name = "demandName", value = "需求标题", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "companyCode", value = "公司编码", paramType = "query",dataType="String") ,
            @ApiImplicitParam(name = "initiator", value = "发起人姓名", paramType = "query",dataType="String")
    })
    public PageInfo<TrainRelateDemandCollectVO> relateDemandList(@ApiIgnore @RequestParam Map<String, Object> params){
        return trainDemandCollectService.relateDemandList(params);
    }

    @ResponseResult
    @PostMapping("getRelateDemandDetailList")
    @ApiOperation(value = "获取关联需求详情数据列表")
    public List<TrainDemandFeedbackDetailVO> getRelateDemandDetailList(@ApiIgnore @RequestBody Integer[] demandIds){
        List<Integer> ids = new ArrayList<>(Arrays.asList(demandIds));
        HashMap<String, Object> params = new HashMap<>();
        params.put("demandIds",ids);
        return trainDemandFeedbackDetailService.getRelateDemandDetailList(params);
    }

    @GetMapping("exportPlanDetailTml")
    @ApiOperation(value = "下载培训计划本地导入模板")
    public void exportPlanDetailTml(HttpServletResponse response) throws IOException {
        List<PlanDetailExcel> excelList = new ArrayList<>();
        // response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.ms-excel");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("培训计划本地导入模板", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ExcelTypeEnum.XLS.getValue());

        // 列下拉框数据
        Map<Integer, List<String>> selectMap = trainPlanDetailService.excelSelectMap();

        // 绕过了创建临时文件，直接将数据读到流中传递至客户端
        EasyExcelFactory.write(response.getOutputStream(), PlanDetailExcel.class)
                .registerWriteHandler(new SelectSheetWriteHandler(selectMap))
                .excelType(ExcelTypeEnum.XLS)
                .sheet("培训计划本地导入模板")
                .doWrite(excelList);
    }

    /*@PostMapping(value = "excelPlanDetail", headers = "content-type=multipart/form-data")
    @ApiOperation(value = "培训计划本地导入")
    @ApiImplicitParams({@ApiImplicitParam(name = "file", value = "培训计划excel", dataTypeClass = MultipartFile.class, required = true, allowMultiple = true)})
    public Result<TrainPlanDetailExcelVO> excelPlanDetail(@RequestBody MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return new Result<TrainPlanDetailExcelVO>().error("上传文件为空，请重新上传！");
        }
        List<PlanDetailExcel> excelList;
        try {
            String fileName = file.getOriginalFilename();
            InputStream inputStream = file.getInputStream();
            Workbook workbook = ExcelImportUtil.chooseWorkbook(fileName, inputStream);
            excelList = ExcelImportUtil.readDateListT(workbook, PlanDetailExcel.class, 2, 0);
        } catch (Exception e) {
            return new Result<TrainPlanDetailExcelVO>().error("文件识别异常：" + e.getMessage());
        }
        if (excelList.isEmpty()) {
            return new Result<TrainPlanDetailExcelVO>().error("培训计划数据不能为空");
        }
        TrainPlanDetailExcelVO excelVO = trainPlanDetailService.handlePlanDetailExcel(excelList);
        if (!excelVO.getErrorList().isEmpty()) {
            return new Result<TrainPlanDetailExcelVO>().error("数据校验不通过", excelVO);
        }
        return new Result<TrainPlanDetailExcelVO>().success("数据校验通过", excelVO);
    }*/

    /*@PostMapping(value = "excelPlanDetail", headers = "content-type=multipart/form-data")
    @ApiOperation(value = "培训计划本地导入")
    @ApiImplicitParams({@ApiImplicitParam(name = "file", value = "培训计划excel", dataTypeClass = MultipartFile.class, required = true, allowMultiple = true)})
    public Result<TrainPlanDetailExcelVO> excelPlanDetail(@RequestBody MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return new Result<TrainPlanDetailExcelVO>().error("上传文件为空，请重新上传！");
        }
        List<PlanDetailExcel> excelList = new ArrayList<>();
        try {
            InputStream inputStream = file.getInputStream();
            ExcelReader excelReader = ExcelUtil.getReader(inputStream);
            Field[] fields = PlanDetailExcel.class.getDeclaredFields();
            for (Field field : fields) {
                String fieldName = field.getName();
                if (!fieldName.equals("errMsg")) {
                    String headName = field.getDeclaredAnnotation(ExcelProperty.class).value()[0];
                    excelReader.addHeaderAlias(headName, fieldName);
                }
            }
            excelList = excelReader.readAll(PlanDetailExcel.class);
        } catch (Exception e) {
            return new Result<TrainPlanDetailExcelVO>().error("文件识别异常：" + e.getMessage());
        }
        if (excelList.isEmpty()) {
            return new Result<TrainPlanDetailExcelVO>().error("培训计划数据不能为空");
        }
        TrainPlanDetailExcelVO excelVO = trainPlanDetailService.handlePlanDetailExcel(excelList);
        if (!excelVO.getErrorList().isEmpty()) {
            return new Result<TrainPlanDetailExcelVO>().error("数据校验不通过", excelVO);
        }
        return new Result<TrainPlanDetailExcelVO>().success("数据校验通过", excelVO);
    }*/

    @PostMapping(value = "excelPlanDetail", headers = "content-type=multipart/form-data")
    @ApiOperation(value = "培训计划本地导入")
    @ApiImplicitParams({@ApiImplicitParam(name = "file", value = "培训计划excel", dataTypeClass = MultipartFile.class, required = true, allowMultiple = true)})
    public Result<TrainPlanDetailExcelVO> excelPlanDetail(@RequestBody MultipartFile file) throws IOException {
        EasyExcelListener<PlanDetailExcel> listener = new EasyExcelListener<>();
        List<PlanDetailExcel> excelList = new ArrayList<>();
        try {
            excelList = EasyExcelFactory.read(file.getInputStream(), PlanDetailExcel.class, listener)
                    .sheet(0)
                    .doReadSync();
        } catch (Exception e) {
            return new Result<TrainPlanDetailExcelVO>().error("文件识别异常：" + e.getMessage());
        }
        if (excelList.isEmpty()) {
            return new Result<TrainPlanDetailExcelVO>().error("培训计划数据不能为空");
        }
        TrainPlanDetailExcelVO excelVO = trainPlanDetailService.handlePlanDetailExcel(excelList);
        if (!excelVO.getErrorList().isEmpty()) {
            return new Result<TrainPlanDetailExcelVO>().error("数据校验不通过", excelVO);
        }
        return new Result<TrainPlanDetailExcelVO>().success("数据校验通过", excelVO);
    }

    @GetMapping("exportPlanDetail")
    @ApiOperation(value = "导出培训计划数据")
    public void exportPlanDetail(HttpServletResponse response, Long planId) throws IOException {
        List<PlanDetailExportExcel> detailList = trainPlanDetailService.exportListByPlanId(planId);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyExcel没有关系
        String fileName = URLEncoder.encode("培训计划导出表", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ExcelTypeEnum.XLSX.getValue());

        // 绕过了创建临时文件，直接将数据读到流中传递至客户端
        EasyExcelFactory.write(response.getOutputStream(), PlanDetailExportExcel.class).sheet("培训计划表").doWrite(detailList);
    }


    @ResponseResult
    @PostMapping("doApprove")
    @ApiOperation(value = "审批计划")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "计划ID", paramType = "path", required = true, dataType="int") ,
            @ApiImplicitParam(name = "status", value = "审批状态 2.同意 3.驳回", paramType = "path", required = true, dataType="int") ,
            @ApiImplicitParam(name = "reason", value = "审批意见", paramType = "path", dataType="String") ,
    })
    public Integer doApprove(@ApiIgnore @RequestBody Map<String, Object> params) {
        params.put("type",1);
        params.put("typeId",params.get("id"));
        params.put("approveEmplid",userHelper.getLoginEmplId());
        return trainApproveService.doApprove(params);
    }
}