package com.dyys.hr.dto.train;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;


/**
 * 证书表
 *
 * @author sie sie
 * @since 1.0.0 2022-04-24
 */
@Data
@ApiModel(value = "用户格式")
public class PersonDTO implements Serializable {
	@ApiModelProperty(value = "用户id")
	@NotBlank(message = "用户id不能为空", groups = {Update.class, Insert.class})
	private String emplId;

	@ApiModelProperty(value = "用户名称")
	@NotBlank(message = "用户名称不能为空", groups = {Update.class, Insert.class})
	private String emplName;

	@ApiIgnore
	public interface Insert{}
	@ApiIgnore
	public interface Update{}
}