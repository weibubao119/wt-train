package com.dyys.hr.entity.train;

import com.dagongma.kernel.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@ApiModel(value = "培训评估人员表")
@Table(name = "train_appraise_person")
public class TrainAppraisePerson extends BaseEntity<Long> {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 评估ID
     */
    private Long appraiseId;
    /**
     * 考试人ID
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
     * 状态 0.待提交 1.已完成
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