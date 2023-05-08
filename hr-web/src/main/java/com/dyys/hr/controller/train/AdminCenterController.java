package com.dyys.hr.controller.train;

import com.dagongma.kernel.annotation.ResponseResult;
import com.dyys.hr.constants.Constant;
import com.dyys.hr.helper.UserHelper;
import com.dyys.hr.service.staff.IStaffUserInfoService;
import com.dyys.hr.service.train.TrainEmployeeCertificateService;
import com.dyys.hr.service.train.TrainProgramsService;
import com.dyys.hr.service.train.TrainTemporaryDemandService;
import com.dyys.hr.vo.statis.MonthlyVO;
import com.dyys.hr.vo.train.*;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

/**
 * @author wsj
 * @date 2022/6/1310:21
 */
@ResponseResult
@Slf4j
@RestController
@RequestMapping("/train/admin")
@Api(value = "管理员首页接口", tags = {"【培训】【管理员页面】首页接口 - 已完成"})
public class AdminCenterController {
    @Autowired
    private IStaffUserInfoService iStaffUserInfoService;
    @Autowired
    private TrainProgramsService trainProgramsService;
    @Autowired
    private TrainTemporaryDemandService trainTemporaryDemandService;
    @Autowired
    private TrainEmployeeCertificateService trainEmployeeCertificateService;
    @Autowired
    private UserHelper userHelper;

    @GetMapping("relatedData")
    @ApiOperation(value = "工作台数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deptId", value = "部门编码", paramType = "query", required = true, dataType="String"),
            @ApiImplicitParam(name = "year", value = "年度", paramType = "query", required = false, dataType="String"),
    })
    public AdminRelateDataVO relatedData(@ApiIgnore @RequestParam Map<String, Object> params) {
        if (params.containsKey("year") && !StringUtils.isEmpty(params.get("year").toString())) {
            String year = params.get("year").toString();
            params.put("startTime", year + "-01-01");
            params.put("endTime", year + "-12-31");
        }
        return iStaffUserInfoService.adminRelatedData(params);
    }

    @GetMapping("trainingDistributionTrend")
    @ApiOperation(value = "培训项目总数分布趋势")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deptId", value = "部门编码", paramType = "query", required = true, dataType="String"),
            @ApiImplicitParam(name = "year", value = "年度", paramType = "query", required = false, dataType="String"),
    })
    public List<MonthlyVO> trainingDistributionTrend(@ApiIgnore @RequestParam Map<String, Object> params){
        return iStaffUserInfoService.trainMonthlyTrend(params);
    }

    @GetMapping("trainingResponsibleList")
    @ApiOperation(value = "最近负责的项目列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
            @ApiImplicitParam(name = "deptId", value = "部门编码", paramType = "query", required = true, dataType="String"),
            @ApiImplicitParam(name = "trainName", value = "项目名称", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "year", value = "年度", paramType = "query", required = false, dataType="String"),
    })
    public PageInfo<AdminResponsibleProgramsVO> trainingResponsibleList(@ApiIgnore @RequestParam Map<String, Object> params){
        params.put("adminId",userHelper.getLoginEmplId());
        if (params.containsKey("year") && !StringUtils.isEmpty(params.get("year").toString())) {
            String year = params.get("year").toString();
            params.put("startTime", year + "-01-01");
            params.put("endTime", year + "-12-31");
        }
        return trainProgramsService.trainingResponsibleList(params);
    }

    @GetMapping("temporaryDemandList")
    @ApiOperation(value = "临时培训需求申请列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
            @ApiImplicitParam(name = "status", value = "状态 1.已审核 0.待审核", paramType = "query",dataType="int"),
            @ApiImplicitParam(name = "year", value = "年度", paramType = "query", required = false, dataType="String"),
    })
    public PageInfo<AdminTemporaryDemandListVO> temporaryDemandList(@ApiIgnore @RequestParam Map<String, Object> params){
        params.put("adminId",userHelper.getLoginEmplId());
        return trainTemporaryDemandService.adminTemporaryDemandList(params);
    }

    @GetMapping("certificateExpirationReminderList")
    @ApiOperation(value = "证书到期提醒列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
            @ApiImplicitParam(name = "deptId", value = "部门编码", paramType = "query", required = true, dataType="String"),
            @ApiImplicitParam(name = "year", value = "年度", paramType = "query", required = false, dataType="String"),
    })
    public PageInfo<AdminCertificateExpirationReminderListVO> certificateExpirationReminderList(@ApiIgnore @RequestParam Map<String, Object> params){
        if (params.containsKey("year") && !StringUtils.isEmpty(params.get("year").toString())) {
            String year = params.get("year").toString();
            params.put("startTime", year + "-01-01");
            params.put("endTime", year + "-12-31");
        }
        return trainEmployeeCertificateService.certificateExpirationReminderList(params);
    }
}
