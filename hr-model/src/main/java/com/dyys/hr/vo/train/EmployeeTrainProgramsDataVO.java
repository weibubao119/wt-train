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
@ApiModel(value = "员工培训信息数据")
public class EmployeeTrainProgramsDataVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "开始时间")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date endTime;

    @ApiModelProperty(value = "培训名称")
    private String trainName;

    @ApiModelProperty(value = "培训证书")
    private String certificateName;

    @ApiModelProperty(value = "培训单位/机构")
    private String companyDescr;

    @ApiModelProperty(value = "课程名称")
    private String courseName;

    @ApiModelProperty(value = "课时")
    private String courseClassHours;

    @ApiModelProperty(value = "培训成绩")
    private String totalScore;

    @ApiModelProperty(value = "培训讲师")
    private String teacherName;

}
