package com.dyys.hr.dto.train;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 过程管理-培训项目计划表
 *
 * @author sie sie
 * @since 1.0.0 2022-05-18
 */
@Data
@ApiModel(value = "过程管理-培训项目计划表")
public class TrainProgramsPlanDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "课程id")
	@NotNull(message = "培训计划表-课程不能为空", groups = {TrainProgramsDTO.Insert.class, TrainProgramsDTO.Update.class})
	private Long courseId;

	@ApiModelProperty(value = "开始时间")
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
	private Date startTime;

	@ApiModelProperty(value = "结束时间")
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
	@Future(message = "培训计划表-结束时间必须大于当前时间", groups = {TrainProgramsDTO.Insert.class, TrainProgramsDTO.Update.class})
	private Date endTime;

	@ApiModelProperty(value = "上课时间")
//	@NotBlank(message = "培训计划表-上课时间不能为空", groups = {TrainProgramsDTO.Insert.class, TrainProgramsDTO.Update.class})
	private String schooltime;

	@ApiModelProperty(value = "场地")
//	@NotBlank(message = "培训计划表-场地说明不能为空", groups = {TrainProgramsDTO.Insert.class, TrainProgramsDTO.Update.class})
	private String site;

	@ApiModelProperty(value = "机构id")
	@NotNull(message = "培训计划表-机构id不能为空", groups = {TrainProgramsDTO.Insert.class, TrainProgramsDTO.Update.class})
	private Long institutionId;

	@ApiModelProperty(value = "学时")
	@NotNull(message = "培训计划表-学时不能为空", groups = {TrainProgramsDTO.Insert.class, TrainProgramsDTO.Update.class})
	private BigDecimal classHour;

	@ApiModelProperty(value = "学习形式id")
	@NotNull(message = "培训计划表-学习形式id不能为空", groups = {TrainProgramsDTO.Insert.class, TrainProgramsDTO.Update.class})
	private Integer learningForm;

	@ApiModelProperty(value = "考核方式id 1.考试 2.考察")
	@NotNull(message = "培训计划表-考核方式id不能为空", groups = {TrainProgramsDTO.Insert.class, TrainProgramsDTO.Update.class})
	private Integer examineForm;

	@ApiModelProperty(value = "费用")
//	@NotNull(message = "培训计划表-费用不能为空", groups = {TrainProgramsDTO.Insert.class, TrainProgramsDTO.Update.class})
	private BigDecimal outlay;

	@ApiModelProperty(value = "费用说明")
//	@NotBlank(message = "培训计划表-费用说明不能为空", groups = {TrainProgramsDTO.Insert.class, TrainProgramsDTO.Update.class})
	private String outlayText;
}