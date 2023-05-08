package com.dyys.hr.vo.train;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "审批记录vo")
public class TrainApproveVO implements Serializable{
    @ApiModelProperty(value = "序号")
    private Integer sortNum;

    @ApiModelProperty(value = "审批人姓名")
    private String approveEmplName;

    @ApiModelProperty(value = "审批人ID")
    private String approveEmplid;

    @ApiModelProperty(value = "当前审批节点")
    private String nodeName;

    @ApiModelProperty(value = "下一审批节点")
    private String nextNodeName;

    @ApiModelProperty(value = "操作类型 1.审批中 2.已通过 3已驳回")
    private Integer status;

    @ApiModelProperty(value = "审核意见")
    private String reason;

    @ApiModelProperty(value = "送达时间")
    private String createTime;

    @ApiModelProperty(value = "完成时间")
    private String updateTime;
}
