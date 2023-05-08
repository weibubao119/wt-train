package com.dyys.hr.dto.train;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * 过程管理-培训考试人员dto
 *
 * @author sie sie
 * @since 1.0.0 2022-05-18
 */
@Data
@ApiModel(value = "过程管理-培训参评人员")
public class TrainAppraisePersonDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "参评人员ID")
	@NotBlank(message = "参评人员ID不能为空", groups = {TrainAppraiseDTO.Insert.class,TrainAppraiseDTO.Update.class})
	private String userId;

	@ApiModelProperty(value = "公司编码")
	@NotNull(message = "公司编码不能为空", groups = {TrainAppraiseDTO.Insert.class,TrainAppraiseDTO.Update.class})
	private String companyCode;

	@ApiModelProperty(value = "部门编码")
	@NotNull(message = "部门编码不能为空", groups = {TrainAppraiseDTO.Insert.class,TrainAppraiseDTO.Update.class})
	private String departmentCode;

	@ApiModelProperty(value = "岗位编码")
	@NotNull(message = "岗位编码不能为空", groups = {TrainAppraiseDTO.Insert.class,TrainAppraiseDTO.Update.class})
	private String jobCode;
}