package com.dyys.hr.dto.train;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;


/**
 * 过程管理-培训项目考勤记录表
 *
 * @author sie sie
 * @since 1.0.0 2022-06-06
 */
@Data
@ApiModel(value = "过程管理-培训项目考勤记录dto")
public class TrainAttendanceRecordDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键")
	private Long id;

	@ApiModelProperty(value = "考勤规则id")
	private Long attendanceRulesId;

	@ApiModelProperty(value = "考勤人员ID")
	private String userId;

	@ApiModelProperty(value = "签到时间")
	private Time signedInTime;

	@ApiModelProperty(value = "状态 0.未签到 1.已签到 2.迟到签到")
	private Integer status;

	@ApiModelProperty(value = "创建人")
	private String createUser;

	@ApiModelProperty(value = "创建时间")
	private Long createTime;

	@ApiModelProperty(value = "更新人")
	private String updateUser;

	@ApiModelProperty(value = "更新时间")
	private Long updateTime;


}