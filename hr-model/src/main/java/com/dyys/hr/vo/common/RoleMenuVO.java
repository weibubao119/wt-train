package com.dyys.hr.vo.common;

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
@ApiModel(value = "菜单权限vo")
public class RoleMenuVO implements Serializable {
	@ApiModelProperty(value = "用户标识 1.管理员 2.员工")
	private Integer isAdmin;

	@ApiModelProperty(value = "菜单编码")
	private List<RoleDataVO> menuList;
}