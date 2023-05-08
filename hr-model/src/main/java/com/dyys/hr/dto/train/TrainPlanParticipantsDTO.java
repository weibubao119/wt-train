package com.dyys.hr.dto.train;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * 计划参训人员dto
 *
 * @author sie sie
 * @since 1.0.0 2022-04-28
 */
@Data
@ApiModel(value = "计划参训人员dto")
public class TrainPlanParticipantsDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "参训人员ID")
	@NotBlank(message = "参训人员ID不能为空", groups = {TrainPlanDTO.Insert.class,TrainPlanDTO.Update.class})
	private String userId;

	@ApiModelProperty(value = "公司编码")
	@NotNull(message = "公司编码不能为空", groups = {TrainPlanDTO.Insert.class,TrainPlanDTO.Update.class})
	private String companyCode;

	@ApiModelProperty(value = "部门编码")
	@NotNull(message = "部门编码不能为空", groups = {TrainPlanDTO.Insert.class,TrainPlanDTO.Update.class})
	private String departmentCode;

	@ApiModelProperty(value = "岗位编码")
	@NotNull(message = "岗位编码不能为空", groups = {TrainPlanDTO.Insert.class,TrainPlanDTO.Update.class})
	private String jobCode;
}