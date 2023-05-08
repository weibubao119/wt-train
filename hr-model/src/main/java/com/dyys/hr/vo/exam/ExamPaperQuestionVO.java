package com.dyys.hr.vo.exam;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 试题信息
 *
 * @author sie sie
 * @since 1.0.0 2022-04-24
 */
@Data
@ApiModel(value = "试题信息")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class ExamPaperQuestionVO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键ID")
	private Long id;

	@ApiModelProperty(value = "题目类型(1:单选，2:多选，3:判断，4:主观)")
	private Integer quType;

	@ApiModelProperty(value = "题目分数")
	private Integer score;

	@ApiModelProperty(value = "题目图片")
	private String image;

	@ApiModelProperty(value = "题目内容")
	private String content;

	@ApiModelProperty(value = "题目备注")
	private String remark;

	@ApiModelProperty(value = "正确答案")
	private String rightAnswer;

	@ApiModelProperty(value = "整题解析")
	private String analysis;

	@ApiModelProperty(value = "问题排序")
	private Integer sort;

	@ApiModelProperty(value = "答案选项列表")
	List<ExamQuestionAnswerVO> answerList;
}