package com.dyys.hr.vo.train;

import com.dyys.hr.dto.train.PersonDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "培训项目列表vo")
public class TrainProgramsVO implements Serializable{
    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "培训项目编号")
    private String number;

    @ApiModelProperty(value = "培训名称")
    private String trainName;

    @ApiModelProperty(value = "开始时间")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date endTime;

    @ApiModelProperty(value = "参训人数")
    private Integer participantsNum;

    @ApiModelProperty(value = "公司编码")
    private String companyCode;

    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @ApiModelProperty(value = "组织单位(部门)编码")
    private String deptId;

    @ApiModelProperty(value = "组织单位(部门)名称")
    private String deptPathName;

    @ApiModelProperty(value = "负责人列表")
    private List<PersonDTO> principalList;

    @ApiModelProperty(value = "状态")
    private Integer  status;

    @ApiModelProperty(value = "状态名称")
    private String statusName;

    @ApiModelProperty(value = "所属计划id")
    private Long planId;

    @ApiModelProperty(value = "计划名称")
    private String planTitle;

    @ApiModelProperty(value = "年度")
    private String planYear;
}
