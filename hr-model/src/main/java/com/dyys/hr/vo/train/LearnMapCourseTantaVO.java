package com.dyys.hr.vo.train;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Desc
 * Create by yangye
 * Date 2022/9/7 10:24
 */
@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "学习地图同等课程详情")
public class LearnMapCourseTantaVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("所属地图课程记录ID")
    private Long mapCourseId;

    @ApiModelProperty("同等课程编号")
    private String tantaCourseNumber;

    @ApiModelProperty("同等课程名称")
    private String tantaCourseName;

    @ApiModelProperty("同等课程类型ID")
    private String tantaCourseCate;

    @ApiModelProperty("同等课程类型名称")
    private String tantaCourseCateName;
}
