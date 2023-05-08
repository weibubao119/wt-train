package com.dyys.hr.dto.train;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * e-learning数据同步dto
 *
 * @author sie sie
 * @since 1.0.0 2022-04-28
 */
@Data
@ApiModel(value = "e-learning数据同步dto")
public class ELearningDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "员工编号")
	@NotNull(message = "员工编号不能为空", groups = {Insert.class})
	private String emplId;

	@ApiModelProperty(value = "员工姓名")
	@NotNull(message = "员工姓名不能为空", groups = {Insert.class})
	private String emplName;

	@ApiModelProperty(value = "项目编号")
	@NotNull(message = "项目编号不能为空", groups = {Insert.class})
	private String trainNumber;

	@ApiModelProperty(value = "项目名称")
	@NotNull(message = "项目名称不能为空", groups = {Insert.class})
	private String trainName;

	@ApiModelProperty(value = "课程编号")
	@NotNull(message = "课程编号不能为空", groups = {ELearningDTO.Insert.class})
	private String courseNumber;

	@ApiModelProperty(value = "课程名称")
	@NotNull(message = "课程名称不能为空", groups = {ELearningDTO.Insert.class})
	private String courseName;

	@ApiModelProperty(value = "课程类别ID")
	@NotNull(message = "课程类别ID不能为空", groups = {ELearningDTO.Insert.class})
	private Integer courseCategory;

	@ApiModelProperty(value = "课程学时")
	@NotNull(message = "课程学时不能为空", groups = {ELearningDTO.Insert.class})
	private BigDecimal courseClassHours;

	@ApiModelProperty(value = "课程学分")
	@NotNull(message = "课程学分不能为空", groups = {ELearningDTO.Insert.class})
	private BigDecimal courseCredit;

	@ApiModelProperty(value = "结业条件")
	@NotNull(message = "结业条件不能为空", groups = {Insert.class})
	private String finishType;

	@ApiModelProperty(value = "考试是否通过 1.是 0.否")
	@NotNull(message = "是否通过不能为空", groups = {Insert.class})
	private Integer isPass;

	@ApiModelProperty(value = "考试成绩")
	@NotNull(message = "考试成绩不能为空", groups = {Insert.class})
	private String examScore;

	@ApiModelProperty(value = "获得学分")
	@NotNull(message = "获得学分不能为空", groups = {Insert.class})
	private BigDecimal credit;

	@ApiModelProperty(value = "获取学分时间")
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	private Date getCreditTime;

	@ApiIgnore
	public interface Insert{}
}