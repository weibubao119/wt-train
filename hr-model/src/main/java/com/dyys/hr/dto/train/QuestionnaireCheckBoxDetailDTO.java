package com.dyys.hr.dto.train;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;


/**
 * 用户评估问卷-选择框详细数据dto
 *
 * @author sie sie
 * @since 1.0.0 2022-06-20
 */
@Data
@ApiModel(value = "用户评估问卷-选择框详细数据dto")
public class QuestionnaireCheckBoxDetailDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户问卷选择框表id")
	private Long boxId;

	@ApiModelProperty(value = "选项值名称")
	private String label;

	@ApiModelProperty(value = "值")
	private String value;
}