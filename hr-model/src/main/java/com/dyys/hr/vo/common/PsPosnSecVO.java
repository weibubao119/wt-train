package com.dyys.hr.vo.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 职序表
 * @author yangye
 * @since 1.0.0 2022-09-05
 */
@Data
@ApiModel(value = "职序表")
public class PsPosnSecVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "职序编码")
    private String posnSecCode;

    @ApiModelProperty(value = "职序名称")
    private String posnSecName;
}
