package com.dyys.hr.controller.train;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.dagongma.kernel.annotation.ResponseResult;
import com.dyys.hr.constants.Constant;
import com.dyys.hr.dto.train.TrainAdminSummaryDTO;
import com.dyys.hr.entity.train.excel.SummaryExcel;
import com.dyys.hr.examUtils.EasyExcelListener;
import com.dyys.hr.helper.UserHelper;
import com.dyys.hr.service.train.TrainAdminSummaryService;
import com.dyys.hr.service.train.TrainProgramsParticipantsService;
import com.dyys.hr.service.train.TrainTraineeSummaryService;
import com.dyys.hr.utils.Result;
import com.dyys.hr.utils.SelectSheetWriteHandler;
import com.dyys.hr.vo.train.TrainAdminSummaryDetailVO;
import com.dyys.hr.vo.train.TrainTraineeSummaryVO;
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
import java.util.List;
import java.util.Map;

/**
 * 培训管理学员管理员总结接口
 *
 * @author WSJ
 * @date 2022/05/22
 */
@Slf4j
@RestController
@RequestMapping("/train/summary")
@Api(value = "项目管理接口", tags = {"【培训】【过程管理】培训项目-培训总结管理-已完成"})
public class TrainSummaryController {
    @Autowired
    private TrainTraineeSummaryService trainTraineeSummaryService;
    @Autowired
    private TrainAdminSummaryService trainAdminSummaryService;
    @Autowired
    private TrainProgramsParticipantsService trainProgramsParticipantsService;
    @Autowired
    private UserHelper userHelper;

    @ResponseResult
    @GetMapping("traineePageList")
    @ApiOperation(value = "学员总结列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
            @ApiImplicitParam(name = "programsId", value = "项目id", paramType = "query", required = true,dataType="int"),
            @ApiImplicitParam(name = "emplName", value = "姓名", paramType = "query",dataType="string"),
            @ApiImplicitParam(name = "companyCode", value = "公司编码", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "departmentCode", value = "部门编码", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "posnGradeCode", value = "职级编码", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "posnSecCode", value = "职序编码", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "isObtain", value = "是否获证 1.是 0.否", paramType = "query",dataType="int"),

    })
    public PageInfo<TrainTraineeSummaryVO> traineePageList(@ApiIgnore @RequestParam Map<String, Object> params){
        return trainTraineeSummaryService.traineePageList(params);
    }

    @ResponseResult
    @GetMapping("getAdminDetail")
    @ApiOperation(value = "管理员培训总结详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "programsId", value = "项目id", paramType = "query", required = true,dataType="int"),
    })
    public TrainAdminSummaryDetailVO getAdminDetail(@ApiIgnore @RequestParam Long programsId) {
        return trainAdminSummaryService.getDetail(programsId);
    }

    @ResponseResult
    @PostMapping("adminUpdate")
    @ApiOperation(value = "管理员培训总结更新")
    public Integer update(@RequestBody @Validated(TrainAdminSummaryDTO.Update.class) TrainAdminSummaryDTO dto) {
        return trainAdminSummaryService.update(dto,userHelper.getLoginEmplId());
    }

    @GetMapping("/exportTml")
    @ApiOperation(value = "下载培训结果导入模板")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "programsId", value = "培训项目id", paramType = "query", dataType="Long", required = true)
    })
    public void exportTml(HttpServletResponse response, @RequestParam Long programsId) throws IOException {
        //获取对应参训人员列表
        List<SummaryExcel> excelList = trainProgramsParticipantsService.getParticipantListTml(programsId);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("培训总结导入模板", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ExcelTypeEnum.XLSX.getValue());
        // 列下拉框数据
        Map<Integer, List<String>> selectMap = trainTraineeSummaryService.excelSelectMap();

        // 绕过了创建临时文件，直接将数据读到流中传递至客户端
        EasyExcelFactory.write(response.getOutputStream(), SummaryExcel.class)
                .registerWriteHandler(new SelectSheetWriteHandler(selectMap))
                .excelType(ExcelTypeEnum.XLSX)
                .sheet("学员培训总结")
                .doWrite(excelList);
    }

    @PostMapping(value = "/importSummary", headers = "content-type=multipart/form-data")
    @ApiOperation(value = "导入培训总结")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "programsId", value = "项目id", paramType = "query", dataType="Long", required = true),
            @ApiImplicitParam(name = "file", value = "导入数据excel", dataTypeClass = MultipartFile.class, required = true, allowMultiple = true)
    })
    @Transactional(rollbackFor = Exception.class)
    public Result<List<SummaryExcel>> importSummary(@RequestBody MultipartFile file, Long programsId) throws IOException {
        if (programsId == null) {
            return new Result<List<SummaryExcel>>().error("缺少项目ID");
        }
        if (file == null) {
            return new Result<List<SummaryExcel>>().error("请上传培训总结数据文件");
        }
        //定义最终入库的数据集合
        EasyExcelListener<SummaryExcel> listener = new EasyExcelListener<>();
        List<SummaryExcel> excelList =
                EasyExcelFactory.read(file.getInputStream(), SummaryExcel.class, listener)
                        .sheet(0).doReadSync();
        if (excelList.isEmpty()) {
            return new Result<List<SummaryExcel>>().error("培训总结数据不能为空");
        }
        List<SummaryExcel> errorList = trainTraineeSummaryService.importExl(excelList, programsId, userHelper.getLoginEmplId());
        if (errorList.isEmpty()) {
            return new Result<List<SummaryExcel>>().success("培训总结导入成功");
        }
        return new Result<List<SummaryExcel>>().error("数据校验不通过", errorList);
    }
}