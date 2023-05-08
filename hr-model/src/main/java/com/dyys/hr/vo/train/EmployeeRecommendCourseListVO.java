package com.dyys.hr.vo.train;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "员工推荐课程列表vo")
public class EmployeeRecommendCourseListVO {
    @ApiModelProperty(value = "课程Id")
    private Long courseId;

    @ApiModelProperty(value = "课程编号")
    private String courseNumber;

    @ApiModelProperty(value = "课程名称")
    private String courseName;

    @ApiModelProperty(value = "课程类别")
    private String cateName;

    @ApiModelProperty(value = "课程学分")
    private String credit;

    @ApiModelProperty(value = "课程学时")
    private String classHours;
}
