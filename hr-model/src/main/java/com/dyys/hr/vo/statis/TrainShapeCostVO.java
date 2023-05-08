package com.dyys.hr.vo.statis;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Desc
 * Create by yangye
 * Date 2023/3/29 11:14
 */
@Data
public class TrainShapeCostVO {
    @ApiModelProperty(value = "培训形式代码")
    private String trainShapeId;

    @ApiModelProperty(value = "培训形式名称")
    private String trainShapeName;

    @ApiModelProperty(value = "费用(元)")
    private String totalFee;

    @ApiModelProperty(value = "费用占比")
    private String statisRatio;
}
