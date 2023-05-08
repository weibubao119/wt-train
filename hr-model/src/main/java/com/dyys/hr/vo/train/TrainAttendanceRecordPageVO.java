package com.dyys.hr.vo.train;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "培训项目考勤明细分页vo")
public class TrainAttendanceRecordPageVO implements Serializable{
    @ApiModelProperty(value = "记录id")
    private Long id;

    @ApiModelProperty(value = "培训项目名称")
    private String trainName;

    @ApiModelProperty(value = "课程名称")
    private String courseName;

    @ApiModelProperty(value = "工号")
    private String emplId;

    @ApiModelProperty(value = "学员姓名")
    private String emplName;

    @ApiModelProperty(value = "公司名称")
    private String companyDescr;

    @ApiModelProperty(value = "部门名称")
    private String deptDescr;

    @ApiModelProperty(value = "考勤日期")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date date;

    @ApiModelProperty(value = "开始时间")
    private String startTime;

    @ApiModelProperty(value = "结束时间")
    private String endTime;

    @ApiModelProperty(value = "签到时间")
    private String signedInTime;

    @ApiModelProperty(value = "签到状态名称")
    private String statusName;
}
