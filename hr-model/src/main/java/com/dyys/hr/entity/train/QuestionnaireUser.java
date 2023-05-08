package com.dyys.hr.entity.train;

import com.dagongma.kernel.core.entity.BaseEntity;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 评估问卷-用户填写问卷记录表
 *
 * @author sie sie
 * @since 1.0.0 2022-06-26
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Table(name = "questionnaire_user")
public class QuestionnaireUser extends BaseEntity<Long> {
	private static final long serialVersionUID = 1L;
	/**
	 * 主键ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/**
	 * 评估类型：1培训评估，2机构评估
	 */
	private Integer type;
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 培训评估id/机构评估ID
	 */
	private Long trainAppraiseId;
	/**
	 * 问卷模板id
	 */
	private Long questionnaireId;
	/**
	 * 状态 1.已填写 0.未填写
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