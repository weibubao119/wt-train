package com.dyys.hr.controller.train;

import com.dagongma.kernel.annotation.ResponseResult;
import com.dyys.hr.constants.Constant;
import com.dyys.hr.dto.train.TrainCostDTO;
import com.dyys.hr.helper.UserHelper;
import com.dyys.hr.service.train.TrainCostService;
import com.dyys.hr.vo.train.TrainCostVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * 培训管理费用接口
 *
 * @author WSJ
 * @date 2022/05/22
 */
@ResponseResult
@Slf4j
@RestController
@RequestMapping("/train/cost")
@Api(value = "项目管理接口", tags = {"【培训】【过程管理】培训项目-培训费用管理-已完成"})
public class TrainCostController {
    @Autowired
    private TrainCostService trainCostService;
    @Autowired
    private UserHelper userHelper;

    @GetMapping("pageList")
    @ApiOperation(value = "费用列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
            @ApiImplicitParam(name = "programsId", value = "项目id", paramType = "query", required = true,dataType="int"),
    })
    public PageInfo<TrainCostVO> pageList(@ApiIgnore @RequestParam Map<String, Object> params){
        return trainCostService.pageList(params);
    }

    @PostMapping("save")
    @ApiOperation(value = "新增费用")
    public Long save(@RequestBody @Validated(TrainCostDTO.Insert.class) TrainCostDTO dto) {
        return trainCostService.save(dto,userHelper.getLoginEmplId());
    }

    @DeleteMapping("delete")
    @ApiOperation(value = "移除费用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "需求ID", paramType = "path", required = true, dataType="int") ,
    })
    public Integer delete(@ApiIgnore @RequestParam Long id) {
        return trainCostService.deleteByCostId(id);
    }
}