package com.dyys.hr.controller.train;

import com.dagongma.kernel.annotation.ResponseResult;
import com.dyys.hr.constants.Constant;
import com.dyys.hr.helper.UserHelper;
import com.dyys.hr.service.staff.IStaffJobService;
import com.dyys.hr.service.train.TrainDataService;
import com.dyys.hr.service.train.TrainEmployeeCertificateService;
import com.dyys.hr.vo.train.*;
import com.github.pagehelper.PageInfo;
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
@RequestMapping("/train/studentFiles")
@Api(value = "学员档案接口", tags = {"【培训】【资源管理】学员档案 - 已完成"})
public class StudentFilesController {
    @Autowired
    private IStaffJobService iStaffJobService;
    @Autowired
    private TrainDataService trainDataService;
    @Autowired
    private TrainEmployeeCertificateService trainEmployeeCertificateService;
    @Autowired
    private UserHelper userHelper;

    @GetMapping("studentsList")
    @ApiOperation(value = "学员列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = "emplName", value = "学员姓名", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "emplId", value = "学员工号", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "departmentCode", value = "部门编码", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "professionCode", value = "职族编码", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "posnSecCode", value = "职序编码", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "dictCode", value = "职位层级编码", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "posnGradeCode", value = "职位等级编码", paramType = "query",dataType="String")
    })
    public PageInfo<StudentFilesPageVO> studentsList(@ApiIgnore @RequestParam Map<String, Object> params) {
        params.put("deptList",userHelper.getLoginDeptList());
        return iStaffJobService.studentsList(params);
    }

    @GetMapping("emplInfo")
    @ApiOperation(value = "学员档案")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "emplId", value = "员工编码", paramType = "query", required = true, dataTypeClass = String.class),
    })
    public EmplInfoVO emplInfo(@ApiIgnore @RequestParam Map<String, Object> params) {
        return iStaffJobService.emplInfo(params);
    }

    @GetMapping("trainList")
    @ApiOperation(value = "培训记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "emplId", value = "员工编码", paramType = "query", required = true, dataTypeClass = String.class),
    })
    public List<EmplTrainListVO> trainList(@ApiIgnore @RequestParam Map<String, Object> params) {
        return trainDataService.trainList(params);
    }

    @GetMapping("eLearningList")
    @ApiOperation(value = "E-learning学习记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "emplId", value = "员工编码", paramType = "query", required = true, dataTypeClass = String.class),
    })
    public List<EmplELearningListVO> eLearningList(@ApiIgnore @RequestParam Map<String, Object> params) {
        return trainDataService.emplELearningList(params);
    }

    @GetMapping("certificateList")
    @ApiOperation(value = "获证记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "emplId", value = "员工编码", paramType = "query", required = true, dataTypeClass = String.class),
    })
    public List<EmplCertificateListVO> certificateList(@ApiIgnore @RequestParam Map<String, Object> params) {
        return trainEmployeeCertificateService.emplCertificateList(params);
    }
}
