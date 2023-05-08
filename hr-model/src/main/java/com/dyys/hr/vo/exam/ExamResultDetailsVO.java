package com.dyys.hr.vo.exam;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author: Daan li
 * @Date: 2022/6/16 11:14
 */
@Data
@ApiModel(value = "考试结果详情VO")
public class ExamResultDetailsVO {
    @ApiModelProperty("考试标题")
    private String title;
    @ApiModelProperty("试题数")
    private Integer quesCount;
    @ApiModelProperty("试卷总分")
    private Integer totalScore;
    @ApiModelProperty("考试用时")
    private String useTime;
    @ApiModelProperty("考试得分")
    private Float score;

    @ApiModelProperty("答题结果")
    List<ExamAnswerDetailsVO> answerDetailsList;
}
