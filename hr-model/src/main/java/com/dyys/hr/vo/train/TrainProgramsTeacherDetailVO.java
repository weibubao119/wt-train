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
@ApiModel(value = "培训项目讲师列表VO")
public class TrainProgramsTeacherDetailVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "讲师ID")
    private Long id;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "性别 F.男 M.女")
    private String sex;

    @ApiModelProperty(value = "文化程度")
    private String educationLevel;

    @ApiModelProperty(value = "讲师等级常量id ")
    private Integer level;

    @ApiModelProperty(value = "讲师等级名称 ")
    private String levelName;

    @ApiModelProperty(value = "类别 1.内部讲师 2.外部讲师")
    private Integer type;

    @ApiModelProperty(value = "类别名称")
    private String typeName;

    @ApiModelProperty(value = "所属公司/机构")
    private String organizationName;

    @ApiModelProperty(value = "联系电话")
    private String contactPhone;
}
