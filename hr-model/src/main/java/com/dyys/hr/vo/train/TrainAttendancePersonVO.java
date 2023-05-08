package com.dyys.hr.vo.train;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "培训项目考勤人员列表vo")
public class TrainAttendancePersonVO implements Serializable{
    @ApiModelProperty(value = "考勤学员记录id")
    private Long attendancePersonId;

    @ApiModelProperty(value = "学员编号")
    private String emplId;

    @ApiModelProperty(value = "学员名称")
    private String emplName;

    @ApiModelProperty(value = "公司编码")
    private String company;

    @ApiModelProperty(value = "公司名称")
    private String companyDescr;

    @ApiModelProperty(value = "部门编码")
    private String deptId;

    @ApiModelProperty(value = "部门名称")
    private String deptDescr;

    @ApiModelProperty(value = "岗位编码")
    private String postCode;

    @ApiModelProperty(value = "岗位名称")
    private String postDescr;
}
