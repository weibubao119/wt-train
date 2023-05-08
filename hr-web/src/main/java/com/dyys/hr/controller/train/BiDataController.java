package com.dyys.hr.controller.train;

import com.dagongma.kernel.annotation.ResponseResult;
import com.dyys.hr.service.staff.IStaffUserInfoService;
import com.dyys.hr.vo.statis.*;
import com.dyys.hr.vo.train.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/train/bi")
@Api(value = "BI数据接口", tags = {"【培训】【BI数据接口】"})
public class BiDataController {
    @Autowired
    private IStaffUserInfoService iStaffUserInfoService;

    @GetMapping("relatedData")
    @ApiOperation(value = "工作台数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deptId", value = "组织部门编码", paramType = "query", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "year", value = "查询年度", paramType = "query", required = true, dataTypeClass = String.class)
    })
    public AdminRelateDataVO relatedData(@ApiIgnore @RequestParam Map<String, Object> params) {
        return iStaffUserInfoService.adminRelatedData(params);
    }

    @GetMapping("trainMonthlyTrend")
    @ApiOperation(value = "培训项目总数分布趋势-图")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deptId", value = "组织部门编码", paramType = "query", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "year", value = "查询年度", paramType = "query", required = true, dataTypeClass = String.class)
    })
    public List<MonthlyVO> trainMonthlyTrend(@ApiIgnore @RequestParam Map<String, Object> params){
        return iStaffUserInfoService.trainMonthlyTrend(params);
    }

    @GetMapping("trainMonthlyTrendList")
    @ApiOperation(value = "培训项目总数分布趋势-表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deptId", value = "组织部门编码", paramType = "query", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "year", value = "查询年度", paramType = "query", required = true, dataTypeClass = String.class)
    })
    public List<MonthlyDeptVO> trainMonthlyTrendList(@ApiIgnore @RequestParam Map<String, Object> params){
        return iStaffUserInfoService.trainMonthlyTrendList(params);
    }

    @GetMapping("courseCateStatis")
    @ApiOperation(value = "已授课程类别分布-图")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deptId", value = "组织部门编码", paramType = "query", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "year", value = "查询年度", paramType = "query", required = true, dataTypeClass = String.class)
    })
    public List<CourseCateVO> courseCateStatis(@ApiIgnore @RequestParam Map<String, Object> params){
        return iStaffUserInfoService.courseCateStatis(params);
    }

    @GetMapping("courseCateDeptStatis")
    @ApiOperation(value = "已授课程类别分布-表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deptId", value = "组织部门编码", paramType = "query", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "year", value = "查询年度", paramType = "query", required = true, dataTypeClass = String.class)
    })
    public List<CourseCateDeptVO> courseCateDeptStatis(@ApiIgnore @RequestParam Map<String, Object> params){
        return iStaffUserInfoService.courseCateDeptStatis(params);
    }

    @GetMapping("costByTrainShape")
    @ApiOperation(value = "培训费用占比(按培训形式)-图")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deptId", value = "组织部门编码", paramType = "query", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "year", value = "查询年度", paramType = "query", required = true, dataTypeClass = String.class)
    })
    public List<TrainShapeCostVO> costByTrainShape(@ApiIgnore @RequestParam Map<String, Object> params){
        return iStaffUserInfoService.costByTrainShape(params);
    }

    @GetMapping("costDeptByTrainShape")
    @ApiOperation(value = "培训费用占比(按培训形式)-表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deptId", value = "组织部门编码", paramType = "query", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "year", value = "查询年度", paramType = "query", required = true, dataTypeClass = String.class)
    })
    public List<TrainShapeCostDeptVO> costDeptByTrainShape(@ApiIgnore @RequestParam Map<String, Object> params){
        return iStaffUserInfoService.costDeptByTrainShape(params);
    }

    @GetMapping("costByPosnGrade")
    @ApiOperation(value = "培训费用占比(按职级)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deptId", value = "组织部门编码", paramType = "query", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "year", value = "查询年度", paramType = "query", required = true, dataTypeClass = String.class)
    })
    public List<PosnGradeCostVO> costByPosnGrade(@ApiIgnore @RequestParam Map<String, Object> params){
        return iStaffUserInfoService.costByPosnGrade(params);
    }

    @GetMapping("trainTypeBiDict")
    @ApiOperation(value = "BI系统培训相关类型字典")
    public List<TrainTypeBiDictVO> trainTypeBiDict() {
        return iStaffUserInfoService.trainTypeBiDictList();
    }
}
