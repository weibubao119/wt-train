package com.dyys.hr.vo.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * 公司表
 *
 * @author sie sie
 * @since 1.0.0 2022-05-05
 */
@Data
@ApiModel(value = "公司表")
public class PsCompanyVO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "公司编码")
	private String companyCode;

	@ApiModelProperty(value = "公司名称")
	private String companyName;

}