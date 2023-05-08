package com.dyys.hr.controller.train;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.dagongma.kernel.annotation.ResponseResult;
import com.dyys.hr.constants.Constant;
import com.dyys.hr.helper.UserHelper;
import com.dyys.hr.service.train.TrainConstantService;
import com.dyys.hr.service.train.TrainCostService;
import com.dyys.hr.service.train.TrainProgramsService;
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

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author wsj
 * @date 2022/6/1310:21
 */
@ResponseResult
@Slf4j
@RestController
@RequestMapping("/train/report")
@Api(value = "培训报表数据接口", tags = {"【培训】【报表数据接口】"})
public class TrainReportController {
    @Autowired
    private TrainProgramsService trainProgramsService;
    @Autowired
    private TrainConstantService trainConstantService;
    @Autowired
    private TrainCostService trainCostService;
    @Autowired
    private UserHelper userHelper;

    @GetMapping("trainPlanYearImplement")
    @ApiOperation(value = "年度培训计划执行列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
            @ApiImplicitParam(name = "planYear", value = "查询年度", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "companyCode", value = "公司编码", paramType = "query",dataType="String") ,
            @ApiImplicitParam(name = "deptId", value = "部门编码", paramType = "query",dataType="String") ,
            @ApiImplicitParam(name = "status", value = "进度 1.未完成 2.已完成", paramType = "query", dataType="String"),
    })
    public PageInfo<TrainPlanYearImplementVO> trainPlanYearImplementPageList(@ApiIgnore @RequestParam Map<String, Object> params) {
        params.put("deptList",userHelper.getLoginDeptList());
        return trainProgramsService.trainPlanYearImplementPageList(params);
    }

    @GetMapping("studentTrainingDetails")
    @ApiOperation(value = "学员培训明细列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
            @ApiImplicitParam(name = "planYear", value = "查询年度", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "companyCode", value = "公司编码", paramType = "query",dataType="String") ,
            @ApiImplicitParam(name = "deptId", value = "部门编码", paramType = "query",dataType="String") ,
            @ApiImplicitParam(name = "professionCode", value = "职族编码", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "posnGradeCode", value = "职级编码", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "posnSecCode", value = "职序编码", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "emplName", value = "姓名", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "emplId", value = "工号", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "trainName", value = "项目名称", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "courseName", value = "课程名称", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "courseCategory", value = "课程类别", paramType = "query",dataType="int"),
            @ApiImplicitParam(name = "trainShape", value = "培训形式 1.内部 2.外部", paramType = "query",dataType="int"),
            @ApiImplicitParam(name = "learningForm", value = "学习形式", paramType = "query",dataType="int")
    })
    public PageInfo<StudentTrainingDetailsVO> studentTrainingDetails(@ApiIgnore @RequestParam Map<String, Object> params) {
        params.put("deptList",userHelper.getLoginDeptList());
        return trainProgramsService.studentTrainingDetails(params);
    }

    @GetMapping("courseTrainingDetails")
    @ApiOperation(value = "课程培训明细报表列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
            @ApiImplicitParam(name = "planYear", value = "查询年度", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "startMonth", value = "开始月份", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "endMonth", value = "结束月份", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "companyCode", value = "公司编码", paramType = "query",dataType="String") ,
            @ApiImplicitParam(name = "deptId", value = "部门编码", paramType = "query",dataType="String") ,
    })
    public PageInfo<CourseTrainingDetailsVO> courseTrainingDetails(@ApiIgnore @RequestParam Map<String, Object> params) {
        params.put("deptList",userHelper.getLoginDeptList());
        return trainProgramsService.courseTrainingDetails(params);
    }

    @GetMapping("courseTeacherDetails")
    @ApiOperation(value = "讲师课程明细列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
            @ApiImplicitParam(name = "number", value = "讲师工号", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "teacherName", value = "讲师姓名", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "companyCode", value = "公司编码", paramType = "query",dataType="String") ,
            @ApiImplicitParam(name = "deptId", value = "部门编码", paramType = "query",dataType="String") ,
            @ApiImplicitParam(name = "level", value = "讲师等级", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "type", value = "讲师类型 1.内部 2.外部", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "courseName", value = "课程名称", paramType = "query", dataType="String"),
    })
    public PageInfo<CourseTeacherDetailsVO> courseTeacherDetails(@ApiIgnore @RequestParam Map<String, Object> params) {
        params.put("deptList",userHelper.getLoginDeptList());
        return trainProgramsService.courseTeacherDetails(params);
    }

    @GetMapping("taughtCourseTeacherDetails")
    @ApiOperation(value = "讲师已授课程明细列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
            @ApiImplicitParam(name = "companyCode", value = "组织单位编码", paramType = "query",dataType="String") ,
            @ApiImplicitParam(name = "deptId", value = "部门编码", paramType = "query",dataType="String") ,
            @ApiImplicitParam(name = "courseName", value = "课程名称", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "trainName", value = "培训项目名称", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "teacherName", value = "讲师姓名", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "courseCategory", value = "课程类别", paramType = "query",dataType="int"),
            @ApiImplicitParam(name = "trainShape", value = "培训形式 1.内部 2.外部", paramType = "query",dataType="int"),
            @ApiImplicitParam(name = "learningForm", value = "学习形式", paramType = "query",dataType="int")
    })
    public PageInfo<TaughtCourseTeacherDetailsVO> taughtCourseTeacherDetails(@ApiIgnore @RequestParam Map<String, Object> params) {
        params.put("deptList",userHelper.getLoginDeptList());
        return trainProgramsService.taughtCourseTeacherDetails(params);
    }

    @GetMapping("trainCostDetails")
    @ApiOperation(value = "培训项目费用明细")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
            @ApiImplicitParam(name = "planYear", value = "查询年度", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "planName", value = "所属计划", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "companyCode", value = "所在公司编码", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "deptId", value = "部门编码", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "trainName", value = "培训项目名称", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "trainShape", value = "培训形式 1.内部 2.外部", paramType = "query",dataType="int"),
    })
    public PageInfo<TrainCostDetailsVO> trainCostDetails(@ApiIgnore @RequestParam Map<String, Object> params) {
        params.put("deptList",userHelper.getLoginDeptList());
        return trainProgramsService.trainCostDetails(params);
    }

    @GetMapping("studentTrainCostDetails")
    @ApiOperation(value = "学员培训费用明细")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
            @ApiImplicitParam(name = "planYear", value = "查询年度", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "emplName", value = "姓名", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "companyCode", value = "所在公司编码", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "deptId", value = "部门编码", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "trainName", value = "培训项目名称", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "trainShape", value = "培训形式 1.内部 2.外部", paramType = "query",dataType="int"),
    })
    public PageInfo<StudentTrainCostDetailsVO> studentTrainCostDetails(@ApiIgnore @RequestParam Map<String, Object> params) {
        params.put("deptList",userHelper.getLoginDeptList());
        return trainProgramsService.studentTrainCostDetails(params);
    }



    //导出接口
    @GetMapping("trainPlanYearImplementExport")
    @ApiOperation(value = "年度培训计划执行导出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "planYear", value = "查询年度", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "companyCode", value = "公司编码", paramType = "query",dataType="String") ,
            @ApiImplicitParam(name = "deptId", value = "部门编码", paramType = "query",dataType="String") ,
            @ApiImplicitParam(name = "status", value = "进度 1.未完成 2.已完成", paramType = "query", dataType="String"),
    })
    public void trainPlanYearImplementExport(HttpServletResponse response, @ApiIgnore @RequestParam Map<String, Object> params) throws IOException {
        params.put("deptList",userHelper.getLoginDeptList());
        params.put("page",1);
        params.put("limit",10000);
        PageInfo<TrainPlanYearImplementVO> list = trainProgramsService.trainPlanYearImplementPageList(params);
        List<TrainPlanYearImplementVO> detailList = list.getList();

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyExcel没有关系
        String fileName = URLEncoder.encode("年度培训计划执行", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ExcelTypeEnum.XLSX.getValue());

        // 绕过了创建临时文件，直接将数据读到流中传递至客户端
        EasyExcelFactory.write(response.getOutputStream(), TrainPlanYearImplementVO.class).sheet("年度培训计划执行").doWrite(detailList);
    }

    @GetMapping("studentTrainingDetailsExport")
    @ApiOperation(value = "学员培训明细导出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "planYear", value = "查询年度", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "companyCode", value = "公司编码", paramType = "query",dataType="String") ,
            @ApiImplicitParam(name = "deptId", value = "部门编码", paramType = "query",dataType="String") ,
            @ApiImplicitParam(name = "professionCode", value = "职族编码", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "posnGradeCode", value = "职级编码", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "posnSecCode", value = "职序编码", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "emplName", value = "姓名", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "emplId", value = "工号", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "trainName", value = "项目名称", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "courseName", value = "课程名称", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "courseCategory", value = "课程类别", paramType = "query",dataType="int"),
            @ApiImplicitParam(name = "trainShape", value = "培训形式 1.内部 2.外部", paramType = "query",dataType="int"),
            @ApiImplicitParam(name = "learningForm", value = "学习形式", paramType = "query",dataType="int")
    })
    public void studentTrainingDetailsExport(HttpServletResponse response, @ApiIgnore @RequestParam Map<String, Object> params) throws IOException {
        params.put("deptList",userHelper.getLoginDeptList());
        params.put("page",1);
        params.put("limit",10000);
        PageInfo<StudentTrainingDetailsVO> list = trainProgramsService.studentTrainingDetails(params);
        List<StudentTrainingDetailsVO> detailList = list.getList();

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyExcel没有关系
        String fileName = URLEncoder.encode("学员培训明细", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ExcelTypeEnum.XLSX.getValue());

        // 绕过了创建临时文件，直接将数据读到流中传递至客户端
        EasyExcelFactory.write(response.getOutputStream(), StudentTrainingDetailsVO.class).sheet("学员培训明细").doWrite(detailList);
    }

    @GetMapping("courseTrainingDetailsExport")
    @ApiOperation(value = "课程培训明细导出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "planYear", value = "查询年度", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "startMonth", value = "开始月份", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "endMonth", value = "结束月份", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "companyCode", value = "公司编码", paramType = "query",dataType="String") ,
            @ApiImplicitParam(name = "deptId", value = "部门编码", paramType = "query",dataType="String") ,
    })
    public void courseTrainingDetailsExport(HttpServletResponse response, @ApiIgnore @RequestParam Map<String, Object> params) throws IOException {
        params.put("deptList",userHelper.getLoginDeptList());
        params.put("page",1);
        params.put("limit",10000);
        PageInfo<CourseTrainingDetailsVO> list = trainProgramsService.courseTrainingDetails(params);
        List<CourseTrainingDetailsVO> detailList = list.getList();

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyExcel没有关系
        String fileName = URLEncoder.encode("课程培训明细", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ExcelTypeEnum.XLSX.getValue());

        // 绕过了创建临时文件，直接将数据读到流中传递至客户端
        EasyExcelFactory.write(response.getOutputStream(), CourseTrainingDetailsVO.class).sheet("课程培训明细").doWrite(detailList);
    }

    @GetMapping("courseTeacherDetailsExport")
    @ApiOperation(value = "讲师课程明细导出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "number", value = "讲师工号", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "teacherName", value = "讲师姓名", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "companyCode", value = "公司编码", paramType = "query",dataType="String") ,
            @ApiImplicitParam(name = "deptId", value = "部门编码", paramType = "query",dataType="String") ,
            @ApiImplicitParam(name = "level", value = "讲师等级", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "type", value = "讲师类型 1.内部 2.外部", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "courseName", value = "课程名称", paramType = "query", dataType="String"),
    })
    public void courseTeacherDetailsExport(HttpServletResponse response, @ApiIgnore @RequestParam Map<String, Object> params) throws IOException {
        params.put("deptList",userHelper.getLoginDeptList());
        params.put("page",1);
        params.put("limit",10000);
        PageInfo<CourseTeacherDetailsVO> list = trainProgramsService.courseTeacherDetails(params);
        List<CourseTeacherDetailsVO> detailList = list.getList();

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyExcel没有关系
        String fileName = URLEncoder.encode("讲师课程明细", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ExcelTypeEnum.XLSX.getValue());

        // 绕过了创建临时文件，直接将数据读到流中传递至客户端
        EasyExcelFactory.write(response.getOutputStream(), CourseTeacherDetailsVO.class).sheet("讲师课程明细").doWrite(detailList);
    }

    @GetMapping("taughtCourseTeacherDetailsExport")
    @ApiOperation(value = "讲师已授课程明细导出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "companyCode", value = "组织单位编码", paramType = "query",dataType="String") ,
            @ApiImplicitParam(name = "deptId", value = "部门编码", paramType = "query",dataType="String") ,
            @ApiImplicitParam(name = "courseName", value = "课程名称", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "trainName", value = "培训项目名称", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "teacherName", value = "讲师姓名", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "courseCategory", value = "课程类别", paramType = "query",dataType="int"),
            @ApiImplicitParam(name = "trainShape", value = "培训形式 1.内部 2.外部", paramType = "query",dataType="int"),
            @ApiImplicitParam(name = "learningForm", value = "学习形式", paramType = "query",dataType="int")
    })
    public void taughtCourseTeacherDetailsExport(HttpServletResponse response, @ApiIgnore @RequestParam Map<String, Object> params) throws IOException {
        params.put("deptList",userHelper.getLoginDeptList());
        params.put("page",1);
        params.put("limit",10000);
        PageInfo<TaughtCourseTeacherDetailsVO> list = trainProgramsService.taughtCourseTeacherDetails(params);
        List<TaughtCourseTeacherDetailsVO> detailList = list.getList();

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyExcel没有关系
        String fileName = URLEncoder.encode("讲师已授课程明细", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ExcelTypeEnum.XLSX.getValue());

        // 绕过了创建临时文件，直接将数据读到流中传递至客户端
        EasyExcelFactory.write(response.getOutputStream(), TaughtCourseTeacherDetailsVO.class).sheet("讲师已授课程明细").doWrite(detailList);
    }

    @GetMapping("trainCostDetailsExport")
    @ApiOperation(value = "培训项目费用明细导出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "planYear", value = "查询年度", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "planName", value = "所属计划", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "companyCode", value = "所在公司编码", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "deptId", value = "部门编码", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "trainName", value = "培训项目名称", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "trainShape", value = "培训形式 1.内部 2.外部", paramType = "query",dataType="int"),
    })
    public void trainCostDetailsExport(HttpServletResponse response, @ApiIgnore @RequestParam Map<String, Object> params) throws IOException {

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyExcel没有关系
        String fileName = URLEncoder.encode("培训项目费用明细", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ExcelTypeEnum.XLSX.getValue());

        List<List<String>> headList = new ArrayList<>(); // 初始化表头
        // 构建固定表头
        String[] headCost = new String[]{"序号", "年度", "所属计划", "公司名称", "部门名称","培训项目编号","培训名称","培训形式","实际参训人数",
                "培训总费用"};
        for (String head : headCost) {
            List<String> tempHead = new ArrayList<>();
            tempHead.add(head);
            headList.add(tempHead);
        }
        //查询公共科目配置,构建动态表头
        Map<String, Object> map = new HashMap<>();
        map.put("type",8);
        List<TrainConstantVO> subjectsList = trainConstantService.selectList(map);
        for (TrainConstantVO subject : subjectsList){
            List<String> tempHead = new ArrayList<>();
            tempHead.add(subject.getName());
            headList.add(tempHead);
        }
        // 封装数据
        params.put("deptList",userHelper.getLoginDeptList());
        params.put("page",1);
        params.put("limit",10000);
        PageInfo<TrainCostDetailsVO> list = trainProgramsService.trainCostDetails(params);
        List<TrainCostDetailsVO> detailList = list.getList();
        List<List<Object>> dataList = new ArrayList<>(); // 初始化数据
        for (TrainCostDetailsVO detail : detailList) {
            List<Object> dInfo = new ArrayList<>();
            dInfo.add(detail.getId());
            dInfo.add(detail.getPlanYear());
            dInfo.add(detail.getPlanName());
            dInfo.add(detail.getCompanyDescr());
            dInfo.add(detail.getDeptDescr());
            dInfo.add(detail.getTrainNumber());
            dInfo.add(detail.getTrainName());
            dInfo.add(detail.getTrainShape());
            dInfo.add(detail.getActualParticipantsNum());
            dInfo.add(detail.getTotalAmount());
            //构建各培训项目科目费用数据
            for (TrainConstantVO subject : subjectsList) {
                Map<String, Object> query = new HashMap<>();
                query.put("programsId", detail.getProgramsId());
                query.put("subjectsId", subject.getId());
                Float subjectAmount = trainCostService.getSubjectAmount(query);
                dInfo.add(subjectAmount);
            }
            dataList.add(dInfo);
        }

        // 绕过了创建临时文件，直接将数据读到流中传递至客户端
        EasyExcelFactory.write(response.getOutputStream()).head(headList).sheet("培训项目费用明细").doWrite(dataList);
    }

    @GetMapping("studentTrainCostDetailsExport")
    @ApiOperation(value = "学员培训费用明细导出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "planYear", value = "查询年度", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "emplName", value = "姓名", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "companyCode", value = "所在公司编码", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "deptId", value = "部门编码", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "trainName", value = "培训项目名称", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "trainShape", value = "培训形式 1.内部 2.外部", paramType = "query",dataType="int"),
    })
    public void studentTrainCostDetailsExport(HttpServletResponse response, @ApiIgnore @RequestParam Map<String, Object> params) throws IOException {
        params.put("deptList",userHelper.getLoginDeptList());
        params.put("page",1);
        params.put("limit",10000);
        PageInfo<StudentTrainCostDetailsVO> list = trainProgramsService.studentTrainCostDetails(params);
        List<StudentTrainCostDetailsVO> detailList = list.getList();

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyExcel没有关系
        String fileName = URLEncoder.encode("学员培训费用明细", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ExcelTypeEnum.XLSX.getValue());

        // 绕过了创建临时文件，直接将数据读到流中传递至客户端
        EasyExcelFactory.write(response.getOutputStream(), StudentTrainCostDetailsVO.class).sheet("学员培训费用明细").doWrite(detailList);
    }
}
