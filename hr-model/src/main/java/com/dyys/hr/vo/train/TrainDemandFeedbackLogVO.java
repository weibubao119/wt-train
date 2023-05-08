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
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "需求反馈操作日志VO")
public class TrainDemandFeedbackLogVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "主键ID")
    private Long id;
    /**
     * 反馈记录ID
     */
    private String feedbackId;
    /**
     *类型 1.取消 2.回退
     */
    private Integer type;
    /**
     * 操作原因
     */
    private String reason;
    /**
     * 操作人工号
     */
    private String createUser;
    /**
     * 操作人姓名
     */
    private String createUserName;
    /**
     * 创建时间
     */
    private String createTime;


}
