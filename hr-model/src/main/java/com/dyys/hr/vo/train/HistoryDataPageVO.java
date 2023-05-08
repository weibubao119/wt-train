package com.dyys.hr.vo.train;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "历史数据分页VO")
public class HistoryDataPageVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "序号")
    private Long id;

    @ApiModelProperty(value = "工号")
    private String emplId;

    @ApiModelProperty(value = "姓名")
    private String emplName;

    @ApiModelProperty(value = "项目编号")
    private String trainNumber;

    @ApiModelProperty(value = "项目名称")
    private String trainName;

    @ApiModelProperty(value = "培训形式 1.内部 2.外部")
    private String trainShape;

    @ApiModelProperty(value = "课程编号")
    private String courseNumber;

    @ApiModelProperty(value = "课程名称")
    private String courseName;

    @ApiModelProperty(value = "课程类别ID")
    private Integer courseCategory;

    @ApiModelProperty(value = "课程类别名称")
    private String courseCategoryName;

    @ApiModelProperty(value = "是否取证 1.是 0.否")
    private Integer isObtain;
}
