package com.dyys.hr.dto.train;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
/**
 * 项目基础档案dto
 *
 * @author sie sie
 * @since 1.0.0 2022-04-28
 */
@Data
@ApiModel(value = "项目基础档案dto")
public class TrainConstantDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "档案名称")
	@NotBlank(message = "档案名称不能为空", groups = {Insert.class,Update.class})
	private String name;

	@ApiModelProperty(value = "档案类型")
	@NotNull(message = "档案类型不能为空", groups = {Insert.class,Update.class})
	private Integer type;

	@ApiModelProperty(value = "上级ID（职序编码）")
	private String pid;

	@ApiModelProperty(value = "状态")
	@NotNull(message = "档案状态不能为空", groups = {Insert.class,Update.class})
	private Integer status;

	@ApiIgnore
	public interface Insert{}
	@ApiIgnore
	public interface Update{}
}