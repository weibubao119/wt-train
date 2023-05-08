package com.dyys.hr.vo.train;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "培训项目审批流分页VO")
public class TrainApproveFlowPageVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "序号")
    private Integer id;

    @ApiModelProperty(value = "审批流名称")
    private String name;

    @ApiModelProperty(value = "组织(部门)编码")
    private String deptId;

    @ApiModelProperty(value = "适用组织")
    private String deptPathName;

    @ApiModelProperty(value = "状态 1.生效 2.失效")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date createTime;
}
