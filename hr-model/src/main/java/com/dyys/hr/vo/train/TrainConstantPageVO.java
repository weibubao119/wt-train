package com.dyys.hr.vo.train;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "培训项目基础档案分页VO")
public class TrainConstantPageVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "档案Id")
    private Integer id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "上级Id")
    private String pid;

    @ApiModelProperty(value = "上级名称")
    private String parentName;

    @ApiModelProperty(value = "类型：1.课程类别，2.培训-需求依据，3.培训-考核方法，4.讲师-等级，5.机构类型，6.计划类型，7.计划需求类型，8.培训科目，9.机构等级，10.学习形式")
    private Integer type;

    @ApiModelProperty(value = "类型名称")
    private String typeName;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "状态 0.停用 1.启用")
    private Integer status;

    @ApiModelProperty(value = "状态名称")
    private String statusName;
}
