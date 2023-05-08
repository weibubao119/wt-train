package com.dyys.hr.dto.train;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * 用户评估问卷-说明数据记录
 *
 * @author sie sie
 * @since 1.0.0 2022-06-20
 */
@Data
@ApiModel(value = "用户评估问卷-说明数据记录dto")
public class QuestionnaireInstructionsDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "说明标题")
//	@NotBlank(message = "说明标题不能为空", groups = {QuestionnaireDTO.Insert.class, QuestionnaireDTO.Update.class})
	private String title;

	@ApiModelProperty(value = "标题是否显示")
//	@NotNull(message = "标题是否显示不能为空", groups = {QuestionnaireDTO.Insert.class, QuestionnaireDTO.Update.class})
	private Integer titleShown;

	@ApiModelProperty(value = "说明内容")
//	@NotBlank(message = "说明内容不能为空", groups = {QuestionnaireDTO.Insert.class, QuestionnaireDTO.Update.class})
	private String value;
}