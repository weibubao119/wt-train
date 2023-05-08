package com.dyys.hr.vo.train;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "讲师已授课列表vo")
public class TeacherHaveTaughtRecordsVO implements Serializable {

    @ApiModelProperty(value = "培训项目名称")
    private String trainName;

    @ApiModelProperty(value = "课程编号")
    private String courseNumber;

    @ApiModelProperty(value = "课程名称")
    private String courseName;

    @ApiModelProperty(value = "课程类别")
    private String courseCategoryName;

    @ApiModelProperty(value = "开始时间")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:ii")
    private String startTime;

    @ApiModelProperty(value = "结束时间")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:ii")
    private String endTime;

    @ApiModelProperty(value = "课时数")
    private Integer courseClassHours;

    @ApiModelProperty(value = "学员人数")
    private Integer studentsNumber;

    @ApiModelProperty(value = "讲师评分")
    private String score;
}
