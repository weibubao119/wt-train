package com.dyys.hr.dto.train;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * 过程管理-培训项目参训人员表
 *
 * @author sie sie
 * @since 1.0.0 2022-05-18
 */
@Data
@ApiModel(value = "过程管理-培训项目参训人员表")
public class TrainProgramsParticipantsDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "项目ID")
	private Long programsId;

	@ApiModelProperty(value = "参训人员ID")
	@NotBlank(message = "参训人员ID不能为空", groups = {TrainProgramsDTO.Insert.class,TrainProgramsDTO.Update.class})
	private String userId;

	@ApiModelProperty(value = "公司编码")
	@NotBlank(message = "参训人员公司编码不能为空", groups = {TrainProgramsDTO.Insert.class,TrainProgramsDTO.Update.class})
	private String companyCode;

	@ApiModelProperty(value = "部门编码")
	@NotBlank(message = "参训人员部门编码不能为空", groups = {TrainProgramsDTO.Insert.class,TrainProgramsDTO.Update.class})
	private String departmentCode;

	@ApiModelProperty(value = "岗位编码")
	@NotBlank(message = "参训人员岗位编码不能为空", groups = {TrainProgramsDTO.Insert.class,TrainProgramsDTO.Update.class})
	private String jobCode;

}