package com.dyys.hr.controller.train;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.dagongma.kernel.annotation.ResponseResult;
import com.dagongma.kernel.commons.enums.ResultCode;
import com.dagongma.kernel.commons.exceptions.BusinessException;
import com.dyys.hr.constants.Constant;
import com.dyys.hr.constants.TrainConstantTypeData;
import com.dyys.hr.dto.train.TrainConstantDTO;
import com.dyys.hr.entity.train.excel.ConstantExcel;
import com.dyys.hr.helper.UserHelper;
import com.dyys.hr.service.train.TrainConstantService;
import com.dyys.hr.vo.train.TrainConstantPageVO;
import com.dyys.hr.vo.train.TrainConstantTypeVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 培训常量配置表接口
 *
 * @author WSJ
 * @date 2022/06/17
 */
@ResponseResult
@Slf4j
@RestController
@RequestMapping("/train/constant")
@Api(value = "项目基础档案接口", tags = {"【培训】【系统配置】基础档案-已修改"})
public class TrainConstantController {
    @Autowired
    private TrainConstantService trainConstantService;
    @Autowired
    private UserHelper userHelper;

    @GetMapping("typeSelectList")
    @ApiOperation(value = "档案类型筛选列表")
    public List<TrainConstantTypeVO> typeSelectList(){
        List<TrainConstantTypeVO> typeList = new ArrayList<>();
        Map<Integer, String> map = TrainConstantTypeData.typeMap();
        for (int i = 1; i <= map.size(); i++) {
            TrainConstantTypeVO vo = new TrainConstantTypeVO();
            vo.setType(i);
            vo.setName(map.get(i));
            typeList.add(vo);
        }
        return typeList;
    }

    @GetMapping("pageList")
    @ApiOperation(value = "档案列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
            @ApiImplicitParam(name = "type", value = "类型：1.课程类别，2.培训-需求依据，3.培训-考核方法，4.讲师-等级，5.机构类型，6.计划类型，7.职序与学习方向，8.培训科目，9.机构等级，10.学习方式 11.学历等级", paramType = "query",dataType="int"),
            @ApiImplicitParam(name = "pid", value = "父级ID(例如职序编码)", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "status", value = "状态 1.启用 0.停用", paramType = "query",dataType="int"),
            @ApiImplicitParam(name = "name", value = "名称", paramType = "query",dataType="String")
    })
    public PageInfo<TrainConstantPageVO> pageList(@ApiIgnore @RequestParam Map<String, Object> params){
        return trainConstantService.pageList(params);
    }

    @PostMapping("save")
    @ApiOperation(value = "新增档案")
    public Integer save(@RequestBody @Validated(TrainConstantDTO.Insert.class) TrainConstantDTO dto) {
        //校验档案类型参数
        if(TrainConstantTypeData.typeMap().get(dto.getType()) == null){
            throw new BusinessException(ResultCode.EXCEPTION,"档案类型错误");
        }
        if (dto.getType().equals(7) && StringUtils.isEmpty(dto.getPid())) {
            throw new BusinessException(ResultCode.EXCEPTION,"请选择职序");
        }
        return trainConstantService.save(dto,userHelper.getLoginEmplId());
    }

    @PutMapping("changeStatus")
    @ApiOperation(value = "调整状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "档案id", paramType = "path", required = true, dataType="int") ,
            @ApiImplicitParam(name = "status", value = "状态值", paramType = "path", required = true, dataType="int")
    })
    public Integer changeStatus(@ApiIgnore @RequestBody Map<String, Object> params) {
        params.put("userId",userHelper.getLoginEmplId());
        return trainConstantService.changeStatus(params);
    }

    @DeleteMapping("delete")
    @ApiOperation(value = "删除档案")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "档案id", paramType = "path", required = true, dataType="int") ,
    })
    public Integer delete(@ApiIgnore @RequestParam Integer id) {
        return trainConstantService.delById(id, userHelper.getLoginEmplId());
    }

    @GetMapping("constantExp")
    @ApiOperation(value = "基础设置导出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "类型值：1.课程类别，2.培训-需求依据，3.培训-考核方法，4.讲师-等级，5.机构类型，6.计划类型，7.职序与学习方向，8.培训科目，9.机构等级，10.学习方式", paramType = "query",dataType="int"),
            @ApiImplicitParam(name = "pid", value = "父级ID(例如职序编码)", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "name", value = "名称", paramType = "query",dataType="String")
    })
    public void constantExp(HttpServletResponse response, @ApiIgnore @RequestParam Map<String, Object> params) throws IOException {
        // 获取对应基础设置列表
        List<ConstantExcel> excelList = trainConstantService.constantExp(params);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("基础设置启用数据", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ExcelTypeEnum.XLSX.getValue());

        // 绕过了创建临时文件，直接将数据读到流中传递至客户端
        EasyExcelFactory.write(response.getOutputStream(), ConstantExcel.class)
                .excelType(ExcelTypeEnum.XLSX)
                .sheet("基础设置")
                .doWrite(excelList);
    }
}