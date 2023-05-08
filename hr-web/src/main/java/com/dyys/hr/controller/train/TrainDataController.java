package com.dyys.hr.controller.train;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.dagongma.kernel.annotation.ResponseResult;
import com.dyys.hr.constants.Constant;
import com.dyys.hr.dto.train.ELearningDTO;
import com.dyys.hr.entity.train.excel.TrainHistoryDataExcel;
import com.dyys.hr.examUtils.EasyExcelListener;
import com.dyys.hr.helper.UserHelper;
import com.dyys.hr.service.staff.IStaffDepartmentService;
import com.dyys.hr.service.train.TrainDataService;
import com.dyys.hr.utils.Result;
import com.dyys.hr.utils.SelectSheetWriteHandler;
import com.dyys.hr.vo.train.ELearningPageVO;
import com.dyys.hr.vo.train.EmployeeTrainDataVO;
import com.dyys.hr.vo.train.HistoryDataPageVO;
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
 * 培训数据集合表
 *
 * @author WSJ
 * @date 2022/09/01
 */
@Slf4j
@RestController
@RequestMapping("/train/data")
@Api(value = "项目管理接口", tags = {"【培训】【过程管理】培训项目-培训数据集合管理"})
public class TrainDataController {
    @Autowired
    private TrainDataService trainDataService;
    @Autowired
    private UserHelper userHelper;
    @Autowired
    private IStaffDepartmentService staffDepartmentService;

    @ResponseResult
    @GetMapping("eLearningPageList")
    @ApiOperation(value = "e-learning列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
            @ApiImplicitParam(name = "emplName", value = "员工姓名", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "emplId", value = "员工工号", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "deptId", value = "部门编码", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "profession", value = "职族", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "secCode", value = "职序", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "gradeCode", value = "职级", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "courseCategory", value = "课程类别", paramType = "query",dataType="int"),
            @ApiImplicitParam(name = "courseName", value = "课程名称", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "isPass", value = "是否通过 0.否 1.是", paramType = "query",dataType="int")
    })
    public PageInfo<ELearningPageVO> eLearningPageList(@ApiIgnore @RequestParam Map<String, Object> params){
        params.put("deptList",userHelper.getLoginDeptList());
        return trainDataService.eLearningPageList(params);
    }

    @ResponseResult
    @PostMapping("eLearningSave")
    @ApiOperation(value = "e-learning数据同步")
    public Boolean eLearningSave(@RequestBody @Validated(ELearningDTO.Insert.class) List<ELearningDTO> dataList) {
        return trainDataService.eLearningSave(dataList);
    }

    @GetMapping("/downloadHistoryTml")
    @ApiOperation(value = "下载历史数据导入模板")
    public void downloadHistoryTml(HttpServletResponse response) throws IOException {
        List<TrainHistoryDataExcel> excelList = new ArrayList<>();

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyExcel没有关系
        String fileName = URLEncoder.encode("培训历史数据导入模板", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ExcelTypeEnum.XLSX.getValue());

        // 列下拉框数据
        Map<Integer, List<String>> selectMap = trainDataService.excelSelectMap();

        // 绕过了创建临时文件，直接将数据读到流中传递至客户端
        EasyExcelFactory.write(response.getOutputStream(), TrainHistoryDataExcel.class)
                .registerWriteHandler(new SelectSheetWriteHandler(selectMap))
                .excelType(ExcelTypeEnum.XLSX)
                .sheet("历史数据")
                .doWrite(excelList);
    }

    @PostMapping(value = "importHistoryData", headers = "content-type=multipart/form-data")
    @ApiOperation(value = "历史数据导入")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "导入数据excel", dataTypeClass = MultipartFile.class, required = true, allowMultiple = true)
    })
    public Result<List<TrainHistoryDataExcel>> importHistoryData(@RequestBody MultipartFile file) throws IOException {
        if (file == null) {
            return new Result<List<TrainHistoryDataExcel>>().error("请上传培训历史数据文件");
        }
        //定义最终入库的数据集合
        EasyExcelListener<TrainHistoryDataExcel> listener = new EasyExcelListener<>();
        List<TrainHistoryDataExcel> excelList =
                EasyExcelFactory.read(file.getInputStream(), TrainHistoryDataExcel.class, listener)
                        .sheet().doReadSync();
        if (excelList.isEmpty()) {
            return new Result<List<TrainHistoryDataExcel>>().error("培训历史数据不能为空");
        }
        List<TrainHistoryDataExcel> errorList = trainDataService.importExl(excelList, userHelper.getLoginEmplId());
        if (errorList.isEmpty()) {
            return new Result<List<TrainHistoryDataExcel>>().success("培训历史数据导入成功");
        }
        return new Result<List<TrainHistoryDataExcel>>().error("培训历史数据校验不通过", errorList);
    }

    @ResponseResult
    @GetMapping("historyDataPageList")
    @ApiOperation(value = "培训历史数据列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
            @ApiImplicitParam(name = "emplName", value = "员工姓名", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "emplId", value = "员工工号", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "trainName", value = "项目名称", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "trainShape", value = "培训形式 1.内部 2.外部", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "courseName", value = "课程名称", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "courseCategory", value = "课程类别", paramType = "query",dataType="int"),
            @ApiImplicitParam(name = "isObtain", value = "是否取证 0.否 1.是", paramType = "query",dataType="int")
    })
    public PageInfo<HistoryDataPageVO> historyDataPageList(@ApiIgnore @RequestParam Map<String, Object> params){
        params.put("deptList",userHelper.getLoginDeptList());
        return trainDataService.historyDataPageList(params);
    }

    @ResponseResult
    @PostMapping("updateHistoryData")
    @ApiOperation(value = "同步培训历史数据")
    public Boolean updateHistoryData() {
        return trainDataService.updateHistoryData(userHelper.getLoginEmplId());
    }

    @ResponseResult
    @GetMapping("employTrainData")
    @ApiOperation(value = "员工培训数据")
    public EmployeeTrainDataVO employTrainData(@RequestParam String emplId) {
        return trainDataService.employTrainData(emplId);
    }
}