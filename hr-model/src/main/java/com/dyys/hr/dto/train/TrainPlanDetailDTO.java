package com.dyys.hr.dto.train;

import com.dyys.hr.vo.common.OrgDeptVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@ApiModel(value = "计划详情dto")
public class TrainPlanDetailDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "计划详情ID")
	private Long id;

	@ApiModelProperty(value = "计划id")
	private Long planId;

	@ApiModelProperty(value = "公司编码")
	@NotBlank(message = "公司编码编码不能为空", groups = {TrainPlanDTO.Insert.class,TrainPlanDTO.Update.class})
	private String companyCode;

	@ApiModelProperty(value = "组织单位(部门)编码")
	@NotBlank(message = "组织单位编码不能为空", groups = {TrainPlanDTO.Insert.class,TrainPlanDTO.Update.class})
	private String departmentCode;

	@ApiModelProperty(value = "反馈人员id")
	@NotBlank(message = "反馈人员不能为空", groups = {TrainPlanDTO.Insert.class,TrainPlanDTO.Update.class})
	private String feedbackUserId;

	@ApiModelProperty(value = "需求类型")
	private Integer demandType;

	@ApiModelProperty(value = "培训名称")
	@NotBlank(message = "培训名称不能为空", groups = {TrainPlanDTO.Insert.class,TrainPlanDTO.Update.class})
	private String trainName;

	@ApiModelProperty(value = "课程类别常量id")
	@NotNull(message = "课程类别常量id不能为空", groups = {TrainPlanDTO.Insert.class,TrainPlanDTO.Update.class})
	private Integer courseType;

	@ApiModelProperty(value = "培训形式 1.内部 2.外部")
	@NotNull(message = "培训形式不能为空", groups = {TrainPlanDTO.Insert.class,TrainPlanDTO.Update.class})
	private Integer trainShape;

	@ApiModelProperty(value = "受训对象")
	@NotBlank(message = "受训对象不能为空", groups = {TrainPlanDTO.Insert.class,TrainPlanDTO.Update.class})
	private String trainSubject;

	@ApiModelProperty(value = "培训完成时间")
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
	private Date trainCompleteTime;

	@ApiModelProperty(value = "培训需求依据常量id ")
	private Integer trainRequirements;

	@ApiModelProperty(value = "参训人数")
	@NotNull(message = "参训人数不能为空", groups = {TrainPlanDTO.Insert.class,TrainPlanDTO.Update.class})
	private Integer participantsNum;

	@ApiModelProperty(value = "讲师id")
	private Long teacherId;

	@ApiModelProperty(value = "考核方法常量id ")
	private Integer assessmentMethod;

	@ApiModelProperty(value = "经费预算")
//	@NotNull(message = "经费预算不能为空", groups = {TrainPlanDTO.Insert.class,TrainPlanDTO.Update.class})
	private BigDecimal outlay;

	@ApiModelProperty(value = "备注")
	private String remark;

	@ApiModelProperty(value = "协办单位")
	private List<OrgDeptVO> coOrganizerList;

	@ApiModelProperty(value = "参训人员列表")
	/*@Valid
	@NotEmpty(message = "参训人员列表不能为空", groups = {TrainPlanDTO.Insert.class,TrainPlanDTO.Update.class})*/
	private List<TrainPlanParticipantsDTO> participantsList;
}