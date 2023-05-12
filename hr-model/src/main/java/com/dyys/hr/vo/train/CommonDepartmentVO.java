package com.dyys.hr.vo.train;

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
@ApiModel(value = "培训-组织架构vo")
public class CommonDepartmentVO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty("序号")
	private Integer number;

	@ApiModelProperty("集团公司")
	private String setId;

	@ApiModelProperty("部门编码")
	private String id;

	@ApiModelProperty("生效日期")
	private Date effDate;

	@ApiModelProperty("状态")
	private String effStatus;

	@ApiModelProperty("全称")
	private String descr;

	@ApiModelProperty("部门详细路径")
	private String deptNamePath;

	@ApiModelProperty("负责人")
	private String managerName;
}