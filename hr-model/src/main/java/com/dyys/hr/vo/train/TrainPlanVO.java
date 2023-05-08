package com.dyys.hr.vo.train;

import com.dyys.hr.entity.train.TrainPlan;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "计划管理-计划分页vo")
public class TrainPlanVO extends TrainPlan {
    @ApiModelProperty(value = "计划编号")
    private Long id;

    @ApiModelProperty(value = "计划标题")
    private String title;

    @ApiModelProperty(value = "计划年度")
    private String planYear;

    @ApiModelProperty(value = "公司编码")
    private String companyCode;

    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @ApiModelProperty(value = "组织(部门)编码")
    private String deptId;

    @ApiModelProperty(value = "组织(部门)名称")
    private String deptPathName;

    @ApiModelProperty(value = "发起人")
    private String initiator;

    @ApiModelProperty(value = "发起人姓名")
    private String initiatorName;

    @ApiModelProperty(value = "状态 0待审批，1已通过，2已驳回 4.暂存")
    private Integer status;

    @ApiModelProperty(value = "状态名称")
    private String statusName;
}
