package com.dyys.hr.dto.train;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 项目审批流节点dto
 *
 * @author sie sie
 * @since 1.0.0 2022-04-28
 */
@Data
@ApiModel(value = "项目审批流节点dto")
public class TrainApproveFlowNodeDTO implements Serializable {
	@ApiModelProperty(value = "节点名称")
	@NotBlank(message = "节点名称不能为空", groups = {TrainApproveFlowDTO.Insert.class, TrainApproveFlowDTO.Update.class})
	private String name;

	@ApiModelProperty(value = "审批人工号")
	@NotBlank(message = "审批人工号不能为空", groups = {TrainApproveFlowDTO.Insert.class, TrainApproveFlowDTO.Update.class})
	private String emplId;

	@ApiModelProperty(value = "审批人姓名")
	@NotBlank(message = "审批人姓名不能为空", groups = {TrainApproveFlowDTO.Insert.class, TrainApproveFlowDTO.Update.class})
	private String emplName;

	@ApiModelProperty(value = "序号")
	@NotNull(message = "序号不能为空", groups = {TrainApproveFlowDTO.Insert.class, TrainApproveFlowDTO.Update.class})
	private Integer sort;
}