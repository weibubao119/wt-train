package com.dyys.hr.dto.train;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

import java.math.BigDecimal;

/**
 * 需求管理-需求反馈信息表
 *
 * @author sie sie
 * @since 1.0.0 2022-04-28
 */
@Data
@ApiModel(value = "需求管理-需求反馈信息表")
public class TrainDemandFeedbackDetailDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键")
	@NotNull(message = "反馈详情ID不能为空", groups = {Update.class})
	private Long id;

	@ApiModelProperty(value = "需求ID")
	@NotNull(message = "需求ID不能为空", groups = {Insert.class,Update.class})
	private Long demandId;

	@ApiModelProperty(value = "反馈人员Id")
	@NotNull(message = "反馈人员ID不能为空", groups = {Insert.class,Update.class})
	private String feedbackUserId;

	@ApiModelProperty(value = "培训名称")
	@NotBlank(message = "培训名称不能为空", groups = {Insert.class,Update.class})
	private String trainName;

	@ApiModelProperty(value = "培训形式 1.内部 2.外部")
	@NotNull(message = "培训形式不能为空", groups = {Insert.class,Update.class})
	private Integer trainShape;

	@ApiModelProperty(value = "课程类别")
	@NotNull(message = "课程类别不能为空", groups = {Insert.class,Update.class})
	private Integer courseType;

	@ApiModelProperty(value = "受训对象")
	@NotBlank(message = "受训对象不能为空", groups = {Insert.class,Update.class})
	private String trainSubject;

	@ApiModelProperty(value = "拟完成时间")
	@JsonFormat(pattern="yyyy-MM-dd")
	@NotNull(message = "拟完成时间不能为空", groups = {Insert.class,Update.class})
	private Date trainCompleteTime;

	@ApiModelProperty(value = "培训需求依据常量id ")
	@NotNull(message = "培训需求依据常量id不能为空", groups = {Insert.class,Update.class})
	@Min(value = 1, message = "培训需求依据常量id必须大于0", groups = {Insert.class,Update.class})
	private Integer trainRequirements;

	@ApiModelProperty(value = "参训人数")
	@NotNull(message = "参训人数不能为空", groups = {Insert.class,Update.class})
	@Min(value = 1, message = "参训人数必须大于0", groups = {Insert.class,Update.class})
	private Integer participantsNum;

	@ApiModelProperty(value = "讲师id")
	private Long teacherId;

	@ApiModelProperty(value = "考核方法常量id ")
	private Integer assessmentMethod;

	@ApiModelProperty(value = "经费预算")
	@NotNull(message = "经费预算不能为空", groups = {Insert.class,Update.class})
	private BigDecimal outlay;

	@ApiModelProperty(value = "备注")
	private String remark;

	@ApiIgnore
	public interface Insert{}
	@ApiIgnore
	public interface Update{}

}