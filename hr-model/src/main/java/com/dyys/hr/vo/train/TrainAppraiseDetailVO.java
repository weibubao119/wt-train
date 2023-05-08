package com.dyys.hr.vo.train;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "培训项目评估详情vo")
public class TrainAppraiseDetailVO implements Serializable{
    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "评估标题")
    private String title;

    @ApiModelProperty(value = "评估模板id")
    private Long questionnaireId;

    @ApiModelProperty(value = "评估模板")
    private String questionnaireTitle;

    @ApiModelProperty(value = "关联课程id")
    private Long courseId;

    @ApiModelProperty(value = "关联课程名称")
    private String courseName;

    @ApiModelProperty(value = "开始时间")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm")
    private Date endTime;

    @ApiModelProperty(value = "参评人员数据列表")
    private List<TrainAppraisePersonVO> appraisePersonList;
}
