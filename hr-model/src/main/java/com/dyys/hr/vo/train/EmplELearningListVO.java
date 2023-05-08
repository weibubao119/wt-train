package com.dyys.hr.vo.train;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "学员档案-e-learning学习记录VO")
public class EmplELearningListVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "课程编号")
    private String courseNumber;

    @ApiModelProperty(value = "课程名称")
    private String courseName;

    @ApiModelProperty(value = "课程类别名称")
    private String courseCategoryName;

    @ApiModelProperty(value = "培训开始时间")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date courseStartTime;

    @ApiModelProperty(value = "培训结束时间")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date courseEndTime;

    @ApiModelProperty(value = "考试是否通过 1.是 0.否")
    private String isPass;

    @ApiModelProperty(value = "获得学分")
    private BigDecimal credit;

    @ApiModelProperty(value = "课程学时")
    private BigDecimal classHours;
}
