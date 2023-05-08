package com.dyys.hr.dto.train;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;


/**
 * 用户评估问卷-选择框数据dto
 *
 * @author sie sie
 * @since 1.0.0 2022-06-20
 */
@Data
@ApiModel(value = "用户评估问卷-选择框数据dto")
public class QuestionnaireCheckBoxDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "id")
	private Long id;

	@ApiModelProperty(value = "选择框标题")
	private String label;

	@ApiModelProperty(value = "定位key")
	private String labelKey;

	@ApiModelProperty(value = "类型 1.单选 2.多选")
	private Integer type;

	@ApiModelProperty(value = "选择框详情数据列表")
	@Valid
	private List<QuestionnaireCheckBoxDetailDTO> checkBoxDetailList;
}