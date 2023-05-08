package com.dyys.hr.vo.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 职级表
 * @author yangye
 * @since 1.0.0 2022-09-05
 */
@Data
@ApiModel(value = "职级表")
public class PsPosnGradeVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "职级编码")
    private String posnGradeCode;

    @ApiModelProperty(value = "职级名称")
    private String posnGradeName;
}
