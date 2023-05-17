package com.dyys.hr.entity.train;

import com.dagongma.kernel.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 
 * 通知表
 */
@Data
@ApiModel(value = "通知表")
@Table(name = "train_notice")
public class TrainNotice extends BaseEntity<Long> {
    /**
     * 主键
     */
    @Id
    private Long id;
    /**
     * 类型表对应id
     */
    private Long typeId;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 通知类型
     1.人员参训 2考试通知 3.课程评估 4.机构评估 10.临时需求审批 11.培训计划审批 12.培训资料学习
     */
    private Integer type;
    /**
     * 状态 0.待处理 1.已处理
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