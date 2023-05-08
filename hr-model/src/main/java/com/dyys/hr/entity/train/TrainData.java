package com.dyys.hr.entity.train;

import com.dagongma.kernel.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 培训集合数据表
 *
 * @author sie sie
 * @since 1.0.0 2022-09-01
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Table(name = "train_data")
public class TrainData extends BaseEntity<Long> {
	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/**
	 * 员工编号
	 */
	private String emplId;
	/**
	 * 员工姓名
	 */
	private String emplName;
	/**
	 * 项目编号
	 */
	private String trainNumber;
	/**
	 * 项目名称
	 */
	private String trainName;
	/**
	 * 组织单位(公司)编号
	 */
	private String companyCode;
	/**
	 * 组织单位(部门)编号
	 */
	private String deptId;
	/**
	 * 培训形式
	 1.内部
	 2.外部
	 */
	private Integer trainShape;
	/**
	 * 项目开始时间
	 */
	private Date startTime;
	/**
	 * 项目结束时间
	 */
	private Date endTime;
	/**
	 * 项目实际参训人数
	 */
	private Integer participantsNum;
	/**
	 * 项目使用经费
	 */
	private String outlay;
	/**
	 * 个人费用
	 */
	private String avgFee;
	/**
	 * 负责人工号
	 */
	private String principalId;
	/**
	 * 负责人姓名
	 */
	private String principalName;
	/**
	 * 项目总成绩
	 */
	private String totalScore;
	/**
	 * 是否获证 0.否 1.是
	 */
	private Integer isObtain;
	/**
	 * 课程编号
	 */
	private String courseNumber;
	/**
	 * 课程名称
	 */
	private String courseName;
	/**
	 *
	 课程类别常量id
	 */
	private Integer courseCategory;
	/**
	 * 课时数
	 */
	private BigDecimal courseClassHours;
	/**
	 * 课程简介
	 */
	private String courseInstructions;
	/**
	 * 课程学分
	 */
	private BigDecimal courseCredit;
	/**
	 * 课程开始时间
	 */
	private Date courseStartTime;
	/**
	 * 课程结束时间
	 */
	private Date courseEndTime;
	/**
	 * 学习形式
	 */
	private Integer learningForm;
	/**
	 * 考核形式
	 */
	private Integer examineForm;
	/**
	 * 课程成绩
	 */
	private String courseScore;
	/**
	 * 讲师编号
	 */
	private String teacherNumber;
	/**
	 * 讲师姓名
	 */
	private String teacherName;
	/**
	 * 讲师类别
	 1.内部讲师
	 2.外部讲师
	 */
	private Integer teacherType;
	/**
	 * 讲师性别
	 F.女
	 M.男
	 */
	private String teacherSex;
	/**
	 * 讲师联系电话
	 */
	private String teacherContactPhone;
	/**
	 * 讲师邮箱地址
	 */
	private String teacherEmail;
	/**
	 * 讲师所属机构名称
	 */
	private String teacherOrganizationName;
	/**
	 * 结业条件
	 */
	private String finishType;
	/**
	 * 考试是否
	 通过0.否
	 1.是
	 */
	private Integer isPass;
	/**
	 * 考试成绩
	 */
	private String examScore;
	/**
	 * 获得学分
	 */
	private BigDecimal credit;
	/**
	 * 获取学分时间
	 */
	private Date getCreditTime;
	/**
	 * 数据类型 1.e-learning 2.历史数据
	 */
	private Integer type;
	/**
	 * 数据状态 0.未处理 1.已处理
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