package com.dyys.hr.vo.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 员工相关字典表
 * @author yangye
 * @since 1.0.0 2022-09-05
 */
@Data
@ApiModel(value = "员工相关字典表")
public class PsDictVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编码")
    private String dictCode;

    @ApiModelProperty(value = "名称")
    private String dictName;
}
