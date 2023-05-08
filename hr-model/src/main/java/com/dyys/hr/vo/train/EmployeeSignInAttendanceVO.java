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
@ApiModel(value = "学员签到操作vo")
public class EmployeeSignInAttendanceVO implements Serializable{
    @ApiModelProperty(value = "记录Id")
    private Long id;

    @ApiModelProperty(value = "签到开始时间")
    private String startTime;

    @ApiModelProperty(value = "签到结束时间")
    private String endTime;

    @ApiModelProperty(value = "本节课结束时间")
    private String courseEndTime;

    @ApiModelProperty(value = "实际签到时间")
    private String signedInTime;

    @ApiModelProperty(value = "签到状态")
    private Integer status;
}
