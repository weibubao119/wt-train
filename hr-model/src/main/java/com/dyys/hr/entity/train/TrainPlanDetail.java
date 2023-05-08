package com.dyys.hr.entity.train;

import com.dagongma.kernel.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "计划详情表")
@Table(name = "train_plan_detail")
public class TrainPlanDetail extends BaseEntity<Long> {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 计划id
     */
    private Long planId;
    /**
     * 组织单位编码
     */
    private String companyCode;
    /**
     * 部门编码
     */
    private String departmentCode;
    /**
     * 反馈人员id
     */
    private String feedbackUserId;
    /**
     * 需求类型：0计划培训(默认)，1临时需求
     */
    private Integer demandType;
    /**
     * 培训名称
     */
    private String trainName;
    /**
     * 课程类别ID
     */
    private Integer courseType;
    /**
     * 培训形式
     1.内部
     2.外部
     */
    private Integer trainShape;
    /**
     * 受训对象
     */
    private String trainSubject;
    /**
     * 培训完成时间
     */
    private Date trainCompleteTime;
    /**
     * 培训需求依据常量id

     */
    private Integer trainRequirements;
    /**
     * 参训人数
     */
    private Integer participantsNum;
    /**
     * 讲师id
     */
    private Long teacherId;
    /**
     * 考核方法常量id
     */
    private Integer assessmentMethod;
    /**
     * 经费预算
     */
    private BigDecimal outlay;
    /**
     * 备注
     */
    private String remark;
    /**
     * 协办单位
     */
    private String coOrganizer;
    /**
     * 是否显示 1.是 0.否
     */
    private Integer isShow;
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