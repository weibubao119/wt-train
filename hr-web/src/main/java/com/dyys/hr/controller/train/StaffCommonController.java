package com.dyys.hr.controller.train;

import com.dagongma.kernel.annotation.ResponseResult;
import com.dyys.hr.constants.Constant;
import com.dyys.hr.helper.UserHelper;
import com.dyys.hr.service.staff.IStaffDepartmentService;
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

import java.util.Map;

/**
 * @author wsj
 * @date 2022/6/1310:21
 */
@ResponseResult
@Slf4j
@RestController
@RequestMapping("/train/staffCommon")
@Api(value = "学员档案接口", tags = {"【培训】【资源管理】基础数据"})
public class StaffCommonController {
    @Autowired
    private IStaffDepartmentService iStaffDepartmentService;
    @Autowired
    private UserHelper userHelper;

    @GetMapping("departmentList")
    @ApiOperation(value = "组织架构列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query", required = true, dataType = "int"),
    })
    public PageInfo<CommonDepartmentVO> departmentList(@ApiIgnore @RequestParam Map<String, Object> params) {
        params.put("deptList",userHelper.getLoginDeptList());
        return iStaffDepartmentService.departmentList(params);
    }
}
