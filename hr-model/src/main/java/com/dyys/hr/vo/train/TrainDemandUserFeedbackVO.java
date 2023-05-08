package com.dyys.hr.vo.train;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wangyunzhen
 * @date 2019/6/19 22:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "反馈人接收前端参数VO")
public class TrainDemandUserFeedbackVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "需求ID")
    private Long id;

    @ApiModelProperty(value = "需求名称")
    private String demandName;

    @ApiModelProperty(value = "发起人id")
    private String initiator;

    @ApiModelProperty(value = "发起人姓名")
    private String initiatorName;

    @ApiModelProperty(value = "反馈截止时间")
    private Date feedbackEndTime;

    @ApiModelProperty(value = "反馈结果状态")
    private Integer status;

    @ApiModelProperty(value = "反馈结果状态说明")
    private String statusName;
}
