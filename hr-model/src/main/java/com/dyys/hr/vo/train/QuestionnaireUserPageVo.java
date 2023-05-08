package com.dyys.hr.vo.train;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * 评估问卷-主表
 *
 * @author sie sie
 * @since 1.0.0 2022-06-20
 */
@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "评估问卷-用户填写问卷分页列表vo")
public class QuestionnaireUserPageVo implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键id")
	private Long id;

	@ApiModelProperty(value = "问卷模板id")
	private Long questionnaireId;

	@ApiModelProperty(value = "问卷名称")
	private String title;

	@ApiModelProperty(value = "状态 1.已提交 0.未提交")
	private Integer status;

	@ApiModelProperty(value = "状态名称")
	private String statusName;

	@ApiModelProperty(value = "创建时间")
	private String createTime;

	@ApiModelProperty(value = "更新时间")
	private String updateTime;
}