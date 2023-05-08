package com.dyys.hr.controller.train;

import cn.hutool.json.JSONUtil;
import com.dagongma.kernel.annotation.ResponseResult;
import com.dagongma.kernel.commons.enums.ResultCode;
import com.dagongma.kernel.commons.exceptions.BusinessException;
import com.dyys.hr.constants.Constant;
import com.dyys.hr.dto.train.FileDTO;
import com.dyys.hr.dto.train.TrainBaseCertificateDTO;
import com.dyys.hr.entity.train.TrainBaseCertificate;
import com.dyys.hr.helper.UserHelper;
import com.dyys.hr.service.train.TrainBaseCertificateService;
import com.dyys.hr.vo.train.TrainBaseCertificateVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

/**
 *
 * 证书表操作接口
 * @author WSJ
 */
@RestController
@ResponseResult
@Slf4j
@RequestMapping("/train/certificate")
@Api(value = "证书管理",tags={"【培训】【资源管理】证书管理-已废除"})
public class TrainBaseCertificateController {
    @Autowired
    private TrainBaseCertificateService trainBaseCertificateService;

    @Autowired
    private UserHelper userHelper;

    @GetMapping("pageList")
    @ApiOperation(value = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
            @ApiImplicitParam(name = "certificateName", value = "场地名称", paramType = "query", dataType="String") ,
            @ApiImplicitParam(name = "issuedInstitutions", value = "机构名称", paramType = "query",dataType="String") ,
    })
    public PageInfo<TrainBaseCertificateVO> pageList(@ApiIgnore @RequestParam Map<String, Object> params){
        return trainBaseCertificateService.pageList(params);
    }

    @PostMapping("save")
    @ApiOperation(value = "保存数据")
    public Integer save(@RequestBody @Validated(TrainBaseCertificateDTO.Insert.class) TrainBaseCertificateDTO dto) {
        //校验证书编号唯一性
        TrainBaseCertificateVO existCate = trainBaseCertificateService.selectByCertificateNo(dto.getCertificateNo());
        if(existCate != null){
            throw new BusinessException(ResultCode.ERROR,"证书号已存在");
        }
        TrainBaseCertificate certificate = new TrainBaseCertificate();
        BeanUtils.copyProperties(dto,certificate);
        List<FileDTO> fileList = dto.getFileList();
        certificate.setFileList(JSONUtil.toJsonStr(fileList));
        certificate.setCreateTime(System.currentTimeMillis()/1000);
        certificate.setCreateUser(userHelper.getLoginEmplId());
        return trainBaseCertificateService.insertSelective(certificate);
    }

    @GetMapping("find")
    @ApiOperation(value = "根据证书ID获取数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "证书ID", paramType = "path", required = true, dataType="int") ,
    })
    public TrainBaseCertificateDTO find(@ApiIgnore @RequestParam Map<String, Object> params) {
        return trainBaseCertificateService.selectByCateId(params);
    }

    @PutMapping("update")
    @ApiOperation(value = "更新数据")
    public Integer update(@RequestBody @Validated(TrainBaseCertificateDTO.Update.class) TrainBaseCertificateDTO dto) {
        //校验证书编号唯一性
        TrainBaseCertificateVO existCate = trainBaseCertificateService.selectByCertificateNo(dto.getCertificateNo());
        if(existCate != null && !(existCate.getId().equals(dto.getId()))){
            throw new BusinessException(ResultCode.ERROR,"证书号已存在");
        }

        TrainBaseCertificate certificate = new TrainBaseCertificate();
        BeanUtils.copyProperties(dto,certificate);
        List<FileDTO> fileList = dto.getFileList();
        certificate.setFileList(JSONUtil.toJsonStr(fileList));
        certificate.setId(dto.getId());
        certificate.setUpdateTime(System.currentTimeMillis()/1000);
        certificate.setUpdateUser(userHelper.getLoginEmplId());
        return trainBaseCertificateService.updateSelective(certificate);
    }

    @GetMapping("getCertificatePageList")
    @ApiOperation(value = "获取证书列表查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
    })
    public PageInfo getCertificatePageList(@ApiIgnore @RequestParam Map<String, Object> params){
        return null;
    }
}