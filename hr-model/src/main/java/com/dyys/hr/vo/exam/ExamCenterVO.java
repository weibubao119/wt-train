package com.dyys.hr.vo.exam;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: Daan li
 * @Date: 2022/6/15 16:14
 */
@Data
@ApiModel(value = "考试中心列表VO")
public class ExamCenterVO implements Serializable {
    @ApiModelProperty(value = "考试IDID")
    private String examId;

    @ApiModelProperty(value = "考试记录ID")
    private String detailsId;

    @ApiModelProperty(value = "培训项目")
    private String project;

    @ApiModelProperty(value = "考试标题")
    private String title;

    @ApiModelProperty(value = "考试类型名称")
    private String typeName;

    @ApiModelProperty(value = "考试总时长")
    private String duration;

    @ApiModelProperty(value = "开始时间")
    @JsonFormat(locale="zh", timezone="GMT+8",pattern = "yyyy-MM-dd HH:mm")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    @JsonFormat(locale="zh", timezone="GMT+8",pattern = "yyyy-MM-dd HH:mm")
    private Date endTime;

    @ApiModelProperty(value = "考试得分")
    private String score;
}
