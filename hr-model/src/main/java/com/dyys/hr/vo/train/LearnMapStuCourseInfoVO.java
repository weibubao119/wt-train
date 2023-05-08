package com.dyys.hr.vo.train;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Desc
 * Create by yangye
 * Date 2022/9/8 16:17
 */
@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "学员推荐课程相关信息")
public class LearnMapStuCourseInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("职级编码名称")
    private String gradeCodeName;

    @ApiModelProperty("推荐课程完成比例")
    private String finishRatio;

    @ApiModelProperty("推荐课程完成率")
    private String finishRate;

    @ApiModelProperty("推荐课程列表")
    private List<LearnMapStuCourseVO> courseList;
}
