package com.dyys.hr.entity.train;

import com.dagongma.kernel.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 培训-审批记录表
 *
 * @author sie sie
 * @since 1.0.0 2023-02-17
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Table(name = "train_approve")
public class TrainApprove extends BaseEntity<Long> {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    /**
     * 类型 1.培训计划  2.临时需求
     */
	private Integer type;
    /**
     * 类型数据记录id
     */
	private Long typeId;
    /**
     * 审批人工号
     */
	private String approveEmplid;
    /**
     * 审批节点名称
     */
	private String nodeName;
    /**
     * 审批人顺序
     */
	private Integer sortNum;
    /**
     * 状态 1.审批中 2.已通过 3已驳回
     */
	private Integer status;
    /**
     * 审批意见
     */
	private String reason;
	/**
	 * 是否历史审批 0:否 大于0:是
	 */
	private Integer isHistory;
    /**
     * 创建时间
     */
	private Long createTime;
    /**
     * 创建人
     */
	private String createUser;
    /**
     * 修改时间
     */
	private Long updateTime;
    /**
     * 修改人
     */
	private String updateUser;
}