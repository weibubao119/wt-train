package com.dyys.hr.vo.train;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "培训项目考试列表vo")
public class TrainExamVO implements Serializable{
    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "课程id")
    private String courseId;

    @ApiModelProperty(value = "课程名称")
    private String courseName;

    @ApiModelProperty(value = "考试标题")
    private String title;

    @ApiModelProperty(value = "考试类型")
    private Integer type;

    @ApiModelProperty(value = "考试类型名称")
    private String typeName;

    @ApiModelProperty(value = "考试模板id")
    private Long paperId;

    @ApiModelProperty(value = "考试模板")
    private String paperName;

    @ApiModelProperty(value = "开始时间")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm")
    private Date endTime;

    @ApiModelProperty(value = "考试人数")
    private Integer userCount;

    @ApiModelProperty(value = "考试总时长")
    private String duration;

    @ApiModelProperty(value = "考试及格分")
    private String passScore;

    @ApiModelProperty(value = "考试总分")
    private String totalScore;

    @ApiModelProperty(value = "考试次数限制")
    private Integer examCount;

    @ApiModelProperty(value = "状态")
    private Integer  status;

    @ApiModelProperty(value = "状态名称")
    private String statusName;


}
