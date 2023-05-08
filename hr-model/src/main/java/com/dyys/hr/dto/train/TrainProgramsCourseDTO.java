package com.dyys.hr.dto.train;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 过程管理-培训项目课程表
 *
 * @author sie sie
 * @since 1.0.0 2022-08-25
 */
@Data
@ApiModel(value = "过程管理-培训项目课程表dto")
public class TrainProgramsCourseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "课程id")
	@NotNull(message = "培训课表-课程不能为空", groups = {TrainProgramsDTO.Insert.class, TrainProgramsDTO.Update.class})
	private Long courseId;

	@ApiModelProperty(value = "课程名称")
	@NotNull(message = "培训课表-课程名称不能为空", groups = {TrainProgramsDTO.Insert.class, TrainProgramsDTO.Update.class})
	private String courseName;

	@ApiModelProperty(value = "上课日期")
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
//	@Future(message = "培训课表-上课日期必须大于当前日期", groups = {TrainProgramsDTO.Insert.class, TrainProgramsDTO.Update.class})
	private Date schooltime;

	@ApiModelProperty(value = "开始时间")
	@NotNull(message = "培训课表-课程开始时间不能为空", groups = {TrainProgramsDTO.Insert.class, TrainProgramsDTO.Update.class})
	private String startTime;

	@ApiModelProperty(value = "结束时间")
	@NotNull(message = "培训课表-课程结束时间不能为空", groups = {TrainProgramsDTO.Insert.class, TrainProgramsDTO.Update.class})
	private String endTime;

	@ApiModelProperty(value = "场地id")
	@NotNull(message = "培训课表-场地id不能为空", groups = {TrainProgramsDTO.Insert.class, TrainProgramsDTO.Update.class})
	private Integer siteId;

	@ApiModelProperty(value = "培训内容")
	@NotBlank(message = "培训课表-培训内容不能为空", groups = {TrainProgramsDTO.Insert.class, TrainProgramsDTO.Update.class})
	private String content;

	@ApiModelProperty(value = "讲师id")
	@NotNull(message = "培训课表-讲师id不能为空", groups = {TrainProgramsDTO.Insert.class, TrainProgramsDTO.Update.class})
	private Long teacherId;

	@ApiModelProperty(value = "学时")
	@NotNull(message = "培训课表-学时不能为空", groups = {TrainProgramsDTO.Insert.class, TrainProgramsDTO.Update.class})
	private BigDecimal classHour;
}