package com.dyys.hr.vo.train;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "数据统计占比vo列表")
public class StatisticalProportionListVo implements Serializable {
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "占比列表")
    private List<StatisticalProportionVo> proportionList;
}
