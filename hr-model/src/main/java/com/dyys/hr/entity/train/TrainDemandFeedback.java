package com.dyys.hr.entity.train;

import com.dagongma.kernel.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "反馈人员表")
@Table(name = "train_demand_feedback")
public class TrainDemandFeedback extends BaseEntity<Long> {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 需求主键ID
     */
    private Long demandId;
    /**
     * 反馈人ID
     */
    private String feedbackUserId;
    /**
     * 公司编码
     */
    private String companyCode;
    /**
     * 部门编码
     */
    private String departmentCode;
    /**
     * 状态
     0.待反馈
     1.已反馈
     */
    private Integer status;
    /**
     * 创建人
     */
    private String createUser;
    /**
     * 创建时间
     */
    private Long createTime;
    /**
     * 更新人
     */
    private String updateUser;
    /**
     * 更新时间
     */
    private Long updateTime;
}