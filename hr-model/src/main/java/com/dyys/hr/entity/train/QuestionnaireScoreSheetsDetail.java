package com.dyys.hr.entity.train;

import com.dagongma.kernel.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户评估问卷-评分表详细数据记录表
 *
 * @author sie sie
 * @since 1.0.0 2022-06-28
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Table(name = "questionnaire_score_sheets_detail")
public class QuestionnaireScoreSheetsDetail extends BaseEntity<Long> {
	private static final long serialVersionUID = 1L;
	/**
	 * 主键ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    /**
     * 用户问卷评分记录表id
     */
	private Long sheetsId;
    /**
     * 评分项名称
     */
	private String tableDataName;
    /**
     * 评分项key
     */
	private String tableDataKey;
    /**
     * 打分列名称
     */
	private String tableHeaderName;
    /**
     * 打分列key
     */
	private String tableHeaderKey;
    /**
     * 值
     */
	private String value;
	/**
	 * 打分列分值设定
	 */
	private String headerScoreValue;
	/**
	 * 说明
	 */
	private String descr;
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