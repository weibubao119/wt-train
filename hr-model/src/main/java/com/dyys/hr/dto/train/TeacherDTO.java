package com.dyys.hr.dto.train;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * 证书表
 *
 * @author sie sie
 * @since 1.0.0 2022-04-24
 */
@Data
@ApiModel(value = "课程管理-授课讲师dto")
public class TeacherDTO implements Serializable {
	@ApiModelProperty(value = "讲师id")
	@NotNull(message = "讲师id不能为空", groups = {TrainBaseCourseDTO.Update.class, TrainBaseCourseDTO.Insert.class})
	private Long teacherId;

	@ApiModelProperty(value = "讲师工号/编号")
	@NotBlank(message = "讲师工号/编号不能为空", groups = {TrainBaseCourseDTO.Update.class, TrainBaseCourseDTO.Insert.class})
	private String number;

	@ApiModelProperty(value = "讲师姓名")
	@NotBlank(message = "讲师姓名不能为空", groups = {TrainBaseCourseDTO.Update.class, TrainBaseCourseDTO.Insert.class})
	private String name;

	@ApiModelProperty(value = "讲师类型 1.内部 2.外部")
	@NotNull(message = "讲师类型不能为空", groups = {TrainBaseCourseDTO.Update.class, TrainBaseCourseDTO.Insert.class})
	private Integer type;

	@ApiModelProperty(value = "所属公司/机构名称")
	@NotBlank(message = "所属公司/机构名称不能为空", groups = {TrainBaseCourseDTO.Update.class, TrainBaseCourseDTO.Insert.class})
	private String organizationName;
}