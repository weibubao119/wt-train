package com.dyys.hr.vo.exam;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: Daan li
 * @Date: 2022/6/16 11:22
 */
@Data
@ApiModel(value = "试题与结果详情VO")
public class ExamAnswerDetailsVO extends ExamQuDetialsVO {
    @ApiModelProperty(value = "答题结果(选择题或判断题结果，多选以,分开)")
    private String answer;

    @ApiModelProperty("用户答案是否正确(1:是，0:否)")
    private Integer isRight;
}
