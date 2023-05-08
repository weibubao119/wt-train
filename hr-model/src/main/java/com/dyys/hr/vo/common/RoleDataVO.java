package com.dyys.hr.vo.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * 部门表
 *
 * @author sie sie
 * @since 1.0.0 2022-05-05
 */
@Data
@ApiModel(value = "菜单权限vo")
public class RoleDataVO implements Serializable {
    private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "菜单编码")
	private String pageCode;
}