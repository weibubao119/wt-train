package com.dyys.hr.vo.train;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Desc
 * Create by yangye
 * Date 2022/9/6 17:39
 */
@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "学习地图详情")
public class LearnMapInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("地图ID")
    private Long id;

    @ApiModelProperty("地图编号")
    private String mapCode;

    @ApiModelProperty("地图名称")
    private String mapName;

    @ApiModelProperty("职序编码")
    private String posnSecCode;

    @ApiModelProperty("职序名称")
    private String posnSecName;

    @ApiModelProperty("职级列表")
    private List<LearnMapPosnGradeVO> posnGradeList;
}
