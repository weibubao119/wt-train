package com.dyys.hr.vo.exam;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: Daan li
 * @Date: 2022/4/22 8:45
 */
@Data
@ApiModel(value = "ExamAnswerResultVO对象", description = "试题答案结果")
public class ExamAnswerResultVO {
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty(value = "主观题答案", required = true)
    private String content;

    @ApiModelProperty(value = "答题结果(选择题或判断题结果，多选以、分开)", required = true)
    private String answer;

    @ApiModelProperty("是否正确答案(1:是，0:否)")
    private Integer isRight;
}
