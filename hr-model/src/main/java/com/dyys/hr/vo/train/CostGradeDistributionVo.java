package com.dyys.hr.vo.train;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "培训费用职级/职级分类占比vo")
public class CostGradeDistributionVo implements Serializable {
    @ApiModelProperty(value = "职级/职级分类名称")
    private String name;
    @ApiModelProperty(value = "人次")
    private Integer gradeNum;
    @ApiModelProperty(value = "培训费用(万元)")
    private double totalCost;
    @ApiModelProperty(value = "人均费用(元)")
    private double averageCost;
    @ApiModelProperty(value = "占比")
    private String rate;
}
