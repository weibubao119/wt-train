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
@ApiModel(value = "培训项目-考勤管理列表vo")
public class TrainAttendanceRulesVO implements Serializable{
    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "课程名称")
    private String courseName;

    @ApiModelProperty(value = "课程上课时间")
    private String courseTime;

    @ApiModelProperty(value = "考勤日期")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date date;

    @ApiModelProperty(value = "开始时间")
    private String startTime;

    @ApiModelProperty(value = "结束时间")
    private String endTime;

    @ApiModelProperty(value = "考勤人数")
    private Integer totalNum;

    @ApiModelProperty(value = "正常出勤人数")
    private Integer attendanceNum;

    @ApiModelProperty(value = "缺勤人数")
    private Integer absentNum;

    @ApiModelProperty(value = "迟到人数")
    private Integer lateNum;
}
