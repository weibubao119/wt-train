package com.dyys.hr.vo.train;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "e-learning数据分页VO")
public class ELearningPageVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "工号")
    private String emplId;

    @ApiModelProperty(value = "员工姓名")
    private String emplName;

    @ApiModelProperty(value = "公司id")
    private String company;

    @ApiModelProperty(value = "公司名称")
    private String companyDescr;

    @ApiModelProperty(value = "部门id")
    private String deptId;

    @ApiModelProperty(value = "部门名称")
    private String deptDescr;

    @ApiModelProperty(value = "岗位编码")
    private String postCode;

    @ApiModelProperty(value = "岗位名称")
    private String postDescr;

    @ApiModelProperty(value = "职族编码")
    private String profession;

    @ApiModelProperty(value = "职族名称")
    private String professionDescr;

    @ApiModelProperty(value = "职序编码")
    private String secCode;

    @ApiModelProperty(value = "职序名称")
    private String secDescr;

    @ApiModelProperty(value = "职级编码")
    private String gradeCode;

    @ApiModelProperty(value = "职级名称")
    private String gradeDescr;

    @ApiModelProperty(value = "项目编号")
    private String trainNumber;

    @ApiModelProperty(value = "项目名称")
    private String trainName;

    @ApiModelProperty(value = "课程编号")
    private String courseNumber;

    @ApiModelProperty(value = "课程名称")
    private String courseName;

    @ApiModelProperty(value = "课程类别ID")
    private Integer courseCategory;

    @ApiModelProperty(value = "课程类别名称")
    private String courseCategoryName;

    @ApiModelProperty(value = "课程学时")
    private BigDecimal courseClassHours;

    @ApiModelProperty(value = "课程学分")
    private BigDecimal courseCredit;

    @ApiModelProperty(value = "结业条件")
    private String finishType;

    @ApiModelProperty(value = "考试是否通过 1.是 0.否")
    private Integer isPass;

    @ApiModelProperty(value = "考试成绩")
    private String examScore;

    @ApiModelProperty(value = "获得学分")
    private BigDecimal credit;

    @ApiModelProperty(value = "获取学分时间")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date getCreditTime;

}
