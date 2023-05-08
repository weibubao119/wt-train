package com.dyys.hr.entity.train;

import com.dagongma.kernel.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 培训-审批流配置表
 *
 * @author sie sie
 * @since 1.0.0 2023-02-20
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Table(name = "train_approve_flow")
public class TrainApproveFlow extends BaseEntity<Integer> {
	/**
	 * 主键ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private Integer id;
    /**
     * 审批流名称
     */
	private String name;
    /**
     * 所属公司编码
     */
	private String companyCode;
	/**
	 * 适用组织编码
	 */
	private String deptId;
    /**
     * 状态 1.生效 2.失效
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