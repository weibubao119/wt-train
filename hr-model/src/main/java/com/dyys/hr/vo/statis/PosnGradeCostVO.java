package com.dyys.hr.vo.statis;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Desc
 * Create by yangye
 * Date 2023/4/10 9:21
 */
@Data
public class PosnGradeCostVO {
    @ApiModelProperty(value = "职级编码")
    private String posnGradeCode;

    @ApiModelProperty(value = "职级名称")
    private String posnGradeName;

    @ApiModelProperty(value = "人次")
    private String personTime;

    @ApiModelProperty(value = "费用(元)")
    private String totalFee;

    @ApiModelProperty(value = "人均费用(元)")
    private String avgFee;

    @ApiModelProperty(value = "费用占比")
    private String statisRatio;
}
