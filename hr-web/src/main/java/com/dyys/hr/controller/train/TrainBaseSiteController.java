package com.dyys.hr.controller.train;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.dagongma.kernel.annotation.ResponseResult;
import com.dagongma.kernel.commons.enums.ResultCode;
import com.dagongma.kernel.commons.exceptions.BusinessException;
import com.dyys.hr.constants.Constant;
import com.dyys.hr.dto.train.TrainBaseSiteDTO;
import com.dyys.hr.entity.train.TrainBaseSite;
import com.dyys.hr.entity.train.excel.TrainSiteExcel;
import com.dyys.hr.examUtils.EasyExcelListener;
import com.dyys.hr.helper.UserHelper;
import com.dyys.hr.service.train.TrainBaseSiteService;
import com.dyys.hr.utils.Result;
import com.dyys.hr.vo.train.TrainBaseSiteUsageVO;
import com.dyys.hr.vo.train.TrainBaseSiteVO;
import com.dyys.hr.vo.train.TrainSiteLeaseInfoVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
 *
 * 场地操作接口
 * @author WSJ
 */
@Slf4j
@RestController
@RequestMapping("/train/site")
@Api(value = "场地管理",tags={"【培训】【资源管理】场地管理-已完成"})
public class TrainBaseSiteController {
    @Autowired
    private TrainBaseSiteService trainBaseSiteService;
    @Autowired
    private UserHelper userHelper;

    @ResponseResult
    @GetMapping("pageList")
    @ApiOperation(value = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
            @ApiImplicitParam(name = "siteName", value = "场地名称", paramType = "query", dataType="String") ,
            @ApiImplicitParam(name = "deptId", value = "组织编码", paramType = "query",dataType="String") ,
            @ApiImplicitParam(name = "principal", value = "场地负责人", paramType = "query",dataType="String")
    })
    public PageInfo<TrainBaseSiteVO> pageList(@ApiIgnore @RequestParam Map<String, Object> params){
        return trainBaseSiteService.pageList(params);
    }

    @ResponseResult
    @PostMapping("save")
    @ApiOperation(value = "保存数据")
    public Integer save(@RequestBody @Validated(TrainBaseSiteDTO.Insert.class) TrainBaseSiteDTO trainBaseSiteDTO) {
        // 判断场地名称不能重复
        String resultMsg = trainBaseSiteService.checkData(trainBaseSiteDTO, "add");
        if (!resultMsg.equals("")) {
            throw new BusinessException(ResultCode.ERROR, resultMsg);
        }
        TrainBaseSite trainBaseSite = new TrainBaseSite();
        BeanUtils.copyProperties(trainBaseSiteDTO,trainBaseSite);
        trainBaseSite.setIsImport(0);
        trainBaseSite.setIsDelete(0);
        trainBaseSite.setCreateUser(userHelper.getLoginEmplId());
        trainBaseSite.setCreateTime(System.currentTimeMillis()/1000);
        return trainBaseSiteService.insertSelective(trainBaseSite);
    }

    @ResponseResult
    @PutMapping("update")
    @ApiOperation(value = "更新数据")
    public Integer update(@RequestBody @Validated(TrainBaseSiteDTO.Update.class) TrainBaseSiteDTO trainBaseSiteDTO) {
        // 判断场地名称不能重复
        String resultMsg = trainBaseSiteService.checkData(trainBaseSiteDTO, "update");
        if (!resultMsg.equals("")) {
            throw new BusinessException(ResultCode.ERROR, resultMsg);
        }
        TrainBaseSite trainBaseSite = new TrainBaseSite();
        BeanUtils.copyProperties(trainBaseSiteDTO,trainBaseSite);
        trainBaseSite.setId(trainBaseSiteDTO.getId());
        trainBaseSite.setUpdateUser(userHelper.getLoginEmplId());
        trainBaseSite.setUpdateTime(System.currentTimeMillis()/1000);
        return trainBaseSiteService.updateSelective(trainBaseSite);
    }

    @ResponseResult
    @GetMapping("find")
    @ApiOperation(value = "根据场地ID获取数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "场地ID", paramType = "path", required = true, dataType="int") ,
    })
    public TrainBaseSiteDTO find(@ApiIgnore @RequestParam Integer id) {
        return trainBaseSiteService.selectBySiteId(id);
    }

    @ResponseResult
    @GetMapping("usageList")
    @ApiOperation(value = "场地详情-场地使用情况")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
            @ApiImplicitParam(name = "id", value = "场地ID", paramType = "query", required = true, dataType="int")
    })
    public PageInfo<TrainBaseSiteUsageVO> usageList(@RequestParam @ApiIgnore Map<String, Object> params) {
        return trainBaseSiteService.usageList(params);
    }

    @ResponseResult
    @PutMapping("/lease")
    @ApiOperation(value = "获取场地的租借安排表")
    public List<TrainSiteLeaseInfoVO> leaseInfo(@RequestBody  @RequestParam Map<String, Object> params) {
        return new ArrayList<>();
    }

    @GetMapping("exportTml")
    @ApiOperation(value = "下载培训场地导入模板")
    public void exportTml(HttpServletResponse response) throws IOException {
        // 获取对应导入模板表头
        List<TrainSiteExcel> excelList = new ArrayList<>();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("培训场地导入模板", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ExcelTypeEnum.XLSX.getValue());

        // 绕过了创建临时文件，直接将数据读到流中传递至客户端
        EasyExcelFactory.write(response.getOutputStream(), TrainSiteExcel.class)
                .excelType(ExcelTypeEnum.XLSX)
                .sheet("培训场地")
                .doWrite(excelList);
    }

    @PostMapping(value = "importSite", headers = "content-type=multipart/form-data")
    @ApiOperation(value = "导入培训场地")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "培训场地excel", dataTypeClass = MultipartFile.class, required = true, allowMultiple = true)
    })
    public Result<List<TrainSiteExcel>> importSite(@RequestBody MultipartFile file) throws IOException {
        if (file == null) {
            return new Result<List<TrainSiteExcel>>().error("请上传要导入的文件");
        }
        //定义最终入库的数据集合
        EasyExcelListener<TrainSiteExcel> listener = new EasyExcelListener<>();
        List<TrainSiteExcel> excelList =
                EasyExcelFactory.read(file.getInputStream(), TrainSiteExcel.class, listener)
                        .sheet(0).doReadSync();
        if (excelList.isEmpty()) {
            return new Result<List<TrainSiteExcel>>().error("导入文件场地数据不能为空");
        }
        List<TrainSiteExcel> errorList = trainBaseSiteService.importSite(excelList, userHelper.getLoginEmplId());
        if (errorList.isEmpty()) {
            return new Result<List<TrainSiteExcel>>().success("导入成功");
        }
        return new Result<List<TrainSiteExcel>>().error("场地数据校验不通过", errorList);
    }

    @GetMapping("exportSite")
    @ApiOperation(value = "导出培训场地数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "siteName", value = "场地名称", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "deptId", value = "组织编码", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "principal", value = "场地负责人", paramType = "query",dataType="String")
    })
    public void exportSite(HttpServletResponse response, @RequestParam @ApiIgnore Map<String, Object> params) throws IOException {
        List<TrainSiteExcel> detailList = trainBaseSiteService.exportSite(params);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyExcel没有关系
        String fileName = URLEncoder.encode("培训场地导出表", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

        // 绕过了创建临时文件，直接将数据读到流中传递至客户端
        EasyExcelFactory.write(response.getOutputStream(), TrainSiteExcel.class).sheet("培训场地").doWrite(detailList);
    }
}