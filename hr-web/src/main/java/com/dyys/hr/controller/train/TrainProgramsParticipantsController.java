package com.dyys.hr.controller.train;

import com.alibaba.excel.EasyExcel;
import com.dagongma.kernel.annotation.ResponseResult;
import com.dyys.hr.constants.Constant;
import com.dyys.hr.dto.train.IdDTO;
import com.dyys.hr.dto.train.TrainProgramsParticipantsDTO;
import com.dyys.hr.entity.train.excel.ProgramsParticipantsExcel;
import com.dyys.hr.helper.UserHelper;
import com.dyys.hr.service.train.TrainProgramsParticipantsService;
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
import java.util.List;
import java.util.Map;

/**
 * 培训管理参训人员操作接口
 *
 * @author WSJ
 * @date 2022/05/19
 */
@ResponseResult
@Slf4j
@RestController
@RequestMapping("/train/programsParticipants")
@Api(value = "项目管理接口", tags = {"【培训】【过程管理】培训项目-参训人员管理-已完成"})
public class TrainProgramsParticipantsController {
    @Autowired
    private TrainProgramsParticipantsService trainProgramsParticipantsService;
    @Autowired
    private UserHelper userHelper;

    @GetMapping("pageList")
    @ApiOperation(value = "人员列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
            @ApiImplicitParam(name = "programsId", value = "项目id", paramType = "query", required = true,dataType="int"),
            @ApiImplicitParam(name = "emplName", value = "学员姓名", paramType = "query", dataType="String"),
            @ApiImplicitParam(name = "companyCode", value = "公司编码", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "departmentCode", value = "部门编码", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "status", value = "状态 0.待确认 1.已报名 2.待通知", paramType = "query",dataType="int"),
            @ApiImplicitParam(name = "posnGradeCode", value = "职级编码", paramType = "query",dataType="String"),
            @ApiImplicitParam(name = "posnSecCode", value = "职序编码", paramType = "query",dataType="String")
    })
    public PageInfo<ProgramsParticipantsExcel> pageList(@ApiIgnore @RequestParam Map<String, Object> params){
        return trainProgramsParticipantsService.pageList(params);
    }

    @PostMapping("noticeSignUp")
    @ApiOperation(value = "通知报名")
    public Boolean noticeSignUp(@RequestBody @Validated(IdDTO.Insert.class) List<IdDTO> dtoList) {
        return trainProgramsParticipantsService.noticeSignUp(dtoList,userHelper.getLoginEmplId());
    }

    @PostMapping("confirmSignUp ")
    @ApiOperation(value = "批量确认报名")
    public Boolean confirmSignUp(@RequestBody @Validated(IdDTO.Insert.class) List<IdDTO> dtoList) {
        return trainProgramsParticipantsService.confirmSignUp(dtoList,userHelper.getLoginEmplId());
    }

    @PostMapping("addStudents")
    @ApiOperation(value = "添加学员")
    public Boolean addStudents(@RequestBody List<TrainProgramsParticipantsDTO> dtoList) {
        return trainProgramsParticipantsService.addStudents(dtoList,userHelper.getLoginEmplId());
    }

    @PostMapping("removeStudents")
    @ApiOperation(value = "移除学员")
    public Integer removeStudents(@RequestBody @Validated(IdDTO.Insert.class) List<IdDTO> dtoList) {
        return trainProgramsParticipantsService.removeStudents(dtoList,userHelper.getLoginEmplId());
    }

    @GetMapping("exportParticipants")
    @ApiOperation(value = "导出参训人员")
    public void exportParticipants(HttpServletResponse response,String programsId) throws IOException {
        List<ProgramsParticipantsExcel> excelList = trainProgramsParticipantsService.exportList(programsId);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("项目参训人员", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), ProgramsParticipantsExcel.class).sheet("人员表").doWrite(excelList);
    }
}