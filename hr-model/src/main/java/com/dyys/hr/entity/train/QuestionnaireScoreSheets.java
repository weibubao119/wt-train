package com.dyys.hr.entity.train;

import com.dagongma.kernel.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户评估问卷-评分表数据记录表
 *
 * @author sie sie
 * @since 1.0.0 2022-06-20
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Table(name = "questionnaire_score_sheets")
public class QuestionnaireScoreSheets extends BaseEntity<Long> {
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
	 * 评分表标题
	 */
	private String title;
	/**
	 * 标题是否显示
	 */
	private Integer titleShown;
	/**
	 * 评分对象
	 */
	private Integer scoreObject;
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