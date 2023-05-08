package com.dyys.hr.dto.train;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 考试dto
 *
 * @author sie sie
 * @since 1.0.0 2022-04-28
 */
@Data
@ApiModel(value = "过程管理-培训项目考试表")
public class TrainExamDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "考试主键")
	@NotNull(message = "考试ID不能为空", groups = {Update.class})
	private Long id;

	@ApiModelProperty(value = "项目id")
	@NotNull(message = "项目id不能为空", groups = {Insert.class,Update.class})
	private Long programsId;

	@ApiModelProperty(value = "考试标题")
	@NotBlank(message = "考试标题不能为空", groups = {Insert.class,Update.class})
	private String title;

	@ApiModelProperty(value = "试卷模板id")
	private Integer paperId;

	@ApiModelProperty(value = "考试类型(1:线上考试，2:线下考试)")
	private Integer type;

	@ApiModelProperty(value = "关联课程id")
	@NotNull(message = "关联课程id不能为空", groups = {Insert.class,Update.class})
	private Long courseId;

	@ApiModelProperty(value = "考试总时长")
	@NotBlank(message = "考试总时长不能为空", groups = {Insert.class,Update.class})
	private String duration;

	@ApiModelProperty(value = "考试及格分")
	@NotBlank(message = "考试及格分不能为空", groups = {Insert.class,Update.class})
	private String passScore;

	@ApiModelProperty(value = "考试总分")
	@NotBlank(message = "考试总分不能为空", groups = {Insert.class,Update.class})
	private String totalScore;

	@ApiModelProperty(value = "是否限时(0: 不限时，1: 限时)")
	@NotNull(message = "是否限时不能为空", groups = {Insert.class,Update.class})
	private Integer isLimit;

	@ApiModelProperty(value = "开始时间")
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm")
	private Date startTime;

	@ApiModelProperty(value = "结束时间")
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm")
	private Date endTime;

	@ApiModelProperty(value = "考试人数")
	@NotNull(message = "考试人数不能为空", groups = {Insert.class,Update.class})
	private Integer userCount;

	@ApiModelProperty(value = "考试次数限制")
	private Integer examCount;

	@ApiModelProperty(value = "考试人员名单列表")
	@NotEmpty(message = "考试人员名单列表", groups = {Insert.class, Update.class})
	@Valid
	private List<TrainExaminerDTO> examinerList;

	@ApiIgnore
	public interface Insert{}
	@ApiIgnore
	public interface Update{}

}