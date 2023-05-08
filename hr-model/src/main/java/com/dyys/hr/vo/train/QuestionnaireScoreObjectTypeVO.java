package com.dyys.hr.vo.train;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "评估问卷评分表评分对象数据VO")
public class QuestionnaireScoreObjectTypeVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "评分对象类型")
    private Integer type;

    @ApiModelProperty(value = "评分对象名称")
    private String name;

}
