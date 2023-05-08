package com.dyys.hr.controller.common;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.dagongma.kernel.annotation.ResponseResult;
import com.dyys.hr.constants.Constant;
import com.dyys.hr.entity.sys.SysUserToken;
import com.dyys.hr.entity.train.excel.DeptTerseExcel;
import com.dyys.hr.helper.UserHelper;
import com.dyys.hr.service.staff.*;
import com.dyys.hr.vo.common.*;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@ResponseResult
@RequestMapping("common/staff")
@Api(tags="员工相关数据-新版")
public class StaffController {
    @Autowired
    private UserHelper userHelper;
    @Autowired
    private IStaffUserInfoService iStaffUserInfoService;
    @Autowired
    private IStaffMainPostService iStaffMainPostService;
    @Autowired
    private IStaffCompanyService iStaffCompanyService;
    @Autowired
    private IStaffDepartmentService iStaffDepartmentService;
    @Autowired
    private IStaffProfessionService iStaffProfessionService;
    @Autowired
    private IStaffPosnSecService iStaffPosnSecService;
    @Autowired
    private IStaffPosnGradeService iStaffPosnGradeService;
    @Autowired
    private IStaffJobService iStaffJobService;

    @GetMapping("selectList")
    @ApiOperation(value = "员工筛选列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int"),
            @ApiImplicitParam(name = "name", value = "员工名称", paramType = "query", dataType="String") ,
            @ApiImplicitParam(name = "departmentCode", value = "部门编码", paramType = "query", dataType="String") ,
    })
    public PageInfo<Map<String,Object>> selectList(@ApiIgnore @RequestParam Map<String, Object> params){
        return iStaffUserInfoService.selectList(params);
    }

    @GetMapping("authoritySelectList")
    @ApiOperation(value = "带组织权限员工筛选列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int"),
            @ApiImplicitParam(name = "name", value = "员工名称", paramType = "query", dataType="String") ,
            @ApiImplicitParam(name = "departmentCode", value = "部门编码", paramType = "query", dataType="String") ,
    })
    public PageInfo<Map<String,Object>> authSelectList(@ApiIgnore @RequestParam Map<String, Object> params){
        params.put("deptList",userHelper.getLoginDeptList());
        return iStaffUserInfoService.selectList(params);
    }


    @GetMapping("getUserInfo")
    @ApiOperation(value = "获取当前登录用户信息")
    public PsPersionVO userInfo(@ApiIgnore @RequestParam Map<String, Object> params){
        SysUserToken user = userHelper.getLoginUser(); // 用户登录信息
        PsPersionVO persionVO = iStaffUserInfoService.getUserInfoById(user.getUserId());
        List<String> roleList = user.getRoleList();
        if (roleList.isEmpty()) {
            persionVO.setAuthDeptId(persionVO.getDepartmentCode()); // 员工角色取自身所在的组织ID
        } else if (roleList.contains("KN_TRAIN_ADMIN")) {
            persionVO.setAuthDeptId("1000001"); // 超级管理员角色则取最大的组织ID
        } else {
            if (!user.getDeptList().isEmpty()) {
                persionVO.setAuthDeptId(Collections.min(user.getDeptList())); // 分子公司管理员或部门管理员取拥有权限组织编码最靠前的编码
            }
        }
        return persionVO;
    }

    @GetMapping("detailsSelectList")
    @ApiOperation(value = "员工详细信息筛选列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int"),
            @ApiImplicitParam(name = "name", value = "员工名称", paramType = "query", dataType="String") ,
            @ApiImplicitParam(name = "companyCode", value = "公司编码", paramType = "query", dataType="String") ,
            @ApiImplicitParam(name = "departmentCode", value = "部门编码", paramType = "query", dataType="String") ,
    })
    public PageInfo<PsPersionVO> detailsSelectList(@ApiIgnore @RequestParam Map<String, Object> params){
        return iStaffUserInfoService.detailsSelectList(params);
    }

    @GetMapping("getUserInfoById")
    @ApiOperation(value = "通过用户ID获取信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", paramType = "query", required = true,dataType="String") ,
    })
    public PsPersionVO getUserInfoById(@ApiIgnore @RequestParam Map<String, Object> params){
        String id = params.get("id").toString();
        return iStaffUserInfoService.getUserInfoById(id);
    }

    @GetMapping("mainPostSelectList")
    @ApiOperation(value = "标准岗位筛选列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int"),
            @ApiImplicitParam(name = "positionName", value = "岗位名称", paramType = "query", dataType="String") ,
            @ApiImplicitParam(name = "positionCode", value = "岗位编码", paramType = "query", dataType="String") ,
    })
    public PageInfo<Map<String,Object>> mainPostSelectList(@ApiIgnore @RequestParam Map<String, Object> params){
        return iStaffMainPostService.selectList(params);
    }

    @GetMapping("companySelectList")
    @ApiOperation(value = "公司/单位筛选列表")
    @ApiImplicitParams({})
    public List<PsCompanyVO> companySelectList(@ApiIgnore @RequestParam Map<String, Object> params){
        return iStaffCompanyService.selectList(params);
    }

    @GetMapping("authorityCompanySelectList")
    @ApiOperation(value = "带组织权限公司/单位筛选列表")
    @ApiImplicitParams({})
    public List<PsCompanyVO> authorityCompanySelectList(@ApiIgnore @RequestParam Map<String, Object> params){
        //判断是否是管理员，否则进行当前用户公司
        List<String> deptList = userHelper.getLoginDeptList();
        if(deptList != null){
            String companyCode = iStaffJobService.getInfoByEmplId(userHelper.getLoginEmplId()).getCompany();
            params.put("companyCode",companyCode);
        }
        return iStaffCompanyService.selectList(params);
    }


    @GetMapping("departmentSelectList")
    @ApiOperation(value = "部门筛选列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "companyCode", value = "公司编码", paramType = "query", dataType="String") ,
    })
    public List<PsDepartmentVO> departmentSelectList(@ApiIgnore @RequestParam Map<String, Object> params){
        return iStaffDepartmentService.selectList(params);
    }

    @GetMapping("professionSelectList")
    @ApiOperation(value = "职族筛选列表")
    @ApiImplicitParams({})
    public List<PsProfessionVO> professionSelectList(@ApiIgnore @RequestParam Map<String, Object> params){
        return iStaffProfessionService.selectList(params);
    }

    @GetMapping("posSecSelectList")
    @ApiOperation(value = "职序筛选列表")
    @ApiImplicitParams({})
    public List<PsPosnSecVO> posSecSelectList(@ApiIgnore @RequestParam Map<String, Object> params){
        return iStaffPosnSecService.selectList(params);
    }

    @GetMapping("posGradeSelectList")
    @ApiOperation(value = "职级筛选列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "posnGradeType", value = "职级分类编码", paramType = "query", dataType="String") ,
    })
    public List<PsPosnGradeVO> posGradeSelectList(@ApiIgnore @RequestParam Map<String, Object> params){
        return iStaffPosnGradeService.selectList(params);
    }

    @GetMapping("employeeClassList")
    @ApiOperation(value = "员工分类列表")
    @ApiImplicitParams({})
    public List<PsDictVO> employeeClassList(@ApiIgnore @RequestParam Map<String, Object> params){
        params.put("typeName", "C_EMPL_CLASS"); // 员工分类标识
        return iStaffJobService.employeeDictList(params);
    }

    @GetMapping("belongList")
    @ApiOperation(value = "劳务关系列表")
    @ApiImplicitParams({})
    public List<PsDictVO> belongList(@ApiIgnore @RequestParam Map<String, Object> params){
        params.put("typeName", "C_BELONG_TO"); // 劳务关系标识
        return iStaffJobService.employeeDictList(params);
    }

    @GetMapping("eduBackList")
    @ApiOperation(value = "学历列表")
    @ApiImplicitParams({})
    public List<PsDictVO> eduBackList(@ApiIgnore @RequestParam Map<String, Object> params){
        params.put("typeName", "C_EDCT_BAKG"); // 学历标识
        return iStaffJobService.employeeDictList(params);
    }

    @GetMapping("eduLrngStyleList")
    @ApiOperation(value = "教育学制列表")
    @ApiImplicitParams({})
    public List<PsDictVO> eduLrngStyleList(@ApiIgnore @RequestParam Map<String, Object> params){
        params.put("typeName", "C_EDCT_LRNG_STYLE"); // 教育学制标识
        return iStaffJobService.employeeDictList(params);
    }

    @GetMapping("eduSchoolTypeList")
    @ApiOperation(value = "学校类型列表")
    @ApiImplicitParams({})
    public List<PsDictVO> eduSchoolTypeList(@ApiIgnore @RequestParam Map<String, Object> params){
        params.put("typeName", "C_EDCT_SCHL_TYPE"); // 学校类型标识
        return iStaffJobService.employeeDictList(params);
    }

    @GetMapping("posGradeTypeList")
    @ApiOperation(value = "职位层级（职级分类）列表")
    @ApiImplicitParams({})
    public List<PsDictVO> posGradeTypeList(@ApiIgnore @RequestParam Map<String, Object> params){
        params.put("typeName", "C_POSN_GRDE_TYPE"); // 职级分类标识
        return iStaffJobService.employeeDictList(params);
    }

    @GetMapping("departmentCrumb")
    @ApiOperation(value = "部门组织树（通用版）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deptId", value = "部门编码",  paramType = "query", dataTypeClass = String.class) ,
    })
    public List<StaffDepartmentVO> departmentCrumb(@ApiIgnore @RequestParam Map<String, Object> params){
        return iStaffDepartmentService.departmentCrumb(params);
    }

    @GetMapping("deptEmplCrumb")
    @ApiOperation(value = "部门组织树（员工版）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deptId", value = "部门编码",  paramType = "query", dataTypeClass = String.class) ,
    })
    public List<StaffDepartmentVO> deptEmplCrumb(@ApiIgnore @RequestParam Map<String, Object> params){
        SysUserToken user = userHelper.getLoginUser();
        return iStaffDepartmentService.deptEmplCrumb(params, user);
    }

    @GetMapping("deptCompanyEmplList")
    @ApiOperation(value = "公司级部门组织树（员工）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deptId", value = "部门编码",  paramType = "query", dataTypeClass = String.class) ,
    })
    public List<StaffDepartmentVO> deptCompanyEmplList(@ApiIgnore @RequestParam Map<String, Object> params){
        SysUserToken user = userHelper.getLoginUser();
        return iStaffDepartmentService.deptCompanyEmplList(params, user);
    }

    @GetMapping("/deptExport")
    @ApiOperation(value = "组织单位导出")
    public void deptExport(HttpServletResponse response) throws IOException {
        List<DeptTerseExcel> dataList = iStaffDepartmentService.getDeptList();

        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyExcel没有关系
        String fileName = URLEncoder.encode("组织单位编码及名称", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ExcelTypeEnum.XLSX.getValue());

        // 绕过了创建临时文件，直接将数据读到流中传递至客户端
        EasyExcelFactory.write(response.getOutputStream(), DeptTerseExcel.class).sheet("组织单位").doWrite(dataList);
    }
}
