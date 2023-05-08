package com.dyys.hr.vo.exam;

import com.dyys.hr.entity.exam.ExamQuestionAnswer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;

/**
 * @Author: Daan li
 * @Date: 2022/4/21 14:03
 */
@Data
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@ApiModel(value = "ExamQuDetialsVO对象", description = "试题详情")
public class ExamQuDetialsVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("题目类型(1:单选，2:多选，3:判断，4:主观)")
    private Integer quType;

    @ApiModelProperty("题目分数")
    private Integer score;

    @ApiModelProperty("题目图片")
    private String image;

    @ApiModelProperty("题目内容")
    private String content;

    @ApiModelProperty("题目备注")
    private String remark;

    @ApiModelProperty("整题解析")
    private String analysis;

    @ApiModelProperty("正确答案")
    private String rightAnswer;

    @ApiModelProperty("试题答案")
    private List<ExamQuestionAnswerVO> answers;
}
