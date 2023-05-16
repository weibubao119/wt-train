package com.dyys.hr.dto.train;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

/**
 * 资源管理-课程表
 *
 * @author sie sie
 * @since 1.0.0 2022-04-26
 */
@Data
@ApiModel(value = "资源管理-课程材料dto")
public class TrainBaseCourseMaterialsDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "材料id")
	private Long id;

	@ApiModelProperty(value = "材料分类")
	private String category;

	@ApiModelProperty(value = "材料名称")
	private String filename;

	@ApiModelProperty(value = "材料地址")
	private String src;

	@ApiModelProperty(value = "材料分类")
	private String name;

	@ApiModelProperty(value = "类别 1.音视频 2.其他")
	private Integer type;

	@ApiModelProperty(value = "时长")
	private String duration;
}