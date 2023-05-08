package com.dyys.hr.entity.train;

import com.dagongma.kernel.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;

/**
 * 过程管理-培训项目课程表
 *
 * @author sie sie
 * @since 1.0.0 2022-08-25
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Table(name = "train_programs_course")
public class TrainProgramsCourse extends BaseEntity<Long> {
	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    /**
     * 培训项目id
     */
	private Long programsId;
	/**
	 * 课程id
	 */
	private Long courseId;
    /**
     * 课程名称
     */
	private String courseName;
	/**
	 * 上课日期
	 */
	private Date schooltime;
    /**
     * 开始时间
     */
	private String startTime;
    /**
     * 结束时间
     */
	private String endTime;
    /**
     * 场地id
     */
	private Integer siteId;
    /**
     * 培训内容
     */
	private String content;
    /**
     * 讲师id
     */
	private Long teacherId;
	/**
	 * 学时
	 */
	private BigDecimal classHour;
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