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
import java.util.List;


/**
 * 计划dto
 *
 * @author sie sie
 * @since 1.0.0 2022-04-28
 */
@Data
@ApiModel(value = "计划管理-创建dto")
public class TrainPlanDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "计划ID")
	@NotNull(message = "计划ID不能为空", groups = {Update.class})
	private Long id;

//	@ApiModelProperty(value = "审批流ID")
//	@NotNull(message = "审批流ID不能为空",  groups = {Insert.class, Update.class})
//	private Integer approveFlowId;

	@ApiModelProperty(value = "标题")
	@NotBlank(message = "计划标题不能为空", groups = {Insert.class, Update.class})
	private String title;

	@ApiModelProperty(value = "公司code")
	@NotBlank(message = "公司不能为空", groups = {Insert.class, Update.class})
	private String companyCode;

	@ApiModelProperty(value = "部门编码")
	@NotBlank(message = "部门编码不能为空", groups = {Insert.class, Update.class})
	private String deptId;

	@ApiModelProperty(value = "发起人ID")
	@NotBlank(message = "发起人不能为空", groups = {Insert.class, Update.class})
	private String initiator;

	@ApiModelProperty(value = "计划年度")
	@NotBlank(message = "计划年度不能为空", groups = {Insert.class, Update.class})
	private String planYear;

	@ApiModelProperty(value = "附件列表")
	@Valid
	private List<FileDTO> fileList;

	@ApiModelProperty(value = "计划详情列表")
	@Valid
	@NotEmpty(message = "计划详情列表", groups = {Insert.class, Update.class})
	private List<TrainPlanDetailDTO> planDetailList;

	@ApiIgnore
	public interface Insert{}
	@ApiIgnore
	public interface Update{}
}