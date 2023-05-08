package com.dyys.hr.dto.train;

import com.dyys.hr.vo.common.OrgDeptVO;
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
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * 计划dto
 *
 * @author sie sie
 * @since 1.0.0 2022-04-28
 */
@Data
@ApiModel(value = "过程管理-培训项目表")
public class TrainProgramsDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "项目主键")
	@NotNull(message = "项目ID不能为空", groups = {Update.class})
	private Long id;

	@ApiModelProperty(value = "培训名称")
	@NotBlank(message = "培训名称不能为空", groups = {Insert.class, Update.class})
	private String trainName;

	@ApiModelProperty(value = "大计划id")
	@NotNull(message = "请选择大计划", groups = {Insert.class,Update.class})
	@Min(value = 1, message = "请选择大计划", groups = {Insert.class,Update.class})
	private Long planId;

	@ApiModelProperty(value = "小计划id")
	@NotNull(message = "请选择小计划", groups = {Insert.class,Update.class})
	@Min(value = 1, message = "请选择小计划", groups = {Insert.class,Update.class})
	private Long planDetailId;

	@ApiModelProperty(value = "公司编码")
	@NotBlank(message = "公司不能为空", groups = {Insert.class, Update.class})
	private String companyCode;

	@ApiModelProperty(value = "组织单位编码")
	@NotBlank(message = "组织单位编码不能为空", groups = {Insert.class, Update.class})
	private String deptId;

	@ApiModelProperty(value = "协办单位列表")
	private List<OrgDeptVO> coOrganizer;

	@ApiModelProperty(value = "开始时间")
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
	private Date startTime;

	@ApiModelProperty(value = "结束时间")
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
	private Date endTime;

	@ApiModelProperty(value = "受训对象")
	@NotBlank(message = "受训对象不能为空", groups = {Insert.class, Update.class})
	private String trainSubject;

	@ApiModelProperty(value = "参训人数")
	@NotNull(message = "参训人数不能为空", groups = {Insert.class,Update.class})
	private Integer participantsNum;

	@ApiModelProperty(value = "培训形式 1.内部 2.外部")
	@NotNull(message = "培训形式不能为空", groups = {Insert.class,Update.class})
	private Integer trainShape;

	@ApiModelProperty(value = "经费预算")
	@NotNull(message = "经费预算不能为空", groups = {Insert.class,Update.class})
	private BigDecimal outlay;

	@ApiModelProperty(value = "负责人列表")
	@NotEmpty(message = "负责人列表不能为空", groups = {Insert.class, Update.class})
	private List<PersonDTO> principalList;

	@ApiModelProperty(value = "培训计划列表")
	@NotEmpty(message = "培训计划列表不能为空", groups = {Insert.class, Update.class})
	@Valid
	private List<TrainProgramsPlanDTO> programsPlanList;

	@ApiModelProperty(value = "培训课表列表")
	@NotEmpty(message = "训课表列表不能为空", groups = {Insert.class, Update.class})
	@Valid
	private List<TrainProgramsCourseDTO> programsCourseList;

	@ApiModelProperty(value = "培训讲师列表")
	@NotEmpty(message = "培训讲师列表不能为空", groups = {Insert.class, Update.class})
	@Valid
	private List<TrainProgramsTeacherDTO> programsTeacherList;

	@ApiModelProperty(value = "培训参训名单列表")
	@NotEmpty(message = "培训参训名单列表不能为空", groups = {Insert.class})
	@Valid
	private List<TrainProgramsParticipantsDTO> programsParticipantsList;

	@ApiIgnore
	public interface Insert{}
	@ApiIgnore
	public interface Update{}

}