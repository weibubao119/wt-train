package com.dyys.hr.vo.exam;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 问题答案选项
 *
 * @author sie sie
 * @since 1.0.0 2022-04-24
 */
@Data
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@ApiModel(value = "问题答案选项")
public class ExamQuestionAnswerVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键ID")
	private Long id;

	@ApiModelProperty(value = "问题ID")
	private String quId;

	@ApiModelProperty(value = "是否正确答案(1:是，0:否)")
	private Integer isRight;

	@ApiModelProperty(value = "选项图片")
	private String image;

	@ApiModelProperty(value = "答案内容")
	private String content;

	@ApiModelProperty(value = "答案分析")
	private String analysis;

	@ApiModelProperty(value = "选项标识(abcd)")
	private String itemIndex;

	@ApiModelProperty(value = "排序")
	private Integer sort;
}