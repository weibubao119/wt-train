package com.dyys.hr.vo.train;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * 计划参训人员dto
 *
 * @author sie sie
 * @since 1.0.0 2022-04-28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "计划参训表")
public class TrainPlanParticipantsVO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "参训人员ID")
	private String userId;

	@ApiModelProperty(value = "参训人员名称")
	private String userName;

	@ApiModelProperty(value = "所属单位编码")
	private String companyCode;

	@ApiModelProperty(value = "所属单位名称")
	private String companyName;

	@ApiModelProperty(value = "所属部门编码")
	private String departmentCode;

	@ApiModelProperty(value = "所属部门编码")
	private String departmentName;

	@ApiModelProperty(value = "岗位code")
	private String jobCode;

	@ApiModelProperty(value = "岗位名称")
	private String jobName;

}