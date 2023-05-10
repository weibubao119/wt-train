package com.dyys.hr.controller.train;

import com.alibaba.excel.EasyExcelFactory;
import com.dagongma.kernel.annotation.ResponseResult;
import com.dagongma.kernel.commons.enums.ResultCode;
import com.dagongma.kernel.commons.exceptions.BusinessException;
import com.dyys.hr.constants.Constant;
import com.dyys.hr.dto.train.*;
import com.dyys.hr.entity.train.*;
import com.dyys.hr.entity.train.excel.QuestionnaireExcel;
import com.dyys.hr.entity.train.excel.QuestionnaireStaticsExcel;
import com.dyys.hr.helper.UserHelper;
import com.dyys.hr.service.train.*;
import com.dyys.hr.vo.train.*;
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

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

/**
 * 培训项目评估接口
 *
 * @author WSJ
 * @date 2022/05/21
 */
@ResponseResult
@Slf4j
@RestController
@RequestMapping("/train/appraise")
@Api(value = "培训项目评估接口", tags = {"【培训】【过程管理】培训项目-评估管理-已完成"})
public class TrainAppraiseController {
    @Autowired
    private TrainAppraiseService trainAppraiseService;
    @Autowired
    private TrainAppraisePersonService trainAppraisePersonService;
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


    @Autowired
    private TrainExamService trainExamService;
    @Autowired
    private TrainInstitutionAssessmentService trainInstitutionAssessmentService;


    @Autowired
    private UserHelper userHelper;

    @GetMapping("pageList")
    @ApiOperation(value = "评估列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
            @ApiImplicitParam(name = "programsId", value = "项目id", paramType = "query", required = true,dataType="int"),
            @ApiImplicitParam(name = "title", value = "评估标题", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "courseId", value = "评估课程id", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "status", value = "状态 0.未开始 1.正在进行 2.结束", paramType = "query",dataType="int")
    })
    public PageInfo<TrainAppraiseVO> pageList(@ApiIgnore @RequestParam Map<String, Object> params){
        return trainAppraiseService.pageList(params);
    }

    @PostMapping("save")
    @ApiOperation(value = "创建评估")
    public Long save(@RequestBody @Validated(TrainAppraiseDTO.Insert.class) TrainAppraiseDTO dto) {
        if(dto.getEndTime().before(dto.getStartTime())){
            throw new BusinessException(ResultCode.EXCEPTION,"结束时间不能小于开始时间!");
        }
        //校验同课程评估模板唯一
        TrainAppraise query = new TrainAppraise();
        query.setProgramsId(dto.getProgramsId());
        query.setCourseId(dto.getCourseId());
        query.setQuestionnaireId(dto.getQuestionnaireId());
        TrainAppraise selectOne = trainAppraiseService.selectOne(query);
        if(selectOne != null){
            throw new BusinessException(ResultCode.EXCEPTION,"该关联课程已发起相同评估,请勿重复!");
        }
        return trainAppraiseService.save(dto,userHelper.getLoginEmplId());
    }

    @GetMapping("find")
    @ApiOperation(value = "评估详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "评估ID", paramType = "path", required = true, dataType="int") ,
    })
    public TrainAppraiseDetailVO find(@ApiIgnore @RequestParam Long id) {
        return trainAppraiseService.getDetail(id);
    }


    @PostMapping("update")
    @ApiOperation(value = "评估修改")
    public Integer update(@RequestBody @Validated(TrainAppraiseDTO.Update.class) TrainAppraiseDTO dto) {
        if(dto.getEndTime().before(dto.getStartTime())){
            throw new BusinessException(ResultCode.EXCEPTION,"结束时间不能小于开始时间!");
        }
        //已结束的评估不能修改
        TrainAppraise trainAppraise = trainAppraiseService.selectById(dto.getId());
        if(trainAppraise.getStatus() > 0){
            throw new BusinessException(ResultCode.EXCEPTION,"评估正在进行或已结束无法修改!");
        }
        return trainAppraiseService.update(dto,userHelper.getLoginEmplId());
    }

    @PostMapping("remove")
    @ApiOperation(value = "参评人员移除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appraisePersonId", value = "参评记录id", paramType = "path", required = true, dataType="int") ,
    })
    public Integer remove(@ApiIgnore @RequestBody Map<String, Integer> params) {
        return trainAppraisePersonService.remove(params.get("appraisePersonId").longValue());
    }

    @GetMapping("/exportUserAppraiseDetail")
    @ApiOperation(value = "下载用户评估详情")
    public void exportUserAppraiseDetail(HttpServletResponse response, Long appraisePersonId) throws IOException {
        //获取用户的评估问卷信息
        TrainAppraisePerson person = trainAppraisePersonService.selectById(appraisePersonId);
        if(person == null){
            throw new BusinessException(ResultCode.EXCEPTION,"评估记录不存在");
        }
        QuestionnaireUser user = new QuestionnaireUser();
        user.setUserId(person.getUserId());
        user.setType(1);
        user.setTrainAppraiseId(person.getAppraiseId());
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
        String fileName = URLEncoder.encode("用户问卷评估详情", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

        // 绕过了创建临时文件，直接将数据读到流中传递至客户端
        EasyExcelFactory.write(response.getOutputStream(), QuestionnaireExcel.class).sheet("详情").doWrite(excelList);
    }

    @GetMapping("/exportAppraiseData")
    @ApiOperation(value = "导出评估数据")
    public void exportAppraiseData(HttpServletResponse response, Long appraiseId) throws IOException {
        //获取所有用户的已填写评估记录
        HashMap<String, Object> map = new HashMap<>();
        map.put("trainAppraiseId",appraiseId);
        map.put("type",1);
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
                query.put("trainAppraiseId",appraiseId);
                query.put("type",1);
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
        String fileName = URLEncoder.encode("问卷培训评估统计数据", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

        // 绕过了创建临时文件，直接将数据读到流中传递至客户端
        EasyExcelFactory.write(response.getOutputStream(), QuestionnaireStaticsExcel.class).sheet("详情").doWrite(excelList);
    }

    @PostMapping("batchAppraiseNotice")
    @ApiOperation(value = "批量评估通知提醒")
    public Boolean batchAppraiseNotice(@RequestBody @Validated(IdDTO.Insert.class) List<IdDTO> dtoList) {
        return trainAppraiseService.batchAppraiseNotice(dtoList);
    }
}