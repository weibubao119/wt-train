package com.dyys.hr.vo.train;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 职务数据表
 *
 * @author sie sie
 * @since 1.0.0 2022-07-18
 */
@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "学员档案-培训记录vo")
public class EmplTrainListVO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "项目名称")
	private String trainName;

	@ApiModelProperty(value = "项目编号")
	private String trainNumber;

	@ApiModelProperty(value = "开始时间")
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
	private Date startTime;

	@ApiModelProperty(value = "结束时间")
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
	private Date endTime;

	@ApiModelProperty(value = "项目总成绩")
	private String totalScore;

	@ApiModelProperty(value = "课程列表")
	private List<EmplTrainCourseListVO> courseList;

}