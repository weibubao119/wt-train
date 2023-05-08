package com.dyys.hr.dto.train;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;

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
@ApiModel(value = "员工管理-培训考勤签到操作dto")
public class EmploySignInAttendanceDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "考勤记录ID")
	@NotBlank(message = "考勤记录ID不能为空", groups = {Insert.class})
	private Long attendanceRulesId;

	@ApiModelProperty(value = "签到人工号")
	@NotBlank(message = "签到人工号不能为空", groups = {Insert.class})
	private String emplId;

	@ApiIgnore
	public interface Insert{}
	@ApiIgnore
	public interface Update{}

}