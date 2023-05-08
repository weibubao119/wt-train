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
@ApiModel(value = "培训项目评估列表vo")
public class TrainAppraiseVO implements Serializable{
    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "评估标题")
    private String title;

    @ApiModelProperty(value = "评估模板id")
    private Long questionnaireId;

    @ApiModelProperty(value = "评估模板标题")
    private String questionnaireTitle;

    @ApiModelProperty(value = "关联课程id")
    private Integer courseId;

    @ApiModelProperty(value = "关联课程名称")
    private String courseName;

    @ApiModelProperty(value = "开始时间")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm")
    private Date endTime;

    @ApiModelProperty(value = "状态")
    private Integer  status;

    @ApiModelProperty(value = "状态名称")
    private String statusName;
}
