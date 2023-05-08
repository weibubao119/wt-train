package com.dyys.hr.controller.train;

import com.dagongma.kernel.annotation.ResponseResult;
import com.dagongma.kernel.commons.enums.ResultCode;
import com.dagongma.kernel.commons.exceptions.BusinessException;
import com.dyys.hr.constants.Constant;
import com.dyys.hr.dto.train.TrainDemandAddFeedbackDTO;
import com.dyys.hr.dto.train.TrainDemandCollectDTO;
import com.dyys.hr.dto.train.TrainDemandRelateDTO;
import com.dyys.hr.helper.UserHelper;
import com.dyys.hr.service.train.TrainDemandCollectService;
import com.dyys.hr.service.train.TrainDemandFeedbackDetailService;
import com.dyys.hr.service.train.TrainDemandFeedbackService;
import com.dyys.hr.vo.train.*;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

/**
 * 需求管理操作接口
 *
 * @author WSJ
 * @date 2022/04/028
 */
@ResponseResult
@Slf4j
@RestController
@RequestMapping("/train/demandCollect")
@Api(value = "需求管理接口", tags = {"【培训】【需求管理】需求收集-已完成"})
public class TrainDemandCollectController {
    @Autowired
    private TrainDemandCollectService trainDemandCollectService;

    @Autowired
    private TrainDemandFeedbackService trainDemandFeedbackService;

    @Autowired
    private TrainDemandFeedbackDetailService trainDemandFeedbackDetailService;

    @Autowired
    private UserHelper userHelper;

    @GetMapping("pageList")
    @ApiOperation(value = "需求列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
            @ApiImplicitParam(name = "demandName", value = "需求标题", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "year", value = "需求年度", paramType = "query",dataType="String") ,
            @ApiImplicitParam(name = "deptId", value = "部门编码", paramType = "query",dataType="String") ,
            @ApiImplicitParam(name = "status", value = "状态", paramType = "query",dataType="int")
    })
    public PageInfo<TrainDemandCollectVO> pageList(@ApiIgnore @RequestParam Map<String, Object> params){
        params.put("deptList",userHelper.getLoginDeptList());
        return trainDemandCollectService.pageList(params);
    }

    @PostMapping("save")
    @ApiOperation(value = "创建需求")
    public Long save(@RequestBody @Validated(TrainDemandCollectDTO.Insert.class) TrainDemandCollectDTO dto) {
        if(dto.getFeedbackEndTime().before(dto.getFeedbackStartTime())){
            throw new BusinessException(ResultCode.EXCEPTION,"反馈结束时间不能小于开始时间!");
        }
        return trainDemandCollectService.save(dto,userHelper.getLoginEmplId());
    }

    @GetMapping("find")
    @ApiOperation(value = "需求详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "需求ID", paramType = "path", required = true, dataType="int") ,
    })
    public TrainDemandCollectDetailVO find(@ApiIgnore @RequestParam Long id) {
        return trainDemandCollectService.selectByDemandId(id);
    }

//    @PostMapping("update")
//    @ApiOperation(value = "更新数据")
    public Integer update(@RequestBody @Validated(TrainDemandCollectDTO.Update.class) TrainDemandCollectDTO dto) {
        return trainDemandCollectService.update(dto);
    }

    @DeleteMapping("delete")
    @ApiOperation(value = "删除需求")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "需求ID", paramType = "path", required = true, dataType="int") ,
    })
    public Integer delete(@ApiIgnore @RequestParam Long id) {
        return trainDemandCollectService.deleteByDemandId(id);
    }

    @PostMapping("closeCollect")
    @ApiOperation(value = "需求收集结束")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "需求id", paramType = "path", required = true, dataType="int") ,
    })
    public Integer closeCollect(@ApiIgnore @RequestBody Map<String, Integer> params) {
        return trainDemandCollectService.closeCollect(params.get("id").longValue());
    }


    @GetMapping("feedbackList")
    @ApiOperation(value = "需求进度列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "demand_id", value = "需求ID", paramType = "path", required = true, dataType="int") ,
    })
    public List<TrainDemandFeedbackVO> feedbackList(@ApiIgnore @RequestParam Map<String, Object> params){
        params.put("feedback_user_id",userHelper.getLoginEmplId());
        return trainDemandFeedbackService.feedbackList(params);
    }

    @PostMapping("addFeedBackUser")
    @ApiOperation(value = "补充下发(新增反馈人)")
    public Boolean addFeedBackUser(@RequestBody @Validated(TrainDemandAddFeedbackDTO.Insert.class) List<TrainDemandAddFeedbackDTO> dtoList) {
        return trainDemandFeedbackService.addFeedBackUser(dtoList,userHelper.getLoginEmplId());
    }

    @PostMapping("operateFeedBackData")
    @ApiOperation(value = "反馈操作")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "操作类型 1.取消反馈 2.退回反馈", paramType = "path", required = true, dataType="int") ,
            @ApiImplicitParam(name = "id", value = "反馈人记录id", paramType = "path", required = true, dataType="int") ,
            @ApiImplicitParam(name = "reason", value = "操作原因", paramType = "path",  dataType="string") ,
    })
    public Integer operateFeedBackData(@ApiIgnore @RequestBody Map<String, Object> params) {
        params.put("operate_user_id",userHelper.getLoginEmplId());
        return trainDemandFeedbackService.operateFeedBackData(params);
    }

    @GetMapping("userFeedBackPageList")
    @ApiOperation(value = "用户个人需求反馈任务列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
            @ApiImplicitParam(name = "status", value = "反馈状态 1.已反馈 0.待反馈", paramType = "path", required = true, dataType="int") ,
    })
    public PageInfo<TrainDemandUserFeedbackVO> userFeedBackPageList(@ApiIgnore @RequestParam Map<String, Object> params){
        params.put("feedback_user_id",userHelper.getLoginEmplId());
        return trainDemandFeedbackService.userFeedBackPageList(params);
    }

    @GetMapping("relateDemandList")
    @ApiOperation(value = "关联子需求列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
            @ApiImplicitParam(name = "demandName", value = "需求标题", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "companyCode", value = "公司编码", paramType = "query",dataType="String")
    })
    public PageInfo<TrainRelateDemandCollectVO> relateDemandList(@ApiIgnore @RequestParam Map<String, Object> params){
        params.put("initiator",userHelper.getLoginEmplId());
        return trainDemandCollectService.relateDemandList(params);
    }


    @PostMapping("relateDemand")
    @ApiOperation(value = "需求汇总-关联子需求")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "demandName", value = "需求标题", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "companyCode", value = "公司编码", paramType = "query",dataType="String") ,
            @ApiImplicitParam(name = "initiator", value = "发起人ID", paramType = "query",dataType="String")
    })
    public Boolean relateDemand(@RequestBody @Validated(TrainDemandRelateDTO.Insert.class) List<TrainDemandRelateDTO> dtoList){
        return trainDemandFeedbackDetailService.relateDemand(dtoList,userHelper.getLoginEmplId());
    }

}