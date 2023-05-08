package com.dyys.hr.vo.train;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "机构服务历史展示参数")
public class TrainInstitutionHistoryServiceVO {
    @ApiModelProperty(value = "课程ID")
    private Long courseId;

    @ApiModelProperty(value = "课程名称")
    private String courseName;

    @ApiModelProperty(value = "培训项目ID")
    private Long programsId;

    @ApiModelProperty(value = "培训名称")
    private String trainName;

    @ApiModelProperty(value = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    @ApiModelProperty(value = "讲师姓名")
    private String teacherName;

    @ApiModelProperty(value = "课程评分")
    private BigDecimal courseScore;
}
