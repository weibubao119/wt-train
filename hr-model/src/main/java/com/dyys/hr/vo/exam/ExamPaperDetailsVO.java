package com.dyys.hr.vo.exam;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author: Daan li
 * @Date: 2022/4/24 16:14
 */
@Data
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class ExamPaperDetailsVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("试卷名称")
    private String name;

    @ApiModelProperty("试卷描述")
    private String content;

    @ApiModelProperty("试卷总分")
    private String totle;

    @ApiModelProperty("及格线分数")
    private String passGrade;

    @ApiModelProperty("考试时长(分钟)")
    private String duration;

    @ApiModelProperty("是否限时(0:否, 1:是)")
    private Boolean isLimit;

    @ApiModelProperty("考试开始时间")
    private Date startTime;

    @ApiModelProperty("考试结束时间")
    private Date endTime;

    @ApiModelProperty("状态(1:进行中, 2:结束)")
    private Integer status;

    @ApiModelProperty("创建人")
    private String creator;

    @ApiModelProperty("创建时间")
    private Date createDate;

    @ApiModelProperty("试卷试题")
    private List<ExamPaperQuestionVO> questions;
}
