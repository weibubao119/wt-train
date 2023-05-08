package com.dyys.hr.entity.train;

import com.dagongma.kernel.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 过程管理-培训项目考勤记录表
 *
 * @author sie sie
 * @since 1.0.0 2022-06-06
 */
@Data
@ApiModel(value = "培训项目考勤记录表")
@Table(name = "train_attendance_record")
public class TrainAttendanceRecord extends BaseEntity<Long> {
	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    /**
     * 考勤规则id
     */
	private Long attendanceRulesId;
    /**
     * 考勤人员ID
     */
	private String emplId;
    /**
     * 签到时间
     */
	private String signedInTime;
    /**
     * 状态 0.未开始 1.已签到 2.迟到签到 3.未签到
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