package com.dyys.hr.vo.train;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Desc
 * Create by yangye
 * Date 2022/9/21 10:30
 */
@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "学习地图学习方向选择列表")
public class LearnMapSblSelctVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("学习方向ID")
    private String sblId;

    @ApiModelProperty("学习方向名称")
    private String sblName;
}
