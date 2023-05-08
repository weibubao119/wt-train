package com.dyys.hr.vo.train;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 资源管理-讲师vo
 *
 * @author sie sie
 * @since 1.0.0 2022-04-27
 */
@Data
@ApiModel(value = "资源管理-讲师vo")
@JsonInclude(JsonInclude.Include.ALWAYS)
public class TrainBaseTeacherDetailVO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "讲师ID")
	private Long id;

	@ApiModelProperty(value = "讲师编码")
	private String number;

	@ApiModelProperty(value = "姓名")
	private String name;

	@ApiModelProperty(value = "类别 1.内部讲师 2.外部讲师")
	private Integer type;

	@ApiModelProperty(value = "类别名称")
	private String typeName;

	@ApiModelProperty(value = "等级")
	private Integer level;

	@ApiModelProperty(value = "等级名称")
	private String levelName;

	@ApiModelProperty(value = "性别 M.男 F.女")
	private String sex;

	@ApiModelProperty(value = "性别名称")
	private String sexName;

	@ApiModelProperty(value = "状态 0.失效 1.有效")
	private Integer status;

	@ApiModelProperty(value = "状态名称")
	private String statusName;

	@ApiModelProperty(value = "讲师年龄")
	private Integer age;

	@ApiModelProperty(value = "联系电话")
	private String contactPhone;

	@ApiModelProperty(value = "邮箱地址")
	private String email;

	@ApiModelProperty(value = "学历-文化程度/背景信息编码")
	private String educationLevel;

	@ApiModelProperty(value = "学历-文化程度/背景信息名称")
	private String educationLevelName;

	@ApiModelProperty(value = "头像")
	private String avatar;

	@ApiModelProperty(value = "公司/机构名称")
	private String organizationName;

	@ApiModelProperty(value = "讲师历程列表")
	private List<TrainBaseTeacherProgressVO> progressList;
}