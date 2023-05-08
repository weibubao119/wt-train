package com.dyys.hr.entity.train;

import com.dagongma.kernel.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
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
@ApiModel(value = "计划参训人员表")
@Table(name = "train_plan_participants")
public class TrainPlanParticipants extends BaseEntity<Long> {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 培训计划详情ID
     */
    private Long planDetailId;
    /**
     * 参训人员ID
     */
    private String userId;
    /**
     * 公司编码
     */
    private String companyCode;
    /**
     * 部门编码
     */
    private String departmentCode;
    /**
     * 岗位编码
     */
    private String jobCode;
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