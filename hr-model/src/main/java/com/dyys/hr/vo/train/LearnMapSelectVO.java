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
 * Date 2022/9/21 10:20
 */
@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "学习地图选择列表")
public class LearnMapSelectVO implements Serializable {
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

    @ApiModelProperty(value = "学习职级选择列表")
    private List<LearnMapPosnGradeSelectVO> mapGradeList;

    @ApiModelProperty(value = "学习方向选择列表")
    private List<LearnMapSblSelctVO> mapSblList;
}
