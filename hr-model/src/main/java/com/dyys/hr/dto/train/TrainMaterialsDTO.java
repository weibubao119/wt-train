package com.dyys.hr.dto.train;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * 项目费用dto
 *
 * @author sie sie
 * @since 1.0.0 2022-04-28
 */
@Data
@ApiModel(value = "项目管理资料表")
public class TrainMaterialsDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "培训项目ID")
	@NotNull(message = "培训项目ID不能为空", groups = {Insert.class,Update.class})
	private Long programsId;

	@ApiModelProperty(value = "材料分类")
	@NotBlank(message = "材料分类不能为空", groups = {Insert.class,Update.class})
	private String category;

	@ApiModelProperty(value = "材料名称")
	@NotBlank(message = "材料名称不能为空", groups = {Insert.class,Update.class})
	private String filename;

	@ApiModelProperty(value = "材料地址")
	@NotBlank(message = "材料地址不能为空", groups = {Insert.class,Update.class})
	private String src;

	@ApiModelProperty(value = "类别 1.视频 2.音频 3.其他")
	@NotNull(message = "材料类别不能为空", groups = {Insert.class,Update.class})
	private Integer type;

	@ApiModelProperty(value = "时长")
	private String duration;

	@ApiIgnore
	public interface Insert{}
	@ApiIgnore
	public interface Update{}
}