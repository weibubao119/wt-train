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
@ApiModel(value = "员工考试记录列表vo")
public class EmployeeExamPageVO implements Serializable{
    @ApiModelProperty(value = "参考记录id")
    private Long examinerId;

    @ApiModelProperty(value = "考试标题")
    private String title;

    @ApiModelProperty(value = "考试模板id")
    private Long paperId;

    @ApiModelProperty(value = "考试模板")
    private String paperName;

    @ApiModelProperty(value = "考试类型")
    private Integer type;

    @ApiModelProperty(value = "考试类型名称")
    private String typeName;

    @ApiModelProperty(value = "开始时间")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm")
    private Date endTime;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "状态名称")
    private String statusName;

    @ApiModelProperty(value = "成绩")
    private Float highestScore;


}
