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
@ApiModel(value = "讲师可授课列表vo")
public class TeacherCanTeachingRecordsVO implements Serializable {
    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "课程编号")
    private String number;

    @ApiModelProperty(value = "课程名称")
    private String name;

    @ApiModelProperty(value = "课程类别常量id")
    private Integer category;

    @ApiModelProperty(value = "课程类别名称")
    private String categoryName;

    @ApiModelProperty(value = "课程简介")
    private String instructions;

    @ApiModelProperty(value = "课时数")
    private Integer classHours;
}
