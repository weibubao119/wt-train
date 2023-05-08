package com.dyys.hr.vo.train;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Desc
 * Create by yangye
 * Date 2022/9/26 9:36
 */
@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value="TrainBaseSiteUsageVO",description="场地使用情况")
public class TrainBaseSiteUsageVO implements Serializable {

    @ApiModelProperty(value = "培训项目名称")
    private String trainName;

    @ApiModelProperty(value = "课程名称")
    private String courseName;

    @ApiModelProperty(value = "上课日期")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date schooltime;

    @ApiModelProperty(value = "课程开始时间")
    private String startTime;

    @ApiModelProperty(value = "课程结束时间")
    private String endTime;
}
