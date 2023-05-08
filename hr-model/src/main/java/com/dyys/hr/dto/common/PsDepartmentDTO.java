package com.dyys.hr.dto.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 部门表
 *
 * @author sie sie
 * @since 1.0.0 2022-05-05
 */
@Data
@ApiModel(value = "部门表")
public class PsDepartmentDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键ID")
	private String id;

	@ApiModelProperty(value = "集合ID")
	private String setId;

	@ApiModelProperty(value = "生效时间")
	private Date effDate;

	@ApiModelProperty(value = "生效状态")
	private Integer effStatus;

	@ApiModelProperty(value = "描述(全名)")
	private String descr;

	@ApiModelProperty(value = "简短描述(简称)")
	private String descrShort;

	@ApiModelProperty(value = "所属公司ID")
	private String company;

	@ApiModelProperty(value = "单位")
	private String businessUnit;

	@ApiModelProperty(value = "地点")
	private String location;

	@ApiModelProperty(value = "管理者ID")
	private String managerId;

	@ApiModelProperty(value = "管理者职位")
	private String managerPosn;

	@ApiModelProperty(value = "部门编码")
	private String deptCode;

	@ApiModelProperty(value = "部门层级")
	private String deptLevel;

	@ApiModelProperty(value = "创建者")
	private String creator;

	@ApiModelProperty(value = "创建时间")
	private Date createrDate;

	@ApiModelProperty(value = "修改者")
	private String updater;

	@ApiModelProperty(value = "修改时间")
	private Date updaterDate;


}