package com.dyys.hr.dto.train;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 项目审批流dto
 *
 * @author sie sie
 * @since 1.0.0 2022-04-28
 */
@Data
@ApiModel(value = "项目审批流dto")
public class TrainApproveFlowDTO implements Serializable {

	@ApiModelProperty(value = "审批流ID")
	@NotNull(message = "审批流ID不能为空", groups = {Update.class})
	private Integer id;

	@ApiModelProperty(value = "审批流名称")
	@NotBlank(message = "审批流名称不能为空", groups = {Insert.class,Update.class})
	private String name;

	@ApiModelProperty(value = "所属公司编码")
	@NotBlank(message = "所属公司编码不能为空", groups = {Insert.class,Update.class})
	private String companyCode;

	@ApiModelProperty(value = "适用组织编码")
	@NotBlank(message = "适用组织编码不能为空", groups = {Insert.class,Update.class})
	private String deptId;

	@ApiModelProperty(value = "状态")
	@NotNull(message = "审批流状态不能为空", groups = {Insert.class,Update.class})
	private Integer status;

	@ApiModelProperty(value = "审批流节点列表")
	@NotEmpty(message = "审批流节点列表", groups = {Insert.class, Update.class})
	@Valid
	private List<TrainApproveFlowNodeDTO>  flowNodeList;

	@ApiIgnore
	public interface Insert{}
	@ApiIgnore
	public interface Update{}
}