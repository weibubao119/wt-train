package com.dyys.hr.controller.train;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.dagongma.kernel.annotation.ResponseResult;
import com.dyys.hr.constants.Constant;
import com.dyys.hr.dto.train.TrainTemporaryDemandDTO;
import com.dyys.hr.entity.train.excel.ParticipantsExcel;
import com.dyys.hr.examUtils.EasyExcelListener;
import com.dyys.hr.helper.UserHelper;
import com.dyys.hr.service.staff.IStaffJobService;
import com.dyys.hr.service.train.*;
import com.dyys.hr.utils.Result;
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
import java.util.*;

/**
 * 员工临时需求接口
 *
 * @author WSJ
 * @date 2022/05/30
 */
@Slf4j
@RestController
@RequestMapping("/train/temporaryDemand")
@Api(value = "员工页面接口", tags = {"【培训】【员工页面】员工临时需求 - 已完成"})
public class TemporaryDemandController {
    @Autowired
    private TrainTemporaryDemandService trainTemporaryDemandService;
    @Autowired
    private UserHelper userHelper;
    @Autowired
    private IStaffJobService iStaffJobService;
    @Autowired
    private TrainApproveService trainApproveService;

    @ResponseResult
    @GetMapping("pageList")
    @ApiOperation(value = "申请列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
    })
    public PageInfo<TrainTemporaryDemandPageVO> pageList(@ApiIgnore @RequestParam Map<String, Object> params){
        params.put("userId",userHelper.getLoginEmplId());
        return trainTemporaryDemandService.pageList(params);
    }

    @ResponseResult
    @PostMapping("save")
    @ApiOperation(value = "创建需求")
    public Long save(@RequestBody @Validated(TrainTemporaryDemandDTO.Insert.class) TrainTemporaryDemandDTO dto) {
        return trainTemporaryDemandService.save(dto,userHelper.getLoginEmplId());
    }

    @ResponseResult
    @PostMapping("update")
    @ApiOperation(value = "更新需求")
    public int update(@RequestBody @Validated(TrainTemporaryDemandDTO.Update.class) TrainTemporaryDemandDTO dto) {
        return trainTemporaryDemandService.update(dto,userHelper.getLoginEmplId());
    }

    @ResponseResult
    @GetMapping("find")
    @ApiOperation(value = "需求详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "需求ID", paramType = "path", required = true, dataType="int") ,
    })
    public TrainTemporaryDemandDetailVO find(@ApiIgnore @RequestParam Long id) {
        return trainTemporaryDemandService.getDetail(id);
    }

    @GetMapping("exportParticipantsTml")
    @ApiOperation(value = "下载参训人员导入模板")
    public void exportParticipantsTml(HttpServletResponse response) throws IOException {
        List<ParticipantsExcel> excelList = new ArrayList<>();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyExcel没有关系
        String fileName = URLEncoder.encode("参训人员导入模板", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ExcelTypeEnum.XLSX.getValue());

        // 绕过了创建临时文件，直接将数据读到流中传递至客户端
        EasyExcelFactory.write(response.getOutputStream(), ParticipantsExcel.class)
                .sheet("参训人员导入模板")
                .doWrite(excelList);
    }

    @PostMapping(value = "importParticipants", headers = "content-type=multipart/form-data")
    @ApiOperation(value = "参训人员导入")
    @ApiImplicitParams({@ApiImplicitParam(name = "file", value = "参训人员excel", dataTypeClass = MultipartFile.class, required = true, allowMultiple = true)})
    public Result<List<ParticipantsExcel>> excelImport(@RequestBody MultipartFile file) throws IOException {
        if (file == null) {
            return new Result<List<ParticipantsExcel>>().error("请上传要导入的文件");
        }
        EasyExcelListener<ParticipantsExcel> listener = new EasyExcelListener<>();
        List<ParticipantsExcel> excelList =
                EasyExcelFactory.read(file.getInputStream(), ParticipantsExcel.class, listener)
                        .sheet().doReadSync();
        if (excelList.isEmpty()) {
            return new Result<List<ParticipantsExcel>>().error("导入文件数据不能为空");
        }
        Map<String, List<ParticipantsExcel>> res = iStaffJobService.importExl(excelList);
        List<ParticipantsExcel> errorList = res.get("errorList"); // 校验不通过数据
        List<ParticipantsExcel> dataList = res.get("dataList"); // 校验通过数据
        if (errorList.isEmpty()) {
            return new Result<List<ParticipantsExcel>>().success("导入成功", dataList);
        }
        return new Result<List<ParticipantsExcel>>().error("数据校验不通过", errorList);
    }

    @ResponseResult
    @PostMapping("doApprove")
    @ApiOperation(value = "审批需求")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "临时需求ID", paramType = "path", required = true, dataType="int") ,
            @ApiImplicitParam(name = "status", value = "审批状态 2.同意 3.驳回", paramType = "path", required = true, dataType="int") ,
            @ApiImplicitParam(name = "reason", value = "审批意见", paramType = "path", dataType="String") ,
    })
    public Integer doApprove(@ApiIgnore @RequestBody Map<String, Object> params) {
        params.put("type",2);
        params.put("typeId",params.get("id"));
        params.put("approveEmplid",userHelper.getLoginEmplId());
        return trainApproveService.doApprove(params);
    }

}