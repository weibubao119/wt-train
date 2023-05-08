package com.dyys.hr.vo.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 职族表
 * @author yangye
 * @since 1.0.0 2022-09-05
 */
@Data
@ApiModel(value = "职族表")
public class PsProfessionVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "职族编码")
    private String professionCode;

    @ApiModelProperty(value = "职族名称")
    private String professionName;
}
