package com.dyys.hr.entity.train;

import com.dagongma.kernel.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 评估问卷-主表
 *
 * @author sie sie
 * @since 1.0.0 2022-06-20
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Table(name = "questionnaire")
public class Questionnaire extends BaseEntity<Long> {
	private static final long serialVersionUID = 1L;
	/**
	 * 主键ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    /**
     * 问卷标题
     */
	private String title;
	/**
	 * 问卷编号
	 */
	private String code;
    /**
     * 问卷说明
     */
	private String instructions;
    /**
     * 状态 1.已发布 0.未发布
     */
	private Integer status;
	/**
	 * 模板json
	 */
	private String templateJson;
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