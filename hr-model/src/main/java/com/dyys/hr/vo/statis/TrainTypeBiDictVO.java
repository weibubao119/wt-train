package com.dyys.hr.vo.statis;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Desc
 * Create by yangye
 * Date 2023/4/19 10:06
 */
@Data
public class TrainTypeBiDictVO {
    @ApiModelProperty(value = "字典类型名")
    private String typeName;

    @ApiModelProperty(value = "字典类型值")
    private String typeVal;

    @ApiModelProperty(value = "字典类型值名称")
    private String valName;

    @ApiModelProperty(value = "备注")
    private String memo;
}
