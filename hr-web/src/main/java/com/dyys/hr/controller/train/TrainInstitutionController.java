package com.dyys.hr.controller.train;

import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.dagongma.kernel.annotation.ResponseResult;
import com.dagongma.kernel.commons.enums.ResultCode;
import com.dagongma.kernel.commons.exceptions.BusinessException;
import com.dagongma.kernel.core.entity.PageView;
import com.dyys.hr.constants.Constant;
import com.dyys.hr.dto.train.*;
import com.dyys.hr.entity.train.QuestionnaireUser;
import com.dyys.hr.entity.train.TrainInstitutionAssessment;
import com.dyys.hr.entity.train.TrainInstitutionAssessmentStaff;
import com.dyys.hr.entity.train.excel.InstitutionExcel;
import com.dyys.hr.entity.train.excel.QuestionnaireExcel;
import com.dyys.hr.entity.train.excel.QuestionnaireStaticsExcel;
import com.dyys.hr.examUtils.EasyExcelListener;
import com.dyys.hr.helper.UserHelper;
import com.dyys.hr.service.train.*;
import com.dyys.hr.utils.Result;
import com.dyys.hr.utils.SelectSheetWriteHandler;
import com.dyys.hr.vo.common.CourseChoiceVO;
import com.dyys.hr.vo.common.ProgramsChoiceVO;
import com.dyys.hr.vo.train.*;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 评估机构管理
 * Create by yangye
 * Date 2022/5/11 12:31
 */
@Slf4j
@RestController
@RequestMapping("/train/institution")
@Api(value = "培训机构",tags={"【培训】【资源管理】培训机构管理-已完成"})
public class TrainInstitutionController {
    @Autowired
    private UserHelper userHelper;
    @Autowired
    private TrainInstitutionService trainInstitutionService;
    @Autowired
    private TrainInstitutionGradeService trainInstitutionGradeService;
    @Autowired
    private TrainInstitutionAssessmentService trainInstitutionAssessmentService;
    @Autowired
    private TrainInstitutionAssessmentStaffService trainInstitutionAssessmentStaffService;
    @Autowired
    private QuestionnaireUserService questionnaireUserService;
    @Autowired
    private QuestionnaireTextService questionnaireTextService;
    @Autowired
    private QuestionnaireScoreSheetsService questionnaireScoreSheetsService;
    @Autowired
    private QuestionnaireScoreSheetsDetailService questionnaireScoreSheetsDetailService;
    @Autowired
    private QuestionnaireInstructionsService questionnaireInstructionsService;
    @Autowired
    private QuestionnaireCheckBoxService questionnaireCheckBoxService;

    @ResponseResult
    @GetMapping("institutionPageList")
    @ApiOperation(value = "培训机构分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = "name", value = "机构名称", paramType = "query", dataType="String") ,
            @ApiImplicitParam(name = "cateId", value = "机构类型", paramType = "query",dataType="int") ,
            @ApiImplicitParam(name = "principalName", value = "机构联系人", paramType = "query",dataType="String")
    })
    public PageView<TrainInstitutionVO> institutionPageList(@RequestParam @ApiIgnore Map<String, Object> params) {
        return trainInstitutionService.institutionListPage(params);
    }

    @ResponseResult
    @GetMapping("institutionInfo")
    @ApiOperation(value = "培训机构详情")
    @ApiImplicitParam(name = "id", value = "机构ID", paramType = "path", required = true, dataType="Long")
    public TrainInstitutionVO institutionInfo(@RequestParam Long id) {
        return trainInstitutionService.institutionInfo(id);
    }

    @ResponseResult
    @PostMapping("institutionAdd")
    @ApiOperation(value = "培训机构添加保存")
    public Long institutionAdd(@RequestBody @Validated(TrainInstitutionDTO.Insert.class) TrainInstitutionDTO institutionDTO) {
        String resultMsg = trainInstitutionService.checkNameAndAddress(institutionDTO, "add");
        if (!resultMsg.equals("")) {
            throw new BusinessException(ResultCode.ERROR, resultMsg);
        }
        return trainInstitutionService.institutionAdd(institutionDTO, userHelper.getLoginEmplId());
    }

    @ResponseResult
    @PostMapping("institutionUpdate")
    @ApiOperation(value = "培训机构更新保存")
    public Integer institutionUpdate(@RequestBody @Validated(TrainInstitutionDTO.Update.class) TrainInstitutionDTO institutionDTO) {
        String resultMsg = trainInstitutionService.checkNameAndAddress(institutionDTO, "update");
        if (!resultMsg.equals("")) {
            throw new BusinessException(ResultCode.ERROR, resultMsg);
        }
        return trainInstitutionService.institutionUpdate(institutionDTO, userHelper.getLoginEmplId());
    }

    @ResponseResult
    @GetMapping("historyServicePageList")
    @ApiOperation(value = "服务历史分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = "institutionId", value = "机构ID", paramType = "query",dataType="Long", required = true),
            @ApiImplicitParam(name = "trainName", value = "培训项目名称", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "planYear", value = "年度", paramType = "query", dataType="String")
    })
    public PageView<TrainInstitutionHistoryServiceVO> historyServicePageList(@RequestParam @ApiIgnore Map<String, Object> params) {
        return trainInstitutionService.historyServicePageList(params);
    }

    @ResponseResult
    @GetMapping("institutionGradePageList")
    @ApiOperation(value = "机构等级分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = "institutionId", value = "机构ID", paramType = "query",dataType="Long", required = true)
    })
    public PageView<TrainInstitutionGradeVO> institutionGradePageList(@RequestParam @ApiIgnore Map<String, Object> params) {
        return trainInstitutionGradeService.institutionGradePageList(params);
    }

    @ResponseResult
    @PostMapping("institutionGradeAdd")
    @ApiOperation(value = "机构等级添加保存")
    public Long institutionGradeAdd(@RequestBody @Validated(TrainInstitutionGradeDTO.Insert.class) TrainInstitutionGradeDTO gradeDTO) {
        String resultMsg = trainInstitutionGradeService.checkYearly(gradeDTO, "add");
        if (!resultMsg.equals("")) {
            throw new BusinessException(ResultCode.ERROR, resultMsg);
        }
        return trainInstitutionGradeService.institutionGradeAdd(gradeDTO, userHelper.getLoginEmplId());
    }

    @ResponseResult
    @PostMapping("institutionGradeUpdate")
    @ApiOperation(value = "机构等级更新保存")
    public Integer institutionGradeUpdate(@RequestBody @Validated(TrainInstitutionGradeDTO.Update.class) TrainInstitutionGradeDTO gradeDTO) {
        String resultMsg = trainInstitutionGradeService.checkYearly(gradeDTO, "update");
        if (!resultMsg.equals("")) {
            throw new BusinessException(ResultCode.ERROR, resultMsg);
        }
        return trainInstitutionGradeService.institutionGradeUpdate(gradeDTO, userHelper.getLoginEmplId());
    }

    @ResponseResult
    @GetMapping("assessmentPageList")
    @ApiOperation(value = "评估记录分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = "institutionId", value = "机构ID", paramType = "query",dataType="Long", required = true)
    })
    public PageView<TrainInstitutionAssessmentVO> assessmentPageList(@RequestParam @ApiIgnore Map<String, Object> params) {
        return trainInstitutionAssessmentService.assessmentPageList(params);
    }

    @ResponseResult
    @PostMapping("assessmentAdd")
    @ApiOperation(value = "评估创建保存")
    public Long assessmentAdd(@RequestBody @Validated(TrainInstitutionAssessmentDTO.Insert.class) TrainInstitutionAssessmentDTO assessmentDTO) {
        if (!assessmentDTO.getStartTime().before(assessmentDTO.getEndTime())) {
            throw new BusinessException(ResultCode.ERROR, "评估开始时间必须小于结束时间");
        }
        boolean res = trainInstitutionAssessmentService.checkUniqueData(assessmentDTO);
        if (res) {
            throw new BusinessException(ResultCode.ERROR, "同一评估标题同一年度同一评估模板已存在！");
        }
        return trainInstitutionAssessmentService.assessmentAdd(assessmentDTO, userHelper.getLoginEmplId());
    }

    @ResponseResult
    @PostMapping("assessmentUpdate")
    @ApiOperation(value = "评估更新保存")
    public Integer assessmentUpdate(@RequestBody @Validated(TrainInstitutionAssessmentDTO.Insert.class) TrainInstitutionAssessmentDTO assessmentDTO) {
        TrainInstitutionAssessment assessment = trainInstitutionAssessmentService.selectById(assessmentDTO.getId());
        if (!assessment.getStatus().equals(1)) {
            throw new BusinessException(ResultCode.ERROR, "未开始状态才可修改！");
        }
        if (!assessmentDTO.getStartTime().before(assessmentDTO.getEndTime())) {
            throw new BusinessException(ResultCode.ERROR, "评估开始时间必须小于结束时间");
        }
        boolean res = trainInstitutionAssessmentService.checkUniqueData(assessmentDTO);
        if (res) {
            throw new BusinessException(ResultCode.ERROR, "同一评估标题同一年度同一评估模板已存在！");
        }
        return trainInstitutionAssessmentService.assessmentUpdate(assessmentDTO, userHelper.getLoginEmplId());
    }

    @ResponseResult
    @PostMapping("assessmentCancel")
    @ApiOperation(value = "取消评估")
    public Integer assessmentCancel(@RequestBody @NotNull(message = "评估记录ID不能为空") String id) {
        Map<String, Object> params = JSONUtil.parseObj(id);
        Long assessmentId = Long.valueOf(params.get("id").toString());
        return trainInstitutionAssessmentService.assessmentCancel(assessmentId, userHelper.getLoginEmplId());
    }

    @ResponseResult
    @GetMapping("assessmentInfo")
    @ApiOperation(value = "评估详情")
    @ApiImplicitParam(name = "id", value = "评估记录ID", paramType = "path", required = true, dataType="Long")
    public TrainInstitutionAssessmentVO assessmentInfo(@RequestParam Long id) {
        return trainInstitutionAssessmentService.assessmentInfo(id);
    }

    @ResponseResult
    @GetMapping("assessmentStaffPageList")
    @ApiOperation(value = "评估详情-参评人员列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = "assessmentId", value = "评估记录ID", paramType = "query",dataType="Long", required = true)
    })
    public PageView<TrainInstitutionAssessmentStaffVO> assessmentStaffPageList(@RequestParam @ApiIgnore Map<String, Object> params) {
        return trainInstitutionAssessmentStaffService.assessmentStaffPageList(params);
    }

    @ResponseResult
    @GetMapping("exportUserAppraiseDetail")
    @ApiOperation(value = "下载用户评估详情")
    public void exportUserAppraiseDetail(HttpServletResponse response, @NotNull(message = "参评人员记录ID不能为空") Long id) throws IOException {
        //获取用户的评估问卷信息
        TrainInstitutionAssessmentStaff staff = trainInstitutionAssessmentStaffService.selectById(id);
        if(staff == null){
            throw new BusinessException(ResultCode.EXCEPTION,"参评人记录不存在");
        }
        QuestionnaireUser user = new QuestionnaireUser();
        user.setUserId(staff.getEvaluatorEmplId());
        user.setType(2);
        user.setTrainAppraiseId(staff.getAssessmentId());
        QuestionnaireUser questionnaireUser = questionnaireUserService.selectOne(user);
        List<QuestionnaireExcel> excelList = new ArrayList<>();

        if(questionnaireUser != null){
            //依次取出文本框，评分表，说明的数据
            Long questionnaireUserId = questionnaireUser.getId();
            //文本框
            Map<String, Object> map = new HashMap<>();
            map.put("questionnaireUserId",questionnaireUserId);
            List<QuestionnaireTextDTO> textDTOS = questionnaireTextService.getListByQuery(map);
            for (QuestionnaireTextDTO textDTO : textDTOS){
                QuestionnaireExcel excel = new QuestionnaireExcel();
                excel.setTitle(textDTO.getTitle() + "(文本项)");
                excelList.add(excel);

                QuestionnaireExcel textExcel = new QuestionnaireExcel();
                textExcel.setTitle(textDTO.getTitle());
                textExcel.setValue(textDTO.getValue());
                excelList.add(textExcel);
            }
            //评分表
            List<QuestionnaireScoreSheetsDTO> sheetsDTOS = questionnaireScoreSheetsService.getListByQuery(map);
            for (QuestionnaireScoreSheetsDTO sheetsDTO : sheetsDTOS){
                QuestionnaireExcel excel = new QuestionnaireExcel();
                excel.setTitle(sheetsDTO.getTitle() + "(评分项)");
                excelList.add(excel);
                for (QuestionnaireScoreSheetsDetailDTO detailDTO : sheetsDTO.getSheetsDetailList()){
                    QuestionnaireExcel detailExcel = new QuestionnaireExcel();
                    detailExcel.setTitle(detailDTO.getTableDataName());
                    detailExcel.setValue(detailDTO.getTableHeaderName());
                    detailExcel.setDescr(detailDTO.getDescr());
                    excelList.add(detailExcel);
                }
            }

            //说明
            List<QuestionnaireInstructionsDTO> instructionsDTOS = questionnaireInstructionsService.getListByQuery(map);
            for (QuestionnaireInstructionsDTO instructionsDTO : instructionsDTOS){
                QuestionnaireExcel excel = new QuestionnaireExcel();
                excel.setTitle(instructionsDTO.getTitle() + "(说明项)");
                excelList.add(excel);

                QuestionnaireExcel instructionsExcel = new QuestionnaireExcel();
                instructionsExcel.setTitle(instructionsDTO.getTitle());
                instructionsExcel.setValue(instructionsDTO.getValue());
                excelList.add(instructionsExcel);
            }

            //复选框
            List<QuestionnaireCheckBoxDTO> checkBoxDTOS = questionnaireCheckBoxService.getListByQuery(map);
            for (QuestionnaireCheckBoxDTO checkBoxDTO : checkBoxDTOS){
                QuestionnaireExcel excel = new QuestionnaireExcel();
                excel.setTitle(checkBoxDTO.getLabel() + "(选择项)");
                String valueString = "";
                for (QuestionnaireCheckBoxDetailDTO detailDTO : checkBoxDTO.getCheckBoxDetailList()){
                    valueString = valueString.concat(detailDTO.getValue() + "|");
                }
                excel.setValue(valueString.substring(0, valueString.length() - 1));
                excelList.add(excel);
                excelList.add(excel);
            }
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("员工评估机构详情", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

        // 绕过了创建临时文件，直接将数据读到流中传递至客户端
        EasyExcelFactory.write(response.getOutputStream(), QuestionnaireExcel.class).sheet("员工评估问卷结果").doWrite(excelList);
    }

    @ResponseResult
    @PostMapping("assessmentStaffDelete")
    @ApiOperation(value = "评估详情-参评人员移除")
    public Integer assessmentStaffDelete(@RequestBody @NotNull(message = "参评人员记录ID不能为空") String id) {
        Map<String, Object> params = JSONUtil.parseObj(id);
        Long assessmentStaffId = Long.valueOf(params.get("id").toString());
        return trainInstitutionAssessmentStaffService.assessmentStaffDelete(assessmentStaffId, userHelper.getLoginEmplId());
    }

    @ResponseResult
    @GetMapping("staffList")
    @ApiOperation(value = "机构评估-人员选择列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = "institutionId", value = "机构ID", paramType = "query",dataType="Long", required = true),
            @ApiImplicitParam(name = "emplName", value = "员工姓名", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "courseId", value = "课程ID", paramType = "query",dataType="Long"),
            @ApiImplicitParam(name = "programsId", value = "培训项目ID", paramType = "query",dataType="Long")
    })
    public PageView<TrainInstitutionStaffVO> staffList(@RequestParam @ApiIgnore Map<String, Object> params) {
        return trainInstitutionAssessmentStaffService.staffList(params);
    }

    @ResponseResult
    @GetMapping("courseChoiceList")
    @ApiOperation(value = "机构评估-人员选择列表-课程名称搜索项")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = "institutionId", value = "机构ID", paramType = "query",dataType="Long", required = true),
            @ApiImplicitParam(name = "courseName", value = "课程名称", paramType = "query",dataType="String")
    })
    public PageView<CourseChoiceVO> courseChoiceList(@RequestParam @ApiIgnore Map<String, Object> params) {
        return trainInstitutionAssessmentStaffService.courseChoiceList(params);
    }

    @ResponseResult
    @GetMapping("programsChoiceList")
    @ApiOperation(value = "机构评估-人员选择列表-培训项目搜索项")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = "institutionId", value = "机构ID", paramType = "query",dataType="Long", required = true),
            @ApiImplicitParam(name = "courseId", value = "课程ID", paramType = "query",dataType="Long"),
            @ApiImplicitParam(name = "trainName", value = "培训项目名称", paramType = "query",dataType="String")
    })
    public PageView<ProgramsChoiceVO> programsChoiceList(@RequestParam @ApiIgnore Map<String, Object> params) {
        return trainInstitutionAssessmentStaffService.programsChoiceList(params);
    }

    @GetMapping("exportTml")
    @ApiOperation(value = "下载培训机构导入模板")
    public void exportTml(HttpServletResponse response) throws IOException {
        // 获取对应导入模板表头
        List<InstitutionExcel> excelList = new ArrayList<>();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("培训机构导入模板", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ExcelTypeEnum.XLSX.getValue());
        // 列下拉框数据
        Map<Integer, List<String>> selectMap = trainInstitutionService.excelSelectMap();

        // 绕过了创建临时文件，直接将数据读到流中传递至客户端
        EasyExcelFactory.write(response.getOutputStream(), InstitutionExcel.class)
                .registerWriteHandler(new SelectSheetWriteHandler(selectMap))
                .excelType(ExcelTypeEnum.XLSX)
                .sheet("培训机构")
                .doWrite(excelList);
    }

    @PostMapping(value = "importInst", headers = "content-type=multipart/form-data")
    @ApiOperation(value = "导入培训机构")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "培训机构excel", dataTypeClass = MultipartFile.class, required = true, allowMultiple = true)
    })
    public Result<List<InstitutionExcel>> importInst(@RequestBody MultipartFile file) throws IOException {
        if (file == null) {
            return new Result<List<InstitutionExcel>>().error("请上传要导入的文件");
        }
        //定义最终入库的数据集合
        EasyExcelListener<InstitutionExcel> listener = new EasyExcelListener<>();
        List<InstitutionExcel> excelList =
                EasyExcelFactory.read(file.getInputStream(), InstitutionExcel.class, listener)
                        .sheet(0).doReadSync();
        if (excelList.isEmpty()) {
            return new Result<List<InstitutionExcel>>().error("导入培训机构数据不能为空");
        }
        List<InstitutionExcel> errorList = trainInstitutionService.importInst(excelList, userHelper.getLoginEmplId());
        if (errorList.isEmpty()) {
            return new Result<List<InstitutionExcel>>().success("导入成功");
        }
        return new Result<List<InstitutionExcel>>().error("培训机构数据校验不通过", errorList);
    }

    @GetMapping("exportInst")
    @ApiOperation(value = "导出培训机构数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "机构名称", paramType = "query", dataType="String") ,
            @ApiImplicitParam(name = "cateId", value = "机构类型", paramType = "query",dataType="int") ,
            @ApiImplicitParam(name = "principalName", value = "机构联系人", paramType = "query",dataType="String")
    })
    public void exportInst(HttpServletResponse response, @RequestParam @ApiIgnore Map<String, Object> params) throws IOException {
        List<InstitutionExcel> detailList = trainInstitutionService.exportInst(params);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyExcel没有关系
        String fileName = URLEncoder.encode("培训机构表", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ExcelTypeEnum.XLSX.getValue());

        // 绕过了创建临时文件，直接将数据读到流中传递至客户端
        EasyExcelFactory.write(response.getOutputStream(), InstitutionExcel.class)
                .sheet("培训机构")
                .doWrite(detailList);
    }

    @GetMapping("/exportInstAssessmentData")
    @ApiOperation(value = "导出机构评估数据")
    public void exportInstAssessmentData(HttpServletResponse response, Long assessmentId) throws IOException {
        //获取所有用户的已填写评估记录
        HashMap<String, Object> map = new HashMap<>();
        map.put("trainAppraiseId",assessmentId);
        map.put("type",2);
        List<QuestionnaireUser> userList = questionnaireUserService.getList(map);
        List<QuestionnaireStaticsExcel> excelList = new ArrayList<>();
        if(!userList.isEmpty()){
            //获取所有的评估评分表打分项列表
            List<Map<String, Object>> groupTableDataNameData = questionnaireScoreSheetsDetailService.getGroupTableDataNameData(map);
            //循环获取每个打分项对应的打分列数据统计
            int num = 1;
            for (Map<String, Object> dataList : groupTableDataNameData){
                String tableDataName = dataList.get("tableDataName").toString();
                Integer total = Integer.parseInt(dataList.get("total").toString());
                //构造数据
                HashMap<String, Object> query = new HashMap<>();
                query.put("trainAppraiseId",assessmentId);
                query.put("type",2);
                double totalAvg = questionnaireScoreSheetsDetailService.getAvgData(query);
                QuestionnaireStaticsExcel totalExcel = new QuestionnaireStaticsExcel();
                totalExcel.setTitle(num + ". " +tableDataName );
                totalExcel.setTotal("(评分题)");
                totalExcel.setAvg(String.format("%.2f",totalAvg));
                excelList.add(totalExcel);

                //获取打分列
                map.put("tableDataName",tableDataName);
                List<Map<String, Object>> groupTableHeaderNameData = questionnaireScoreSheetsDetailService.getGroupTableHeaderNameData(map);
                for (Map<String, Object> headerList : groupTableHeaderNameData){
                    String tableHeaderName = headerList.get("tableHeaderName").toString();
                    Integer everyNum = Integer.parseInt(headerList.get("total").toString());
                    //获取该综合项的平均分
                    map.put("tableHeaderName",tableHeaderName);
                    QuestionnaireStaticsExcel staticsExcel = new QuestionnaireStaticsExcel();
                    staticsExcel.setTitle(tableHeaderName);
                    staticsExcel.setTotal(everyNum.toString());
                    Double rate = Double.parseDouble(everyNum.toString()) / Double.parseDouble(total.toString()) * 100;
                    staticsExcel.setAvg(String.format("%.2f",rate) + "%");
                    excelList.add(staticsExcel);
                }
                num++;
            }

            //获取问卷答题数据列表
            List<Map<String, Object>> groupTextTitleData = questionnaireScoreSheetsDetailService.getGroupTextTitleData(map);
            for (Map<String, Object> titleData : groupTextTitleData){
                String title = titleData.get("title").toString();
                //构造数据
                QuestionnaireStaticsExcel titleExcel = new QuestionnaireStaticsExcel();
                titleExcel.setTitle(num + ". " +title );
                titleExcel.setTotal("(问答题)");
                excelList.add(titleExcel);
                QuestionnaireStaticsExcel headExcel = new QuestionnaireStaticsExcel();
                headExcel.setTitle("姓名：");
                headExcel.setTotal("内容");
                excelList.add(headExcel);
                //获取该项的所有问答
                map.put("title",title);
                List<Map<String, Object>> valueList = questionnaireScoreSheetsDetailService.getTextTitleValueList(map);
                for (Map<String, Object> list : valueList){
                    QuestionnaireStaticsExcel valueExcel = new QuestionnaireStaticsExcel();
                    String emplName = list.get("emplName").toString();
                    String value = list.get("value").toString();
                    valueExcel.setTitle(emplName + "：");
                    valueExcel.setTotal(value);
                    excelList.add(valueExcel);
                }
                num++;
            }
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("问卷机构评估统计数据", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

        // 绕过了创建临时文件，直接将数据读到流中传递至客户端
        EasyExcelFactory.write(response.getOutputStream(), QuestionnaireStaticsExcel.class).sheet("详情").doWrite(excelList);
    }
}
