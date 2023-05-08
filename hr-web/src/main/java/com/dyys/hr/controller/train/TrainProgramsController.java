package com.dyys.hr.controller.train;

import com.dagongma.kernel.annotation.ResponseResult;
import com.dagongma.kernel.commons.enums.ResultCode;
import com.dagongma.kernel.commons.exceptions.BusinessException;
import com.dyys.hr.constants.Constant;
import com.dyys.hr.dto.train.TrainProgramsDTO;
import com.dyys.hr.helper.UserHelper;
import com.dyys.hr.service.train.TrainProgramsService;
import com.dyys.hr.vo.train.TrainProgramsDetailVO;
import com.dyys.hr.vo.train.TrainProgramsVO;
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
 * 培训管理操作接口
 *
 * @author WSJ
 * @date 2022/05/18
 */
@ResponseResult
@Slf4j
@RestController
@RequestMapping("/train/programs")
@Api(value = "项目管理接口", tags = {"【培训】【过程管理】培训项目管理-已完成"})
public class TrainProgramsController {
    @Autowired
    private TrainProgramsService trainProgramsService;
    @Autowired
    private UserHelper userHelper;

    @GetMapping("pageList")
    @ApiOperation(value = "项目列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
            @ApiImplicitParam(name = "trainName", value = "项目名称", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "status", value = "项目状态：1.进行中 2已完成", paramType = "query",dataType="int"),
            @ApiImplicitParam(name = "deptId", value = "部门编码", paramType = "query",dataType="String") ,
            @ApiImplicitParam(name = "principal", value = "负责人编码", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "planId", value = "所属计划id", paramType = "query",dataType="int"),
            @ApiImplicitParam(name = "planYear", value = "年度", paramType = "query",dataType="String")
    })
    public PageInfo<TrainProgramsVO> pageList(@ApiIgnore @RequestParam Map<String, Object> params){
        params.put("deptList",userHelper.getLoginDeptList());
        return trainProgramsService.pageList(params);
    }

    @PostMapping("save")
    @ApiOperation(value = "创建项目")
    public Long save(@RequestBody @Validated(TrainProgramsDTO.Insert.class) TrainProgramsDTO dto) {
        if(dto.getEndTime().before(dto.getStartTime())){
            throw new BusinessException(ResultCode.EXCEPTION,"培训项目的结束时间不能小于开始时间!");
        }
        return trainProgramsService.save(dto,userHelper.getLoginEmplId());
    }

    @GetMapping("find")
    @ApiOperation(value = "项目详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "项目ID", paramType = "path", required = true, dataType="int") ,
    })
    public TrainProgramsDetailVO find(@ApiIgnore @RequestParam Long id) {
        return trainProgramsService.getDetail(id);
    }


    @PostMapping("update")
    @ApiOperation(value = "项目修改")
    public Integer update(@RequestBody @Validated(TrainProgramsDTO.Update.class) TrainProgramsDTO dto) {
        return trainProgramsService.update(dto,userHelper.getLoginEmplId());
    }

    @PostMapping("close")
    @ApiOperation(value = "结束项目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "项目id", paramType = "path", required = true, dataType="int") ,
    })
    public Integer close(@ApiIgnore @RequestBody Map<String, Integer> params) {
        return trainProgramsService.close(params.get("id").longValue(),userHelper.getLoginEmplId());
    }

    @GetMapping("/planCourseList")
    @ApiOperation(value = "培训计划课程列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int"),
            @ApiImplicitParam(name = "programsId", value = "培训项目id", paramType = "query",required = true, dataType="String") ,
            @ApiImplicitParam(name = "name", value = "课程名称", paramType = "query", dataType="String"),
    })
    public PageInfo<Map<String,Object>> planCourseList(@ApiIgnore @RequestParam Map<String, Object> params){
        return trainProgramsService.planCourseList(params);
    }

    @GetMapping("/courseList")
    @ApiOperation(value = "培训项目课表列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int"),
            @ApiImplicitParam(name = "programsId", value = "培训项目id", paramType = "query", required = true, dataType="String"),
            @ApiImplicitParam(name = "schooltime", value = "上课时间", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "name", value = "课程名称", paramType = "query", dataType="String"),
    })
    public PageInfo<Map<String,Object>> courseList(@ApiIgnore @RequestParam Map<String, Object> params){
        return trainProgramsService.courseList(params);
    }

}