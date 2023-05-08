package com.dyys.hr.vo.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * 部门表
 *
 * @author sie sie
 * @since 1.0.0 2022-05-05
 */
@Data
@ApiModel(value = "新部门组织表")
@JsonIgnoreProperties(value = {"handler"})
public class StaffDepartmentVO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "部门编码")
	private String deptId;

	@ApiModelProperty(value = "部门名称")
	private String deptName;

	@ApiModelProperty(value = "部门路径名称")
	private String deptPathName;

	@ApiModelProperty(value = "上级部门编码")
	private String parentDeptid;

	@ApiModelProperty(value = "部门等级")
	private String deptLevel;

	@ApiModelProperty(value = "所属公司编码")
	private String companyCode;

	@ApiModelProperty(value = "所属公司名称")
	private String companyName;

	@ApiModelProperty(value = "可选标识：true不可选，false可选")
	private Boolean disabled;

	@ApiModelProperty(value = "子部门")
	private List<StaffDepartmentVO> children;
}