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
@ApiModel(value = "按月份数据统计vo")
public class MonthDataVo implements Serializable {
    @ApiModelProperty(value = "一月数据")
    private Integer janNum;
    @ApiModelProperty(value = "二月数据")
    private Integer febNum;
    @ApiModelProperty(value = "三月数据")
    private Integer marNum;
    @ApiModelProperty(value = "四月数据")
    private Integer aprNum;
    @ApiModelProperty(value = "五月数据")
    private Integer mayNum;
    @ApiModelProperty(value = "六月数据")
    private Integer junNum;
    @ApiModelProperty(value = "七月数据")
    private Integer julNum;
    @ApiModelProperty(value = "八月数据")
    private Integer augNum;
    @ApiModelProperty(value = "九月数据")
    private Integer sepNum;
    @ApiModelProperty(value = "十月数据")
    private Integer octNum;
    @ApiModelProperty(value = "十一月数据")
    private Integer novNum;
    @ApiModelProperty(value = "十二月数据")
    private Integer decNum;
}
