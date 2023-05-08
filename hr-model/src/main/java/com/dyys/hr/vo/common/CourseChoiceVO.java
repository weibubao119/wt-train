package com.dyys.hr.vo.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Desc
 * Create by yangye
 * Date 2022/12/21 8:54
 */
@Data
public class CourseChoiceVO {
    @ApiModelProperty(value = "课程ID")
    private Long courseId;

    @ApiModelProperty(value = "课程编号")
    private String courseNumber;

    @ApiModelProperty(value = "课程名称")
    private String courseName;
}
