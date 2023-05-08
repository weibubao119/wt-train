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
@ApiModel(value = "课程管理")
public class TrainBaseCourseVO implements Serializable {
    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "课程编号")
    private String number;

    @ApiModelProperty(value = "课程名称")
    private String name;

    @ApiModelProperty(value = "课程类别常量id ")
    private Integer category;

    @ApiModelProperty(value = "课程类别名称 ")
    private String categoryName;

    @ApiModelProperty(value = "课时数")
    private Integer classHours;

    @ApiModelProperty(value = "课程来源 1.外部 2.自有")
    private Integer courseSource;

    @ApiModelProperty(value = "状态 1.生效 0.失效")
    private Integer status;
}
