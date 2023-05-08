package com.dyys.hr.vo.train;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * 职务数据表
 *
 * @author sie sie
 * @since 1.0.0 2022-07-18
 */
@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "学员档案-员工信息vo")
public class EmplInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "员工工号")
	private String emplId;

	@ApiModelProperty(value = "员工姓名")
	private String emplName;

	@ApiModelProperty(value = "公司编码")
	private String company;

	@ApiModelProperty(value = "公司名称")
	private String companyDescr;

	@ApiModelProperty(value = "部门编码")
	private String deptId;

	@ApiModelProperty(value = "部门名称")
	private String deptDescr;

	@ApiModelProperty(value = "职位名称")
	private String postDescr;

	@ApiModelProperty(value = "职位层级名称")
	private String gradeDescr;

	@ApiModelProperty(value = "职位等级名称")
	private String levelDescr;

	@ApiModelProperty(value = "毕业院校")
	private String edctSchl;

	@ApiModelProperty(value = "学历")
	private String edu;

	@ApiModelProperty(value = "民族")
	private String nation;

	@ApiModelProperty(value = "政治面貌")
	private String politicalStaChn;

	@ApiModelProperty(value = "籍贯")
	private String nativePlace;

	@ApiModelProperty(value = "婚姻状况")
	private String marStatus;

	@ApiModelProperty(value = "健康状况")
	private String health;

	@ApiModelProperty(value = "员工头像")
	private String avatar;

	@ApiModelProperty(value = "头像数据")
	private byte[] avatarFileData;
}