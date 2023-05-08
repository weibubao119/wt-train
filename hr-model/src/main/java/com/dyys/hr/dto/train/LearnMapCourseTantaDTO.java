package com.dyys.hr.dto.train;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotBlank;

/**
 * Desc
 * Create by yangye
 * Date 2022/9/7 9:25
 */
@Data
@ApiModel(value = "学习地图同等课程传输参数")
public class LearnMapCourseTantaDTO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "同等课程编号")
    @NotBlank(message = "请选择同等课程",groups = {LearnMapCourseDTO.Insert.class, LearnMapCourseDTO.Update.class})
    private String tantaCourseNumber;
}
