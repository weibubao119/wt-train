package com.dyys.hr.vo.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * 用户表
 *
 * @author sie sie
 * @since 1.0.0 2022-05-05
 */
@Data
@ApiModel(value = "用户表")
@JsonInclude(JsonInclude.Include.ALWAYS)
public class PsPersionVO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "员工id")
	private String emplId;

	@ApiModelProperty(value = "员工姓名")
	private String emplName;

	@ApiModelProperty(value = "员工组织树的第一个可选组织编码")
	private String authDeptId;

	@ApiModelProperty(value = "性别 F:女 M:男")
	private String gender;

	@ApiModelProperty(value = "性别名称")
	private String genderName;

	@ApiModelProperty(value = "学历背景/文化程度编号")
	private String edctBakg;

	@ApiModelProperty(value = "学历背景/文化程度名称")
	private String educationLevel;

	@ApiModelProperty(value = "手机号码")
	private String mobile;

	@ApiModelProperty(value = "邮箱")
	private String email;

	@ApiModelProperty(value = "年龄")
	private String age;

	@ApiModelProperty(value = "头像")
	private String avatar;

	@ApiModelProperty(value = "头像数据")
	private byte[] avatarFileData;

	@ApiModelProperty(value = "单位名称")
	private String companyName;

	@ApiModelProperty(value = "单位编码")
	private String companyCode;

	@ApiModelProperty(value = "部门名称")
	private String departmentName;

	@ApiModelProperty(value = "部门编码")
	private String departmentCode;

	@ApiModelProperty(value = "职位名称")
	private String jobName;

	@ApiModelProperty(value = "职位编码")
	private String jobCode;

	@ApiModelProperty(value = "职位层级（职级分类）编码")
	private String gradeCode;

	@ApiModelProperty(value = "职位层级（职级分类）名称")
	private	String gradeName;

	@ApiModelProperty(value = "职位等级（职级）编码")
	private String levelCode;

	@ApiModelProperty(value = "职位等级（职级）名称")
	private	String levelName;

	@ApiModelProperty(value = "职位序列（职序）编码")
	private String secCode;

	@ApiModelProperty(value = "职位序列（职序）名称")
	private	String secName;

}