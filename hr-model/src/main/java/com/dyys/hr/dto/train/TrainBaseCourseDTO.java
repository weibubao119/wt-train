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
import java.math.BigDecimal;
import java.util.List;


/**
 * 资源管理-课程表
 *
 * @author sie sie
 * @since 1.0.0 2022-04-26
 */
@Data
@ApiModel(value = "资源管理-课程表")
public class TrainBaseCourseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "课程ID")
	@NotNull(message = "课程ID不能为空", groups = {Update.class})
	private Long id;

	@ApiModelProperty(value = "课程名称")
	@NotBlank(message = "课程名称不能为空", groups = {Update.class, Insert.class})
	private String name;

	@ApiModelProperty(value = "课程类别id ")
	@NotNull(message = "请选择课程类别", groups = { Update.class, Insert.class})
	private Integer category;

	@ApiModelProperty(value = "课程来源 1.外部 2.自有")
	@NotNull(message = "请选择课程来源", groups = {Update.class, Insert.class})
	private Integer courseSource;

	@ApiModelProperty(value = "课时数")
	@NotNull(message = "课时数不能为空", groups = {Update.class, Insert.class})
	private BigDecimal classHours;

	@ApiModelProperty(value = "课程学分")
	@NotNull(message = "课程学分不能为空", groups = {Update.class, Insert.class})
	private BigDecimal credit;

	@ApiModelProperty(value = "课程简介")
	private String instructions;

	@ApiModelProperty(value = "授课讲师列表")
	@NotEmpty(message = "授课讲师列表不能为空", groups = {Update.class, Insert.class})
	@Valid
	private List<TeacherDTO> teacherList;

	@ApiModelProperty(value = "课程材料列表")
	private List<TrainBaseCourseMaterialsDTO> materialsList;

	@ApiIgnore
	public interface Insert{}
	@ApiIgnore
	public interface Update{}
}