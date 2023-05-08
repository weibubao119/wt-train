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
@ApiModel(value = "数据统计占比vo")
public class StatisticalProportionVo implements Serializable {
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "数量")
    private Integer num;
    @ApiModelProperty(value = "占比")
    private String rate;
}
