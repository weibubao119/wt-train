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
@ApiModel(value = "培训讲师")
public class TrainBaseTeacherVO implements Serializable {
    @ApiModelProperty(value = "讲师ID")
    private Integer id;

    @ApiModelProperty(value = "讲师工号/编号")
    private String number;

    @ApiModelProperty(value = "讲师姓名")
    private String name;

    @ApiModelProperty(value = "所属公司/机构名称")
    private String organizationName;

    @ApiModelProperty(value = "讲师等级id ")
    private Integer level;

    @ApiModelProperty(value = "讲师等级名称 ")
    private String levelName;

    @ApiModelProperty(value = "讲师类别")
    private Integer type;

    @ApiModelProperty(value = "讲师类别名称")
    private String typeName;

    @ApiModelProperty(value = "讲师状态")
    private Integer status;

    @ApiModelProperty(value = "讲师状态名称")
    private String statusName;
}
