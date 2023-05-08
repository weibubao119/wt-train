package com.dyys.hr.vo.train;

import com.dyys.hr.entity.train.TrainDemandFeedbackLog;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "需求反馈人接收前端参数VO")
public class TrainDemandFeedbackVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "反馈人表主键ID")
    private Long id;

    @ApiModelProperty(value = "需求ID")
    private Long demandId;

    @ApiModelProperty(value = "反馈人ID")
    private String feedbackUserId;

    @ApiModelProperty(value = "反馈人姓名")
    private String feedbackUserName;

    @ApiModelProperty(value = "公司code")
    private String companyCode;

    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @ApiModelProperty(value = "部门code")
    private String departmentCode;

    @ApiModelProperty(value = "部门名称")
    private String departmentName;

    @ApiModelProperty(value = "反馈结果状态")
    private Integer status;

    @ApiModelProperty(value = "反馈结果状态说明")
    private String statusName;

    @ApiModelProperty(value = "反馈时间时间戳")
    private Long feedbackTime;

    @ApiModelProperty(value = "反馈时间")
    private String feedbackDate;

    @ApiModelProperty(value = "需求反馈操作日志列表")
    private List<TrainDemandFeedbackLogVO> feedbackLogList;


}
