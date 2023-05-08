package com.dyys.hr.vo.train;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "培训项目计划列表VO")
public class TrainProgramsPlanDetailVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "课程id")
    private Long courseId;

    @ApiModelProperty(value = "课程名称")
    private String courseName;

    @ApiModelProperty(value = "课程类别id")
    private Long category;

    @ApiModelProperty(value = "课程名称")
    private String categoryName;

    @ApiModelProperty(value = "开始时间")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date endTime;

    @ApiModelProperty(value = "上课时间")
    private String schooltime;

    @ApiModelProperty(value = "场地")
    private String site;

    @ApiModelProperty(value = "机构id")
    private Long institutionId;

    @ApiModelProperty(value = "机构名称")
    private String institutionName;

    @ApiModelProperty(value = "学时")
    private BigDecimal classHour;

    @ApiModelProperty(value = "学习形式id")
    private Integer learningForm;

    @ApiModelProperty(value = "学习形式名称")
    private String learningFormName;

    @ApiModelProperty(value = "考核方式id 1.考试 2.考察")
    private Integer examineForm;

    @ApiModelProperty(value = "费用")
    private BigDecimal outlay;

    @ApiModelProperty(value = "费用说明")
    private String outlayText;
}
