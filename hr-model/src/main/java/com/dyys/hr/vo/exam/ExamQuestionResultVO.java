package com.dyys.hr.vo.exam;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

/**
 * @Author: Daan li
 * @Date: 2022/4/22 8:44
 */
@Data
@ApiModel(value = "ExamQuestionResultVO对象", description = "试题结果")
public class ExamQuestionResultVO {
    @ApiModelProperty(value = "试题ID", required = true)
    private Long quId;

    @ApiModelProperty(value = "提交的试题答案id(多选以,格开)")
    private String answers;

    @ApiModelProperty(value = "主观题答案")
    private String content;
}
