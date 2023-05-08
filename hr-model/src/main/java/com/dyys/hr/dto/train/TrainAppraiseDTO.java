package com.dyys.hr.dto.train;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.Future;
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
@ApiModel(value = "过程管理-培训项目评估表")
public class TrainAppraiseDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "评估主键")
	@NotNull(message = "评估ID不能为空", groups = {Update.class})
	private Long id;

	@ApiModelProperty(value = "项目id")
	@NotNull(message = "项目id不能为空", groups = {Insert.class,Update.class})
	private Long programsId;

	@ApiModelProperty(value = "考试标题")
	@NotBlank(message = "考试标题不能为空", groups = {Insert.class,Update.class})
	private String title;

	@ApiModelProperty(value = "问卷模板id")
	@NotNull(message = "问卷模板id不能为空", groups = {Insert.class,Update.class})
	private Long questionnaireId;

	@ApiModelProperty(value = "关联课程id")
	@NotNull(message = "关联课程id不能为空", groups = {Insert.class,Update.class})
	private Long courseId;

	@ApiModelProperty(value = "开始时间")
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm")
	@Future(message = "开始时间不能小于当前时间", groups = {Insert.class, Update.class})
	private Date startTime;

	@ApiModelProperty(value = "结束时间")
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm")
	@Future(message = "结束时间不能小于当前时间", groups = {Insert.class, Update.class})
	private Date endTime;

	@ApiModelProperty(value = "参评人员名单列表")
	@NotEmpty(message = "参评人员名单列表", groups = {Insert.class, Update.class})
	@Valid
	private List<TrainAppraisePersonDTO> appraisePersonList;

	@ApiIgnore
	public interface Insert{}
	@ApiIgnore
	public interface Update{}

}