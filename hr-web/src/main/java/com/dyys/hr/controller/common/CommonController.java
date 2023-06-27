package com.dyys.hr.controller.common;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.dagongma.kernel.annotation.ResponseResult;
import com.dyys.hr.constants.Constant;
import com.dyys.hr.entity.staff.StaffJob;
import com.dyys.hr.entity.train.excel.ParticipantsExcel;
import com.dyys.hr.examUtils.EasyExcelListener;
import com.dyys.hr.helper.UserHelper;
import com.dyys.hr.service.exam.IExamPaperService;
import com.dyys.hr.service.staff.IStaffJobService;
import com.dyys.hr.service.staff.IStaffUserInfoService;
import com.dyys.hr.service.train.*;
import com.dyys.hr.utils.Result;
import com.dyys.hr.vo.common.RoleDataVO;
import com.dyys.hr.vo.common.RoleMenuVO;
import com.dyys.hr.vo.train.TrainConstantVO;
import com.dyys.hr.vo.train.TrainPlanDetailSelectVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

/**
 * 培训公共配置管理
 *
 * @author sie sie
 * @since 1.0.0 2022-05-05
 */
@RestController
@RequestMapping("common")
@Api(tags="培训公共配置数据")
public class CommonController {
    @Autowired
    private TrainConstantService trainConstantService;
    @Autowired
    private TrainBaseTeacherService trainBaseTeacherService;
    @Autowired
    private TrainBaseSiteService trainBaseSiteService;
    @Autowired
    private TrainBaseCourseService trainBaseCourseService;
    @Autowired
    private TrainInstitutionService trainInstitutionService;
    @Autowired
    private IExamPaperService iExamPaperService;
    @Autowired
    private TrainPlanService trainPlanService;
    @Autowired
    private TrainPlanDetailService trainPlanDetailService;
    @Autowired
    private TrainProgramsPlanService trainProgramsPlanService;
    @Autowired
    private QuestionnaireService questionnaireService;
    @Autowired
    private IStaffUserInfoService iStaffUserInfoService;
    @Autowired
    private TrainBaseCourseTeacherService trainBaseCourseTeacherService;
    @Autowired
    private TrainApproveFlowService trainApproveFlowService;
    @Autowired
    private IStaffJobService iStaffJobService;
    @Autowired
    private UserHelper userHelper;

    @Value("${wtps.auth.driver}")
    private String authDriver;
    @Value("${wtps.auth.url}")
    private String authUrl;
    @Value("${wtps.auth.user}")
    private String authUser;
    @Value("${wtps.auth.password}")
    private String authPassword;

    @ResponseResult
    @ApiOperation("查询菜单")
    @GetMapping(value = "/getMenuList")
    public RoleMenuVO getMenu() {
        Connection con = null;// 数据库连接
        PreparedStatement pre = null;// 预编译语句对象
        ResultSet result = null;// 结果集对象
        List<String> codes = new ArrayList<>();
        List<RoleDataVO> dataList = new ArrayList<>();
        String emplId = userHelper.getLoginEmplId();
        try {
            Class.forName(authDriver);
            String url = authUrl;
            String user = authUser;
            String password = authPassword;
            con = DriverManager.getConnection(url, user, password);

            String sql = "SELECT * FROM role_pagecode_vw where oprid=" + emplId +
                    " " + "and" + " " + "c_code_type='1'";
            pre = con.prepareStatement(sql);// 实例化预编译语句
            result = pre.executeQuery();// 执行查询
            try {
                while (result.next()) {
                    String code = result.getString("ess_sec_code");
                    if (Objects.equals(code, "")) {
                        continue;
                    }
                    if (codes.contains(code)) {
                        continue;
                    }
                    codes.add(code);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 逐一关闭上面的几个对象
                if (result != null)
                    result.close();
                if (pre != null)
                    pre.close();
                if (con != null)
                    con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (String code : codes) {
            RoleDataVO vo = new RoleDataVO();
            vo.setPageCode(code);
            dataList.add(vo);
        }
        //判断用户是否管理员,处理标识
        RoleMenuVO menuVO = new RoleMenuVO();
        menuVO.setIsAdmin(1);
        menuVO.setMenuList(dataList);
        if(dataList.isEmpty()){
            menuVO.setIsAdmin(2);
            List<RoleDataVO> emplCodeList = new ArrayList<>();
            RoleDataVO code1 = new RoleDataVO();
            code1.setPageCode("empCenterManagement");
            emplCodeList.add(code1);
            RoleDataVO code2 = new RoleDataVO();
            code2.setPageCode("myTrainManagement");
            emplCodeList.add(code2);
            RoleDataVO code3 = new RoleDataVO();
            code3.setPageCode("eaxmCenterManagement");
            emplCodeList.add(code3);
            RoleDataVO code4 = new RoleDataVO();
            code4.setPageCode("learArchManagement");
            emplCodeList.add(code4);
            RoleDataVO code5 = new RoleDataVO();
            code5.setPageCode("myApplicationManagement");
            emplCodeList.add(code5);
            RoleDataVO code6 = new RoleDataVO();
            code6.setPageCode("assMentCentManagement");
            emplCodeList.add(code6);
            menuVO.setMenuList(emplCodeList);
        }
        return menuVO;
    }


    @ResponseResult
    @GetMapping("/trainConstant/selectList")
    @ApiOperation(value = "配置类型选择列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "配置类型  1.课程类别 2.培训需求依据 3.培训考核方法 4.讲师等级 5.机构类型 6.计划类型 7.职序与学习方向 8.培训科目 9.机构等级 10.学习形式 11.学历等级", paramType = "query", required = true,dataType="int") ,
            @ApiImplicitParam(name = "pid", value = "上级ID(例如职序编码)", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "name", value = "名称", paramType = "query",dataType="String")
    })
    public List<TrainConstantVO> trainConstantSelectList(@ApiIgnore @RequestParam Map<String, Object> params){
        params.put("status",1);
        return trainConstantService.selectList(params);
    }

    @ResponseResult
    @GetMapping("/trainTeacher/selectList")
    @ApiOperation(value = "讲师列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int"),
            @ApiImplicitParam(name = "name", value = "讲师姓名", paramType = "query", dataType="String") ,
    })
    public PageInfo<Map<String,Object>> teacherSelectList(@ApiIgnore @RequestParam Map<String, Object> params){
        return trainBaseTeacherService.selectList(params);
    }

    @ResponseResult
    @GetMapping("/trainCourseTeacher/selectList")
    @ApiOperation(value = "授课讲师列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int"),
            @ApiImplicitParam(name = "courseId", value = "课程id", paramType = "query", dataType="int"),
            @ApiImplicitParam(name = "name", value = "讲师名称", paramType = "query", dataType="String"),
    })
    public PageInfo<Map<String,Object>> courseTeacherSelectList(@ApiIgnore @RequestParam Map<String, Object> params){
        return trainBaseCourseTeacherService.selectList(params);
    }

    @ResponseResult
    @GetMapping("/trainSite/selectList")
    @ApiOperation(value = "场地列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int"),
            @ApiImplicitParam(name = "name", value = "场地名称", paramType = "query", dataType="String") ,
    })
    public PageInfo<Map<String,Object>> siteSelectList(@ApiIgnore @RequestParam Map<String, Object> params){
        return trainBaseSiteService.selectList(params);
    }

    @ResponseResult
    @GetMapping("/trainCourse/selectList")
    @ApiOperation(value = "课程列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int"),
            @ApiImplicitParam(name = "name", value = "课程名称", paramType = "query", dataType="String") ,
    })
    public PageInfo<Map<String,Object>> courseSelectList(@ApiIgnore @RequestParam Map<String, Object> params){
        return trainBaseCourseService.selectList(params);
    }

    @ResponseResult
    @GetMapping("/trainInstitution/selectList")
    @ApiOperation(value = "机构列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int"),
            @ApiImplicitParam(name = "name", value = "机构名称", paramType = "query", dataType="String") ,
    })
    public PageInfo<Map<String,Object>> institutionSelectList(@ApiIgnore @RequestParam Map<String, Object> params){
        return trainInstitutionService.selectList(params);
    }

    @ResponseResult
    @GetMapping("/examPaper/selectList")
    @ApiOperation(value = "试卷模板列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int"),
            @ApiImplicitParam(name = "name", value = "模板名称", paramType = "query", dataType="String") ,
    })
    public PageInfo<Map<String,Object>> examPaperSelectList(@ApiIgnore @RequestParam Map<String, Object> params){
        return iExamPaperService.selectList(params);
    }

    @ResponseResult
    @GetMapping("/trainPlan/selectList")
    @ApiOperation(value = "培训小计划列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int"),
            @ApiImplicitParam(name = "name", value = "计划名称", paramType = "query", dataType="String") ,
    })
    public PageInfo<TrainPlanDetailSelectVO> trainPlanSelectList(@ApiIgnore @RequestParam Map<String, Object> params){
        return trainPlanDetailService.selectList(params);
    }

    @ResponseResult
    @GetMapping("/trainPlan/planList")
    @ApiOperation(value = "计划筛选列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int"),
            @ApiImplicitParam(name = "name", value = "计划名称", paramType = "query", dataType="String") ,
    })
    public  PageInfo<Map<String,Object>> planList(@ApiIgnore @RequestParam Map<String, Object> params){
        StaffJob staff = iStaffJobService.selectById(userHelper.getLoginEmplId());
        params.put("companyCode", staff.getCompany());
        return trainPlanService.selectList(params);
    }

    @ResponseResult
    @GetMapping("/trainPrograms/planList")
    @ApiOperation(value = "项目计划筛选列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int"),
            @ApiImplicitParam(name = "name", value = "计划名称", paramType = "query", dataType="String") ,
            @ApiImplicitParam(name = "programsId", value = "项目id", paramType = "query", dataType="int")
    })
    public  PageInfo<Map<String,Object>> programsPlanList(@ApiIgnore @RequestParam Map<String, Object> params){
        return trainProgramsPlanService.selectList(params);
    }

    @ResponseResult
    @GetMapping("/questionnaire/selectList")
    @ApiOperation(value = "评估模板列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int"),
            @ApiImplicitParam(name = "name", value = "模板名称", paramType = "query", dataType="String") ,
    })
    public PageInfo<Map<String,Object>> questionnaireSelectList(@ApiIgnore @RequestParam Map<String, Object> params){
        return questionnaireService.selectList(params);
    }

    @ResponseResult
    @GetMapping("/trainApproveFlow/selectList")
    @ApiOperation(value = "审批流列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int"),
            @ApiImplicitParam(name = "name", value = "审批流名称", paramType = "query", dataType="String") ,
    })
    public PageInfo<Map<String,Object>> approveFlowSelectList(@ApiIgnore @RequestParam Map<String, Object> params){
        params.put("deptList",userHelper.getLoginDeptList());
        params.put("emplId", userHelper.getLoginEmplId());
        return trainApproveFlowService.selectList(params);
    }

    @GetMapping("exportParticipantsTml")
    @ApiOperation(value = "下载参加人员导入模板")
    public void exportParticipantsTml(HttpServletResponse response) throws IOException {
        List<ParticipantsExcel> examQuestions = new ArrayList<>();

        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("参加人员导入模板", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ExcelTypeEnum.XLSX.getValue());

        // 绕过了创建临时文件，直接将数据读到流中传递至客户端
        EasyExcelFactory.write(response.getOutputStream(), ParticipantsExcel.class)
                .excelType(ExcelTypeEnum.XLSX)
                .sheet("参加人员")
                .doWrite(examQuestions);
    }

    @PostMapping(value = "importParticipants", headers = "content-type=multipart/form-data")
    @ApiOperation(value = "参加人员导入")
    @ApiImplicitParams({@ApiImplicitParam(name = "file", value = "参加人员excel", dataTypeClass = MultipartFile.class, required = true, allowMultiple = true)})
    public Result<List<ParticipantsExcel>> excelImport(@RequestBody MultipartFile file) throws IOException {
        EasyExcelListener<ParticipantsExcel> listener = new EasyExcelListener<>();

        List<ParticipantsExcel> excelList =
                EasyExcelFactory.read(file.getInputStream(), ParticipantsExcel.class, listener)
                        .sheet(0).doReadSync();

        if (excelList.isEmpty()) {
            return new Result<List<ParticipantsExcel>>().error("参加人员数据不能为空");
        }
        Map<String, List<ParticipantsExcel>> res = iStaffUserInfoService.checkUserData(excelList);
        if (!res.get("errorList").isEmpty()) {
            return new Result<List<ParticipantsExcel>>().error("数据校验不通过", res.get("errorList"));
        }
        return new Result<List<ParticipantsExcel>>().success("数据校验通过", res.get("dataList"));
    }

}