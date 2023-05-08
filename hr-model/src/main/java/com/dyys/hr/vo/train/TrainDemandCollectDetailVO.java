package com.dyys.hr.vo.train;

import com.dyys.hr.dto.train.FileDTO;
import com.dyys.hr.dto.train.TrainDemandFeedbackDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
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
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "需求管理-需求信息表")
public class TrainDemandCollectDetailVO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "需求ID")
	private Long id;

	@ApiModelProperty(value = "标题")
	private String demandName;

	@ApiModelProperty(value = "公司code")
	private String companyCode;

	@ApiModelProperty(value = "部门code")
	private String departmentCode;

	@ApiModelProperty(value = "发起人ID")
	private String initiator;

	@ApiModelProperty(value = "发起人名称")
	private String initiatorName;

	@ApiModelProperty(value = "发起时间")
	private Date initiationTime;

	@ApiModelProperty(value = "状态 0.进行中 1.已完成")
	private Integer status;

	@ApiModelProperty(value = "需求说明")
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
	private List<TrainDemandFeedbackVO> feedbackUserList;

	@ApiIgnore
	public interface Insert{}
	@ApiIgnore
	public interface Update{}
}