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
import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel(value = "临时需求信息表")
@Table(name = "train_temporary_demand")
public class TrainTemporaryDemand extends BaseEntity<Long> {
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
     * 公司code
     */
    private String companyCode;
    /**
     * 部门code
     */
    private String departmentCode;
    /**
     * 发起人ID
     */
    private String initiator;
    /**
     * 发起时间
     */
    private Date initiationTime;
    /**
     * 培训目的
     */
    private String trainingPurpose;
    /**
     * 附件列表
     */
    private String fileList;
    /**
     * 计划id
     */
    private Long planId;
    /**
     *状态
     *1.审批中
     *2.已通过
     *3.已拒绝
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