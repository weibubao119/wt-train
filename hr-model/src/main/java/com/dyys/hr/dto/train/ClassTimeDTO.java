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
@ApiModel(value = "时间段格式")
public class ClassTimeDTO implements Serializable {
	@ApiModelProperty(value = "开始时间")
	@NotBlank(message = "开始时间不能为空", groups = {Update.class, Insert.class})
	private String startTime;

	@ApiModelProperty(value = "结束时间")
	@NotBlank(message = "结束时间不能为空", groups = {Update.class, Insert.class})
	private String endTime;

	@ApiIgnore
	public interface Insert{}
	@ApiIgnore
	public interface Update{}
}