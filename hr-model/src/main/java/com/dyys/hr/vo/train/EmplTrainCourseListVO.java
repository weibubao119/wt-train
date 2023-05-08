package com.dyys.hr.vo.train;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 职务数据表
 *
 * @author sie sie
 * @since 1.0.0 2022-07-18
 */
@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "学员档案-培训课程记录vo")
public class EmplTrainCourseListVO implements Serializable {
    private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "课程名称")
	private String courseName;

	@ApiModelProperty(value = "开始时间")
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
	private Date courseStartTime;

	@ApiModelProperty(value = "结束时间")
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
	private Date courseEndTime;

	@ApiModelProperty(value = "课程类别")
	private String courseCategory;

	@ApiModelProperty(value = "学习形式")
	private String learningForm;

	@ApiModelProperty(value = "课程成绩")
	private String courseScore;
}