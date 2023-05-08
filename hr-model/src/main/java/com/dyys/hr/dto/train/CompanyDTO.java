package com.dyys.hr.dto.train;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;


/**
 * 公司
 *
 * @author sie sie
 * @since 1.0.0 2022-04-24
 */
@Data
@ApiModel(value = "公司格式")
public class CompanyDTO implements Serializable {
	@ApiModelProperty(value = "公司编码")
	@NotBlank(message = "公司编码不能为空", groups = {Update.class, Insert.class})
	private String companyCode;

	@ApiModelProperty(value = "公司名称")
	@NotBlank(message = "公司名称", groups = {Update.class, Insert.class})
	private String companyName;

	@ApiIgnore
	public interface Insert{}
	@ApiIgnore
	public interface Update{}
}