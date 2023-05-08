package com.dyys.hr.controller.train;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.dagongma.kernel.annotation.ResponseResult;
import com.dagongma.kernel.commons.enums.ResultCode;
import com.dagongma.kernel.commons.exceptions.BusinessException;
import com.dyys.hr.constants.Constant;
import com.dyys.hr.dto.train.TrainEmployeeCertificateDTO;
import com.dyys.hr.entity.train.excel.CertificateExcel;
import com.dyys.hr.entity.train.excel.CertificateListExcel;
import com.dyys.hr.examUtils.EasyExcelListener;
import com.dyys.hr.helper.UserHelper;
import com.dyys.hr.service.train.TrainEmployeeCertificateService;
import com.dyys.hr.utils.Result;
import com.dyys.hr.vo.train.TrainEmployeeCertificatePageVO;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * 员工证书表操作接口
 * @author WSJ
 */
@RestController
@Slf4j
@RequestMapping("/train/employeeCertificate")
@Api(value = "员工证书管理",tags={"【培训】【过程管理】员工证书管理-已完成"})
public class TrainEmployeeCertificateController {
    @Autowired
    private TrainEmployeeCertificateService trainEmployeeCertificateService;

    @Autowired
    private UserHelper userHelper;

    @ResponseResult
    @GetMapping("pageList")
    @ApiOperation(value = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
            @ApiImplicitParam(name = "emplId", value = "员工工号", paramType = "query", dataType="String") ,
            @ApiImplicitParam(name = "emplName", value = "员工姓名", paramType = "query", dataType="String") ,
            @ApiImplicitParam(name = "deptId", value = "部门编码", paramType = "query", dataType="String") ,
            @ApiImplicitParam(name = "certificateName", value = "证书名称", paramType = "query", dataType="String") ,
            @ApiImplicitParam(name = "issuingAgencyName", value = "发证机构", paramType = "query", dataType="String") ,
    })
    public PageInfo<TrainEmployeeCertificatePageVO> pageList(@ApiIgnore @RequestParam Map<String, Object> params){
        params.put("deptList",userHelper.getLoginDeptList());
        return trainEmployeeCertificateService.pageList(params);
    }

    @ResponseResult
    @PostMapping("save")
    @ApiOperation(value = "创建证书")
    public Long save(@RequestBody @Validated(TrainEmployeeCertificateDTO.Insert.class) TrainEmployeeCertificateDTO dto) {
        String resultMsg = trainEmployeeCertificateService.checkUniqueData(dto);
        if (!resultMsg.equals("")) {
            throw new BusinessException(ResultCode.ERROR, resultMsg);
        }
        return trainEmployeeCertificateService.save(dto,userHelper.getLoginEmplId());
    }

    @ResponseResult
    @PostMapping("edit")
    @ApiOperation(value = "编辑证书")
    public Integer edit(@RequestBody @Validated(TrainEmployeeCertificateDTO.Update.class) TrainEmployeeCertificateDTO dto) {
        String resultMsg = trainEmployeeCertificateService.checkUniqueData(dto);
        if (!resultMsg.equals("")) {
            throw new BusinessException(ResultCode.ERROR, resultMsg);
        }
        return trainEmployeeCertificateService.edit(dto,userHelper.getLoginEmplId());
    }

    @ResponseResult
    @DeleteMapping("delete")
    @ApiOperation(value = "删除证书")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "证书id", paramType = "path", required = true, dataType="int") ,
    })
    public Integer delete(@ApiIgnore @RequestParam Long id) {
        return trainEmployeeCertificateService.deleteById(id);
    }


    @GetMapping("/exportTml")
    @ApiOperation(value = "下载证书导入模板")
    public void exportTml(HttpServletResponse response) throws IOException {
        //获取对应答题记录列表
        List<CertificateExcel> excelList = new ArrayList<>();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("证书导入模板", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ExcelTypeEnum.XLSX.getValue());

        // 绕过了创建临时文件，直接将数据读到流中传递至客户端
        EasyExcelFactory.write(response.getOutputStream(), CertificateExcel.class)
                .sheet("证书数据")
                .doWrite(excelList);
    }

    @PostMapping(value = "/importCertificate", headers = "content-type=multipart/form-data")
    @ApiOperation(value = "导入证书")
    @Transactional(rollbackFor = Exception.class)
    public Result<List<CertificateExcel>> importCertificate(@RequestBody MultipartFile file) throws IOException {
        if (file == null) {
            return new Result<List<CertificateExcel>>().error("请上传证书数据文件");
        }
        //定义最终入库的数据集合
        EasyExcelListener<CertificateExcel> listener = new EasyExcelListener<>();
        List<CertificateExcel> excelList =
                EasyExcelFactory.read(file.getInputStream(), CertificateExcel.class, listener)
                        .sheet(0).doReadSync();
        if (excelList.isEmpty()) {
            return new Result<List<CertificateExcel>>().error("证书数据不能为空");
        }
        List<CertificateExcel> errorList = trainEmployeeCertificateService.importCertificate(excelList, userHelper.getLoginEmplId());
        if (errorList.isEmpty()) {
            return new Result<List<CertificateExcel>>().success("证书导入成功");
        }
        return new Result<List<CertificateExcel>>().error("数据校验不通过", errorList);
    }

    @GetMapping("/exportCertificateData")
    @ApiOperation(value = "导出证书列表数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "emplId", value = "员工工号", paramType = "query", dataType="String") ,
            @ApiImplicitParam(name = "emplName", value = "员工姓名", paramType = "query", dataType="String") ,
            @ApiImplicitParam(name = "companyDescr", value = "公司名称", paramType = "query", dataType="String") ,
            @ApiImplicitParam(name = "certificateName", value = "证书名称", paramType = "query", dataType="String") ,
            @ApiImplicitParam(name = "issuingAgencyName", value = "发证机构", paramType = "query", dataType="String") ,
    })
    public void exportCertificateData(HttpServletResponse response,@ApiIgnore @RequestParam Map<String, Object> params) throws IOException {
        // 获取证书列表
        List<CertificateListExcel> excelList = trainEmployeeCertificateService.exportList(params);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("证书列表数据", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ExcelTypeEnum.XLSX.getValue());

        // 绕过了创建临时文件，直接将数据读到流中传递至客户端
        EasyExcelFactory.write(response.getOutputStream(), CertificateListExcel.class).sheet("证书列表").doWrite(excelList);
    }
}