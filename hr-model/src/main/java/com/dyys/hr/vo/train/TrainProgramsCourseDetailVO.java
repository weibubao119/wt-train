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
@ApiModel(value = "培训项目课程列表VO")
public class TrainProgramsCourseDetailVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "课程id")
    private Long courseId;

    @ApiModelProperty(value = "课程名称")
    private String courseName;

    @ApiModelProperty(value = "课程开始时间")
    private String startTime;

    @ApiModelProperty(value = "课程结束时间")
    private String endTime;

    @ApiModelProperty(value = "上课时间")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date schooltime;

    @ApiModelProperty(value = "场地id")
    private Integer siteId;

    @ApiModelProperty(value = "场地名称")
    private String siteName;

    @ApiModelProperty(value = "培训内容")
    private String content;

    @ApiModelProperty(value = "讲师id")
    private Integer teacherId;

    @ApiModelProperty(value = "讲师名称")
    private String teacherName;

    @ApiModelProperty(value = "学时")
    private BigDecimal classHour;
}
