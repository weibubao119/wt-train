package com.dyys.hr.vo.train;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Desc
 * Create by yangye
 * Date 2022/9/6 17:43
 */
@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "学习地图详情职级列表")
public class LearnMapPosnGradeVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("职级编码")
    private String posnGradeCode;

    @ApiModelProperty("职级名称")
    private String posnGradeName;

    @ApiModelProperty("高一级职级编码")
    private String pPosnGradeCode;

    @ApiModelProperty("高一级职级名称")
    private String pPosnGradeName;
}
