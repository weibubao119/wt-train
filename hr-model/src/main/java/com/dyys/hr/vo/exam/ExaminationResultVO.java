package com.dyys.hr.vo.exam;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @Author: Daan li
 * @Date: 2022/4/22 8:42
 */
@Data
@ApiModel(value = "ExaminationResultVO", description = "考试结果")
public class ExaminationResultVO {
    @ApiModelProperty(value = "考试id", required = true)
    private String examId;

    @ApiModelProperty(value = "试卷id", required = true)
    private String paperId;

    @ApiModelProperty(value = "考试用时(分钟)", required = true)
    private String useTime;

    @ApiModelProperty(value = "开始考试时间", required = true)
    private Date examTime;

    @ApiModelProperty(value = "答题列表", required = true)
    private List<ExamQuestionResultVO> questions;
}
