package com.dyys.hr.dto.train;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;


/**
 * 用户评估问卷-评分表详细数据记录表
 *
 * @author sie sie
 * @since 1.0.0 2022-06-20
 */
@Data
@ApiModel(value = "用户评估问卷-评分表详细数据记录表dto")
public class QuestionnaireScoreSheetsDetailDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户问卷评分记录表id")
	private Long sheetsId;

	@ApiModelProperty(value = "评分项名称")
	@NotBlank(message = "评分项名称不能为空", groups = {QuestionnaireUserSubmitDTO.Insert.class, QuestionnaireUserSubmitDTO.Update.class})
	private String tableDataName;

	@ApiModelProperty(value = "评分项key")
	@NotBlank(message = "评分项key不能为空", groups = {QuestionnaireUserSubmitDTO.Insert.class, QuestionnaireUserSubmitDTO.Update.class})
	private String tableDataKey;

	@ApiModelProperty(value = "打分列名称")
	@NotBlank(message = "打分列名称不能为空", groups = {QuestionnaireUserSubmitDTO.Insert.class, QuestionnaireUserSubmitDTO.Update.class})
	private String tableHeaderName;

	@ApiModelProperty(value = "打分列key")
	@NotBlank(message = "打分列key不能为空", groups = {QuestionnaireUserSubmitDTO.Insert.class, QuestionnaireUserSubmitDTO.Update.class})
	private String tableHeaderKey;

	@ApiModelProperty(value = "值")
	@NotBlank(message = "值不能为空", groups = {QuestionnaireUserSubmitDTO.Insert.class, QuestionnaireUserSubmitDTO.Update.class})
	private String value;

	@ApiModelProperty(value = "打分列分值设定")
	@NotBlank(message = "打分列分值设定不能为空", groups = {QuestionnaireUserSubmitDTO.Insert.class, QuestionnaireUserSubmitDTO.Update.class})
	private String headerScoreValue;

	@ApiModelProperty(value = "说明")
	private String descr;
}