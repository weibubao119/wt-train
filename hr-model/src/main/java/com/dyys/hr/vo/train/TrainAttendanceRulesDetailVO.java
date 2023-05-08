package com.dyys.hr.vo.train;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "培训项目考勤规则详情vo")
public class TrainAttendanceRulesDetailVO implements Serializable{
    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "项目id")
    private Long programsId;

    @ApiModelProperty(value = "项目课表id")
    private Long programsCourseId;

    @ApiModelProperty(value = "课程id")
    private Long courseId;

    @ApiModelProperty(value = "课程名称")
    private String courseName;

    @ApiModelProperty(value = "考勤日期")
    private Date date;

    @ApiModelProperty(value = "签到开始时间")
    private Time startTime;

    @ApiModelProperty(value = "签到结束时间")
    private Time endTime;

    @ApiModelProperty(value = "考勤人员数据列表")
    private List<TrainAttendancePersonVO> attendancePersonList;
}
