package com.dyys.hr.dto.train;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * 过程管理-培训项目考勤人员表
 *
 * @author sie sie
 * @since 1.0.0 2022-06-06
 */
@Data
@ApiModel(value = "过程管理-培训项目考勤人员dto")
public class TrainAttendancePersonDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "考勤ID")
	private Long attendanceRulesId;

	@ApiModelProperty(value = "考勤人员ID")
	@NotBlank(message = "考勤人员ID不能为空", groups = {TrainAttendanceRulesDTO.Insert.class,TrainAttendanceRulesDTO.Update.class})
	private String emplId;

	@ApiModelProperty(value = "公司编码")
	@NotNull(message = "公司编码不能为空", groups = {TrainAppraiseDTO.Insert.class,TrainAppraiseDTO.Update.class})
	private String company;

	@ApiModelProperty(value = "部门编码")
	@NotNull(message = "部门编码不能为空", groups = {TrainAppraiseDTO.Insert.class,TrainAppraiseDTO.Update.class})
	private String deptId;

	@ApiModelProperty(value = "岗位编码")
	@NotNull(message = "岗位编码不能为空", groups = {TrainAppraiseDTO.Insert.class,TrainAppraiseDTO.Update.class})
	private String postCode;
}