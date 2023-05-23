package com.dyys.hr.vo.train;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * 资源管理-课程表
 *
 * @author sie sie
 * @since 1.0.0 2022-04-26
 */
@Data
@ApiModel(value = "资源管理-课程材料分页vo")
public class TrainBaseCourseMaterialsCategoryVO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "材料分类")
	private String category;

	@ApiModelProperty(value = "课程材料列表")
	private List<TrainBaseCourseMaterialsVO> materialsList;
}