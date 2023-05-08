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
@ApiModel(value = "ID格式")
public class IdDTO implements Serializable {
	@ApiModelProperty(value = "ID")
	@NotBlank(message = "ID不能为空", groups = {Update.class, Insert.class})
	private Long id;

	@ApiIgnore
	public interface Insert{}
	@ApiIgnore
	public interface Update{}
}