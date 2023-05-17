package com.dyys.hr.controller.train;

import com.dagongma.kernel.annotation.ResponseResult;
import com.dagongma.kernel.commons.enums.ResultCode;
import com.dagongma.kernel.commons.exceptions.BusinessException;
import com.dyys.hr.constants.Constant;
import com.dyys.hr.dto.train.EmploySignInAttendanceDTO;
import com.dyys.hr.dto.train.QuestionnaireUserSubmitDTO;
import com.dyys.hr.entity.train.*;
import com.dyys.hr.helper.UserHelper;
import com.dyys.hr.service.staff.IStaffUserInfoService;
import com.dyys.hr.service.train.*;
import com.dyys.hr.vo.train.*;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import tk.mybatis.mapper.entity.Condition;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 员工中心相关接口
 *
 * @author WSJ
 * @date 2022/05/25
 */
@ResponseResult
@Slf4j
@RestController
@RequestMapping("/train/employee")
@Api(value = "员工页面接口", tags = {"【培训】【员工页面】员工页面接口 - 已完成"})
public class EmployeeCenterController {
    @Autowired
    private IStaffUserInfoService iStaffUserInfoService;
    @Autowired
    private TrainNoticeService trainNoticeService;
    @Autowired
    private TrainProgramsParticipantsService trainProgramsParticipantsService;
    @Autowired
    private TrainProgramsService trainProgramsService;
    @Autowired
    private TrainExamService trainExamService;
    @Autowired
    private TrainExaminerDetailService trainExaminerDetailService;
    @Autowired
    private TrainAppraiseService trainAppraiseService;
    @Autowired
    private TrainAttendanceRecordService trainAttendanceRecordService;
    @Autowired
    private QuestionnaireService questionnaireService;
    @Autowired
    private TrainInstitutionAssessmentStaffService trainInstitutionAssessmentStaffService;
    @Autowired
    private TrainInstitutionAssessmentService trainInstitutionAssessmentService;
    @Autowired
    private TrainLearnMapCourseService trainLearnMapCourseService;
    @Autowired
    private UserHelper userHelper;

    @GetMapping("relatedData")
    @ApiOperation(value = "工作台数据")
    public EmployeeRelateDataVO relatedData() {
        return iStaffUserInfoService.relatedData(userHelper.getLoginEmplId());
    }

    @GetMapping("participantsNoticeList")
    @ApiOperation(value = "参训通知列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
    })
    public PageInfo<EmployeeParticipantsNoticeListVO> participantsNoticeList(@ApiIgnore @RequestParam Map<String, Object> params){
        params.put("user_id",userHelper.getLoginEmplId());
        return trainNoticeService.participantsNoticeList(params);
    }

    @GetMapping("toDoNoticeList")
    @ApiOperation(value = "代办通知列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
            @ApiImplicitParam(name = "type", value = "通知类型 2.考试 3.培训评估 4.机构评估 11.计划审批 12.培训资料学习", paramType = "query", dataType="int")
    })
    public PageInfo<EmployeeToDoNoticeListVO> toDoNoticeList(@ApiIgnore @RequestParam Map<String, Object> params){
        params.put("user_id",userHelper.getLoginEmplId());
        return trainNoticeService.toDoNoticeList(params);
    }

    @GetMapping("recommendProgramsList")
    @ApiOperation(value = "推荐课程列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
    })
    public PageInfo<EmployeeRecommendCourseListVO> recommendCourseList(@ApiIgnore @RequestParam Map<String, Object> params){
        return trainLearnMapCourseService.recommendCourseList(params, userHelper.getLoginEmplId());
    }


    @GetMapping("trainingSchedule")
    @ApiOperation(value = "培训课表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "year", value = "年份", paramType = "query", dataType="string") ,
    })
    public List<List<EmployeeTrainingScheduleVO>> trainingSchedule(@ApiIgnore @RequestParam Map<String, Object> params){
        params.put("user_id",userHelper.getLoginEmplId());
        return iStaffUserInfoService.trainingSchedule(params);
    }

    @GetMapping("noticeProgramsDetail")
    @ApiOperation(value = "参训通知详情页面")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "trainId", value = "项目ID", paramType = "path", required = true, dataType="int") ,
    })
    public TrainProgramsDetailVO find(@ApiIgnore @RequestParam Long trainId) {
        TrainProgramsDetailVO vo = trainProgramsService.getDetail(trainId);
        vo.setProgramsParticipantsList(null);
        TrainProgramsParticipants participants = new TrainProgramsParticipants();
        participants.setProgramsId(trainId);
        participants.setUserId(userHelper.getLoginEmplId());
        TrainProgramsParticipants selectOne = trainProgramsParticipantsService.selectOne(participants);
        vo.setSingUp(selectOne.getStatus());
        return vo;
    }

    @GetMapping("agreeToParticipate")
    @ApiOperation(value = "同意参训")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "trainId", value = "项目ID", paramType = "path", required = true, dataType="int") ,
    })
    @Transactional(rollbackFor = Exception.class)
    public Boolean agreeToParticipate(@ApiIgnore @RequestParam Long trainId) {
        //更新参训人员表为已确认
        String userId = userHelper.getLoginEmplId();
        TrainProgramsParticipants pUpdate = new TrainProgramsParticipants();
        pUpdate.setStatus(1);
        pUpdate.setUpdateUser(userHelper.getLoginEmplId());
        pUpdate.setUpdateTime(System.currentTimeMillis()/1000);
        Condition pCondition = new Condition(TrainProgramsParticipants.class);
        pCondition.createCriteria().andCondition("programs_id =  " + trainId +" and user_id = '" + userId + "'");
        trainProgramsParticipantsService.updateByConditionSelective(pUpdate,pCondition);

        //更新参训通知为已处理
        TrainNotice nUpdate = new TrainNotice();
        nUpdate.setStatus(1);
        nUpdate.setUpdateUser(userHelper.getLoginEmplId());
        nUpdate.setUpdateTime(System.currentTimeMillis()/1000);
        Condition nCondition = new Condition(TrainNotice.class);
        nCondition.createCriteria().andCondition("type = 1 and type_id =  " + trainId +" and user_id = '" + userId + "'");
        trainNoticeService.updateByConditionSelective(nUpdate,nCondition);
        return true;
    }


    @GetMapping("myProgramsList")
    @ApiOperation(value = "我的培训列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
            @ApiImplicitParam(name = "status", value = "项目状态 1.进行中 2.已完成", paramType = "query",required = true,dataType="int"),
    })
    public PageInfo<EmployeeProgramsVO> myProgramsList(@ApiIgnore @RequestParam Map<String, Object> params){
        params.put("userId",userHelper.getLoginEmplId());
        return trainProgramsService.employeePageList(params);
    }

    @GetMapping("myProgramsDetail")
    @ApiOperation(value = "我的培训查看")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "项目ID", paramType = "path", required = true, dataType="int") ,
    })
    public TrainProgramsDetailVO myProgramsDetail(@ApiIgnore @RequestParam Long id) {
        TrainProgramsDetailVO detail = trainProgramsService.getDetail(id);
        detail.setProgramsParticipantsList(null);
        return detail;
    }

    @GetMapping("myAttendanceRecordList")
    @ApiOperation(value = "我的考勤记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
            @ApiImplicitParam(name = "programsId", value = "项目id", paramType = "query", required = true,dataType="int"),
            @ApiImplicitParam(name = "date", value = "考勤日期", paramType = "query", dataType="String"),
    })
    public PageInfo<EmployeeAttendanceRecordPageVO> myAttendanceRecordList(@ApiIgnore @RequestParam Map<String, Object> params){
        params.put("emplId",userHelper.getLoginEmplId());
        return trainAttendanceRecordService.myAttendanceRecordList(params);
    }

    @PostMapping("signInAttendance")
    @ApiOperation(value = "项目考勤签到")
    public Integer signInAttendance(@RequestBody EmploySignInAttendanceDTO dto) throws ParseException {
        if (dto.getAttendanceRulesId() == null || dto.getEmplId() == null) {
            throw new BusinessException(ResultCode.EXCEPTION, "缺少必要参数");
        }
        return trainAttendanceRecordService.signInAttendance(dto);
    }

    @GetMapping("myExamList")
    @ApiOperation(value = "我的考试记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
            @ApiImplicitParam(name = "programsId", value = "项目id", paramType = "query", required = true,dataType="int"),
            @ApiImplicitParam(name = "title", value = "考试标题", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "status", value = "状态 0.未通过 1.已通过 2.未答卷", paramType = "query",dataType="int")
    })
    public PageInfo<EmployeeExamPageVO> myExamList(@ApiIgnore @RequestParam Map<String, Object> params){
        params.put("userId",userHelper.getLoginEmplId());
        return trainExamService.myExamList(params);
    }

    @GetMapping("myExamDetail")
    @ApiOperation(value = "我的考试详情明细")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "examinerId", value = "参考记录id", paramType = "path", required = true, dataType="int") ,
    })
    public List<TrainExaminerDetailVO> myExamDetail(@ApiIgnore @RequestParam Long examinerId) {
        Map<String, Object> query = new HashMap<>();
        query.put("examiner_id",examinerId);
        return trainExaminerDetailService.getListByQuery(query);
    }

    @GetMapping("myAppraiseList")
    @ApiOperation(value = "评估中心-培训评估列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
            @ApiImplicitParam(name = "programsId", value = "项目id", paramType = "query",dataType="int"),
            @ApiImplicitParam(name = "title", value = "评估标题", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "status", value = "状态 0.待提交 1.正完成", paramType = "query",dataType="int")
    })
    public PageInfo<EmployeeAppraisePageVO> myAppraiseList(@ApiIgnore @RequestParam Map<String, Object> params){
        params.put("userId",userHelper.getLoginEmplId());
        return trainAppraiseService.myAppraiseList(params);
    }

    @GetMapping("getQuestionnaireDetail")
    @ApiOperation(value = "培训评估获取问卷详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appraiseId", value = "培训评估主键id", paramType = "path", dataType="int")
    })
    public Map<String, Object> getQuestionnaireDetail(@ApiIgnore @RequestParam Map<String, Object> params) {
        //校验评估是否可以正常进行
        TrainAppraise trainAppraise = trainAppraiseService.selectById(Long.valueOf(params.get("appraiseId").toString()));
        Map<String, Object> map = new HashMap<>();
        if(trainAppraise.getStatus() < 1){
            map.put("code", 0);
            map.put("msg", "评估未开始,不能作答!");
            return map;
        } else if(trainAppraise.getStatus() > 1){
            map.put("code", 0);
            map.put("msg", "评估已结束,不能作答!");
            return map;
        } else {
            map.put("code", 1);
            map.put("msg", "获取详情成功");
            map.put("info", questionnaireService.getDetail(params));
            return map;
        }
    }

    @GetMapping("institutionAssessPageList")
    @ApiOperation(value = "评估中心-机构评估列表(新增)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
            @ApiImplicitParam(name = "institutionId", value = "培训机构ID", paramType = "query",dataType="long"),
            @ApiImplicitParam(name = "title", value = "评估标题", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "status", value = "状态 0未完成 1.已完成", paramType = "query",dataType="int")
    })
    public PageInfo<EmplInstitutionAssessVO> institutionAssessPageList(@ApiIgnore @RequestParam Map<String, Object> params){
        params.put("evaluatorEmplId",userHelper.getLoginEmplId());
        return trainInstitutionAssessmentStaffService.institutionAssessPageList(params);
    }

    @GetMapping("institutionQuestionnaireInfo")
    @ApiOperation(value = "机构评估问卷详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "assessmentId", value = "机构评估记录id", paramType = "path", dataType="int")
    })
    public Map<String, Object> institutionQuestionnaireInfo(@ApiIgnore @RequestParam Map<String, Object> params) {
        //校验评估是否可以正常进行
        TrainInstitutionAssessment assessment = trainInstitutionAssessmentService.selectById(Long.valueOf(params.get("assessmentId").toString()));
        Map<String, Object> map = new HashMap<>();
        if(assessment.getStatus() < 2){
            map.put("code", 0);
            map.put("msg", "评估未开始,不能作答!");
            return map;
        } else if(assessment.getStatus() > 2){
            map.put("code", 0);
            map.put("msg", "评估已结束,不能作答!");
            return map;
        } else {
            map.put("code", 1);
            map.put("msg", "获取详情成功");
            map.put("info", questionnaireService.getDetail(params));
            return map;
        }
    }


    @GetMapping("mynQuestionnaireSubmitDetail")
    @ApiOperation(value = "获取问卷提交详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appraiseId", value = "评估主键id", paramType = "path", dataType="int"),
            @ApiImplicitParam(name = "type", value = "评估类型 1.培训评估 2.机构评估", paramType = "path", dataType="int")
    })
    public QuestionnaireUserSubmitDTO mynQuestionnaireSubmitDetail(@ApiIgnore @RequestParam Map<String, Object> params) {
        params.put("user_id",userHelper.getLoginEmplId());
        return trainAppraiseService.mynQuestionnaireSubmitDetail(params);
    }
}