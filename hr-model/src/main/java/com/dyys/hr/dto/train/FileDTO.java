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
@ApiModel(value = "证书格式")
public class FileDTO implements Serializable {
	@ApiModelProperty(value = "文件Id")
	@NotBlank(message = "文件Id不能为空", groups = {Update.class, Insert.class})
	private String fileId;

	@ApiModelProperty(value = "文件名称")
	@NotBlank(message = "证书文件名称不能为空", groups = {Update.class, Insert.class})
	private String filename;

	@ApiModelProperty(value = "文件地址")
	@NotBlank(message = "证书文件地址不能为空", groups = {Update.class, Insert.class})
	private String src;


	@ApiIgnore
	public interface Insert{}
	@ApiIgnore
	public interface Update{}
}