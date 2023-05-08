package com.dyys.hr.dto.train;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;


/**
 * 评估问卷-主表
 *
 * @author sie sie
 * @since 1.0.0 2022-06-20
 */
@Data
@ApiModel(value = "评估问卷-用户提交问卷dto")
public class QuestionnaireUserSubmitDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "评估类型：1培训评估，2机构评估")
	@NotNull(message = "评估类型值不能为空", groups = {Insert.class,Update.class})
	private Integer type;

	@ApiModelProperty(value = "培训评估ID/机构评估ID")
	@NotNull(message = "评估记录id不能为空", groups = {Insert.class,Update.class})
	private Long trainAppraiseId;

	@ApiModelProperty(value = "文本框数据列表")
	private List<QuestionnaireTextDTO> questionnaireTextList;

	@ApiModelProperty(value = "评分表数据列表")
	private List<QuestionnaireScoreSheetsDTO> questionnaireScoreSheetsList;

	@ApiModelProperty(value = "说明列表")
	private List<QuestionnaireInstructionsDTO> questionnaireInstructionsList;

	@ApiModelProperty(value = "选择框列表")
	private List<QuestionnaireCheckBoxDTO> questionnaireCheckBoxList;

	@ApiIgnore
	public interface Insert{}
	@ApiIgnore
	public interface Update{}
}