package com.dyys.hr.entity.train;

import com.dagongma.kernel.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "需求反馈操作日志表")
@Table(name = "train_demand_feedback_log")
public class TrainDemandFeedbackLog extends BaseEntity<Long> {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键")
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
     * 创建人
     */
    private String createUser;
    /**
     * 创建时间
     */
    private Long createTime;
}