package com.dyys.hr.vo.train;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "培训项目基础档案类型数据VO")
public class TrainConstantTypeVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "档案类型")
    private Integer type;

    @ApiModelProperty(value = "档案类型名称")
    private String name;

}
