package com.dyys.hr.dto.train;

import com.dagongma.kernel.annotation.EnumValue;
import com.dyys.hr.enums.TrainTypeEnum;
import com.dyys.hr.enums.YesOrNoEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

import java.util.List;

/**
 * 资源管理-讲师dto
 *
 * @author sie sie
 * @since 1.0.0 2022-04-27
 */
@Data
@ApiModel(value = "资源管理-讲师dto")
public class TrainBaseTeacherDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "讲师ID")
	private Long id;

	@ApiModelProperty(value = "姓名")
	@NotBlank(message = "讲师姓名不能为空", groups = { Insert.class,Update.class})
	private String name;

	@ApiModelProperty(value = "类别 1.内部讲师 2.外部讲师")
	@EnumValue(enumClass = TrainTypeEnum.class,enumMethod = "isValid",message = "请选择讲师类型",groups = { Insert.class,Update.class})
	private Integer type;

	@ApiModelProperty(value = "性别 M.男 F.女")
	private String sex;

	@ApiModelProperty(value = "状态 0.无效 1.有效")
	@NotNull(message = "请选择讲师状态", groups = { Insert.class,Update.class})
	@EnumValue(enumClass = YesOrNoEnum.class,enumMethod = "isValid",message = "请选择讲师状态")
	private Integer status;

	@ApiModelProperty(value = "机构/公司名称")
	private String organizationName;

	@ApiModelProperty(value = "讲师编号")
	private String number;

	@ApiModelProperty(value = "讲师年龄")
	private Integer age;

	@ApiModelProperty(value = "联系电话")
	private String contactPhone;

	@ApiModelProperty(value = "邮箱地址")
	private String email;

	@ApiModelProperty(value = "学历编码-文化程度/背景信息")
	private String educationLevel;

	@ApiModelProperty(value = "头像")
	private String avatar;

	@ApiModelProperty(value = "讲师历程列表")
	// @NotEmpty(message = "讲师历程列表为空", groups = {Insert.class,Update.class})
	// @Valid
	private List<TrainBaseTeacherProgressDTO> progressList;

	@ApiIgnore
	public interface Insert{}
	@ApiIgnore
	public interface Update{}
}