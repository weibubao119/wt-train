package com.dyys.hr.vo.train;

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
@ApiModel(value = "资源管理-课程材料vo")
public class TrainBaseCourseMaterialsVO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "材料id")
	private Long id;

	@ApiModelProperty(value = "材料分类")
	private String category;

	@ApiModelProperty(value = "材料名称")
	private String filename;

	@ApiModelProperty(value = "材料标题")
	private String fileTitle;

	@ApiModelProperty(value = "材料地址")
	private String src;

	@ApiModelProperty(value = "类别 1.视频 2.音频 3.其他")
	private Integer type;

	@ApiModelProperty(value = "时长")
	private String duration;

	@ApiModelProperty(value = "学习状态：1.已学 0.未学")
	private Integer learnedStatus;
}