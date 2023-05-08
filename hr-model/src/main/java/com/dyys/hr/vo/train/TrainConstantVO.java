package com.dyys.hr.vo.train;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value="TrainConstantVO",description="基础档案筛选视图对象")
public class TrainConstantVO implements Serializable {
    @ApiModelProperty(value = "配置id")
    private Integer id;

    @ApiModelProperty(value = "配置名称")
    private String name;

}
