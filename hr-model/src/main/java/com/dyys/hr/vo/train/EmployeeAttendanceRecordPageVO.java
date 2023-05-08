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
@ApiModel(value = "员工考勤记录列表vo")
public class EmployeeAttendanceRecordPageVO implements Serializable{
    @ApiModelProperty(value = "记录Id")
    private Long id;

    @ApiModelProperty(value = "考勤日期")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date date;

    @ApiModelProperty(value = "开始时间")
    private String startTime;

    @ApiModelProperty(value = "结束时间")
    private String endTime;

    @ApiModelProperty(value = "实际签到时间")
    private String signedInTime;

    @ApiModelProperty(value = "签到状态")
    private Integer status;

    @ApiModelProperty(value = "签到状态名称")
    private String statusName;


}
