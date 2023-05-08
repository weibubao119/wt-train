package com.dyys.hr.vo.train;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Desc
 * Create by yangye
 * Date 2022/9/21 10:28
 */
@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "学习地图职级选择列表")
public class LearnMapPosnGradeSelectVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("职级编码")
    private String posnGradeCode;

    @ApiModelProperty("职级编码名称")
    private String posnGradeCodeName;
}
