package com.dyys.hr.dto.train;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 需求管理-需求信息表
 *
 * @author sie sie
 * @since 1.0.0 2022-04-28
 */
@Data
@ApiModel(value = "需求管理-需求信息dto")
public class TrainDemandCollectDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "需求ID")
	@NotNull(message = "需求ID不能为空", groups = {Update.class})
	private Long id;

	@ApiModelProperty(value = "标题")
	@NotBlank(message = "需求标题不能为空", groups = {Insert.class,Update.class})
	private String demandName;

	@ApiModelProperty(value = "公司code")
	@NotBlank(message = "公司code不能为空", groups = {Insert.class,Update.class})
	private String companyCode;

	@ApiModelProperty(value = "部门code")
	@NotBlank(message = "部门code不能为空", groups = {Insert.class,Update.class})
	private String departmentCode;

	@ApiModelProperty(value = "发起人ID")
	@NotNull(message = "需求ID不能为空", groups = {Insert.class,Update.class})
	private String initiator;

	@ApiModelProperty(value = "发起时间")
	private Date initiationTime;

	@ApiModelProperty(value = "需求说明")
	@NotBlank(message = "需求说明不能为空", groups = {Insert.class,Update.class})
	private String demandDescription;

	@ApiModelProperty(value = "附件文件列表")
	@Valid
	private List<FileDTO> fileList;

	@ApiModelProperty(value = "反馈开始时间")
	private Date feedbackStartTime;

	@ApiModelProperty(value = "反馈结束时间")
	private Date feedbackEndTime;

	@ApiModelProperty(value = "需求反馈人列表")
	@Valid
	@NotEmpty(message = "需求反馈人列表", groups = {Insert.class,Update.class})
	private List<TrainDemandFeedbackDTO> feedbackUserList;

	@ApiIgnore
	public interface Insert{}
	@ApiIgnore
	public interface Update{}
}