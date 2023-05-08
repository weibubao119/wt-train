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
@ApiModel(value = "需求反馈数据填写接收前端参数VO")
public class TrainDemandCollectVO extends TrainDemandCollect {
    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "标题")
    private String demandName;

    @ApiModelProperty(value = "公司编码")
    private String companyCode;

    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @ApiModelProperty(value = "组织编码")
    private String departmentCode;

    @ApiModelProperty(value = "组织名称")
    private String deptPathName;

    @ApiModelProperty(value = "需求年度")
    private String year;

    @ApiModelProperty(value = "状态 0.进行中 1.已完成")
    private Integer status;

    @ApiModelProperty(value = "状态名称")
    private String statusName;

    @ApiModelProperty(value = "创建时间")
    private Long createTime;
}
