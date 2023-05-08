package com.dyys.hr.dto.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 公司表
 *
 * @author sie sie
 * @since 1.0.0 2022-05-05
 */
@Data
@ApiModel(value = "公司表")
public class PsCompanyDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键ID")
	private String id;

	@ApiModelProperty(value = "父ID")
	private String parentId;

	@ApiModelProperty(value = "描述")
	private String descr;

	@ApiModelProperty(value = "简称")
	private String descrShort;

	@ApiModelProperty(value = "生效时间")
	private Date effDate;

	@ApiModelProperty(value = "生效状态")
	private Integer effStatus;

	@ApiModelProperty(value = "创建人")
	private String creator;

	@ApiModelProperty(value = "创建时间")
	private Date createDate;

	@ApiModelProperty(value = "修改人")
	private String updater;

	@ApiModelProperty(value = "修改时间")
	private Date updateDate;


}