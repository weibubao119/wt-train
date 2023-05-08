package com.dyys.hr.dto.train;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 过程管理-培训项目考勤规则表
 *
 * @author sie sie
 * @since 1.0.0 2022-06-06
 */
@Data
@ApiModel(value = "过程管理-培训项目考勤规则dto")
public class TrainAttendanceRulesDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "考勤记录ID")
	@NotNull(message = "缺少考勤记录ID",groups = {Update.class})
	@Min(value = 1L, message = "缺少考勤记录ID", groups = {Update.class})
	private Long id;

	@ApiModelProperty(value = "项目id")
	@NotNull(message = "项目id不能为空", groups = {Insert.class, Update.class})
	private Long programsId;

	@ApiModelProperty(value = "项目课表id")
	@NotNull(message = "项目课表id不能为空", groups = {Insert.class, Update.class})
	private Long programsCourseId;

	@ApiModelProperty(value = "课程id")
	@NotNull(message = "课程id不能为空", groups = {Insert.class, Update.class})
	private Long courseId;

	@ApiModelProperty(value = "日期")
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
	private Date date;

	@ApiModelProperty(value = "签到开始时间")
	@NotBlank(message = "签到开始时间不能为空", groups = {Insert.class, Update.class})
	private String startTime;

	@ApiModelProperty(value = "签到结束时间")
	@NotBlank(message = "签到结束时间不能为空", groups = {Insert.class, Update.class})
	private String endTime;

	@ApiModelProperty(value = "考勤人员名单列表")
	@NotEmpty(message = "考勤人员名单列表", groups = {Insert.class})
	@Valid
	private List<TrainAttendancePersonDTO> attendancePersonList;

	@ApiIgnore
	public interface Insert{}
	@ApiIgnore
	public interface Update{}
}