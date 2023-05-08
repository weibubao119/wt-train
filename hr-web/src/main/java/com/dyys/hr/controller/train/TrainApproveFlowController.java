package com.dyys.hr.controller.train;

import com.dagongma.kernel.annotation.ResponseResult;
import com.dagongma.kernel.commons.enums.ResultCode;
import com.dagongma.kernel.commons.exceptions.BusinessException;
import com.dyys.hr.constants.Constant;
import com.dyys.hr.dto.train.TrainApproveFlowDTO;
import com.dyys.hr.entity.train.TrainApproveFlow;
import com.dyys.hr.helper.UserHelper;
import com.dyys.hr.service.staff.IStaffJobService;
import com.dyys.hr.service.train.TrainApproveFlowService;
import com.dyys.hr.vo.train.TrainApproveFlowPageVO;
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
 * 培训审批流表接口
 *
 * @author WSJ
 * @date 2022/06/17
 */
@ResponseResult
@Slf4j
@RestController
@RequestMapping("/train/approveFlow")
@Api(value = "培训审批流接口", tags = {"【培训】【系统配置】审批设置-已完成"})
public class TrainApproveFlowController {
    @Autowired
    private TrainApproveFlowService trainApproveFlowService;
    @Autowired
    private IStaffJobService iStaffJobService;
    @Autowired
    private UserHelper userHelper;

    @GetMapping("pageList")
    @ApiOperation(value = "分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
            @ApiImplicitParam(name = "name", value = "审批流名称", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "deptId", value = "组织(部门)编码", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "status", value = "状态 1.生效 2.失效", paramType = "query",dataType="int"),
    })
    public PageInfo<TrainApproveFlowPageVO> pageList(@ApiIgnore @RequestParam Map<String, Object> params){
        params.put("deptList",userHelper.getLoginDeptList());
        String company = iStaffJobService.getInfoByEmplId(userHelper.getLoginEmplId()).getCompany();
        params.put("company",company);
        return trainApproveFlowService.pageList(params);
    }

    @PostMapping("save")
    @ApiOperation(value = "新增")
    public Integer save(@RequestBody @Validated(TrainApproveFlowDTO.Insert.class) TrainApproveFlowDTO dto) {
        //校验审批流名称重复
        TrainApproveFlow flow = new TrainApproveFlow();
        flow.setName(dto.getName());
        flow.setDeptId(dto.getDeptId());
        TrainApproveFlow selectOne = trainApproveFlowService.selectOne(flow);
        if(selectOne != null){
            throw new BusinessException(ResultCode.EXCEPTION,"同组织下相同名称审批流已经存在,不能重复添加");
        }
        return trainApproveFlowService.save(dto,userHelper.getLoginEmplId());
    }

    @ResponseResult
    @GetMapping("find")
    @ApiOperation(value = "详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "审批流ID", paramType = "path", required = true, dataType="int") ,
    })
    public TrainApproveFlowDTO find(@ApiIgnore @RequestParam Integer id) {
        return trainApproveFlowService.getDetail(id);
    }


    @PostMapping("update")
    @ApiOperation(value = "编辑")
    public Integer update(@RequestBody @Validated(TrainApproveFlowDTO.Update.class) TrainApproveFlowDTO dto) {
        TrainApproveFlow flow = trainApproveFlowService.selectById(dto.getId());
        return trainApproveFlowService.update(dto,userHelper.getLoginEmplId());
    }

    @PostMapping("changeStatus")
    @ApiOperation(value = "调整状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "审批流id", paramType = "path", required = true, dataType="int") ,
            @ApiImplicitParam(name = "status", value = "状态值 1.生效 2.失效", paramType = "path", required = true, dataType="int")
    })
    public Integer changeStatus(@ApiIgnore @RequestBody Map<String, Object> params) {
        params.put("userId",userHelper.getLoginEmplId());
        return trainApproveFlowService.changeStatus(params);
    }
}