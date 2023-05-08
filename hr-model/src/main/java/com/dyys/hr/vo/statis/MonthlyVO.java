package com.dyys.hr.vo.statis;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Desc
 * Create by yangye
 * Date 2023/3/22 10:29
 */
@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "某个组织培训项目总数分布趋势VO")
public class MonthlyVO {

    @ApiModelProperty(value = "月份")
    private String monthly;

    @ApiModelProperty(value = "每月统计值")
    private Integer monthlyNum;
}
