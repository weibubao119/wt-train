package com.dyys.hr.vo.train;

import com.dyys.hr.entity.train.TrainDemandCollect;
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
@ApiModel(value = "关联需求列表")
public class TrainRelateDemandCollectVO extends TrainDemandCollect {
    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "标题")
    private String demandName;

    @ApiModelProperty(value = "公司编码")
    private String companyCode;

    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @ApiModelProperty(value = "部门编码")
    private String departmentCode;

    @ApiModelProperty(value = "部门名称")
    private String departmentName;

    @ApiModelProperty(value = "发起人Id")
    private String initiator;

    @ApiModelProperty(value = "发起人姓名")
    private String initiatorName;
}
