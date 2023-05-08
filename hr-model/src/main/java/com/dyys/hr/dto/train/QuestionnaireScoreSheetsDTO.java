package com.dyys.hr.dto.train;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;


/**
 * 用户评估问卷-评分表数据记录
 *
 * @author sie sie
 * @since 1.0.0 2022-06-20
 */
@Data
@ApiModel(value = "用户评估问卷-评分表数据记录dto")
public class QuestionnaireScoreSheetsDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "id")
	private Long id;

	@ApiModelProperty(value = "评分表标题")
//	@NotBlank(message = "评分表标题不能为空", groups = {QuestionnaireUserSubmitDTO.Insert.class, QuestionnaireUserSubmitDTO.Update.class})
	private String title;

	@ApiModelProperty(value = "标题是否显示")
//	@NotNull(message = "评分表标题是否显示不能为空", groups = {QuestionnaireUserSubmitDTO.Insert.class, QuestionnaireUserSubmitDTO.Update.class})
	private Integer titleShown;

	@ApiModelProperty(value = "评分对象")
//	@NotNull(message = "评分对象不能为空", groups = {QuestionnaireUserSubmitDTO.Insert.class, QuestionnaireUserSubmitDTO.Update.class})
	private Integer scoreObject;

	@ApiModelProperty(value = "评分项详情数据列表")
	@Valid
//	@NotEmpty(message = "评分项详情数据列表不能为空", groups = {QuestionnaireUserSubmitDTO.Insert.class, QuestionnaireUserSubmitDTO.Update.class})
	private List<QuestionnaireScoreSheetsDetailDTO> sheetsDetailList;
}