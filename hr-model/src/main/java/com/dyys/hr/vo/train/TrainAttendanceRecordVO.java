package com.dyys.hr.vo.train;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "培训项目考勤记录列表vo")
public class TrainAttendanceRecordVO implements Serializable{
    @ApiModelProperty(value = "考勤记录id")
    private Long id;

    @ApiModelProperty(value = "学员编号")
    private String userId;

    @ApiModelProperty(value = "学员名称")
    private String userName;

    @ApiModelProperty(value = "公司编码")
    private String companyCode;

    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @ApiModelProperty(value = "部门编码")
    private String departmentCode;

    @ApiModelProperty(value = "部门名称")
    private String departmentName;

    @ApiModelProperty(value = "岗位编码")
    private String jobCode;

    @ApiModelProperty(value = "岗位名称")
    private String jobName;

    @ApiModelProperty(value = "签到时间")
    private String signedInTime;

    @ApiModelProperty(value = "签到状态")
    private String status;

    @ApiModelProperty(value = "签到状态名称")
    private String statusName;
}
