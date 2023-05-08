package com.dyys.hr.entity.train;

import com.dagongma.kernel.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户评估问卷-选择框数据记录表
 *
 * @author sie sie
 * @since 1.0.0 2022-06-20
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Table(name = "questionnaire_check_box")
public class QuestionnaireCheckBox extends BaseEntity<Long> {
	private static final long serialVersionUID = 1L;
	/**
	 * 主键ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/**
	 * 用户问卷主表id
	 */
	private Long questionnaireUserId;
	/**
	 * 选择框标题
	 */
	private String label;
	/**
	 * 定位key
	 */
	private String labelKey;
	/**
	 * 类型
	 * 1.单选
	 * 2.多选
	 */
	private Integer type;
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