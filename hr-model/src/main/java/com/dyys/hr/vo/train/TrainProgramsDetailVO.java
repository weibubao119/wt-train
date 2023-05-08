package com.dyys.hr.vo.train;

import com.dyys.hr.dto.train.*;
import com.dyys.hr.vo.common.OrgDeptVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "培训项目详情vo")
public class TrainProgramsDetailVO implements Serializable {
    @ApiModelProperty(value = "项目主键")
    private Long id;

    @ApiModelProperty(value = "培训项目编号")
    private String number;

    @ApiModelProperty(value = "培训名称")
    private String trainName;

    @ApiModelProperty(value = "大计划id")
    private Long planId;

    @ApiModelProperty(value = "小计划id")
    private Long planDetailId;

    @ApiModelProperty(value = "大计划名称")
    private String planName;

    @ApiModelProperty(value = "公司编码")
    private String companyCode;

    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @ApiModelProperty(value = "组织单位(部门)编码")
    private String deptId;

    @ApiModelProperty(value = "组织单位(部门)名称")
    private String deptPathName;

    @ApiModelProperty(value = "协办单位列表")
    private List<OrgDeptVO> coOrganizer;

    @ApiModelProperty(value = "开始时间")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date endTime;

    @ApiModelProperty(value = "受训对象")
    private String trainSubject;

    @ApiModelProperty(value = "参训人数")
    private Integer participantsNum;

    @ApiModelProperty(value = "培训形式 1.内部 2.外部")
    private Integer trainShape;

    @ApiModelProperty(value = "培训形式名称")
    private String trainShapeName;

    @ApiModelProperty(value = "经费预算")
    private BigDecimal outlay;

    @ApiModelProperty(value = "负责人列表")
    private List<PersonDTO> principalList;

    @ApiModelProperty(value = "培训计划列表")
    private List<TrainProgramsPlanDetailVO> programsPlanList;

    @ApiModelProperty(value = "培训课表列表")
    private List<TrainProgramsCourseDetailVO> programsCourseList;

    @ApiModelProperty(value = "培训讲师列表")
    private List<TrainProgramsTeacherDetailVO> programsTeacherList;

    @ApiModelProperty(value = "培训参训名单列表")
    private List<TrainProgramsParticipantsDetailVO> programsParticipantsList;

    @ApiModelProperty(value = "当前员工是否已报名 1.是 0.否")
    private Integer singUp;

    @ApiModelProperty(value = "项目状态 1.进行中 2.已完成")
    private Integer status;
}
