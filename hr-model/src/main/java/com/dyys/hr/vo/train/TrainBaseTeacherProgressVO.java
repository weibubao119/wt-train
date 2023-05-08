package com.dyys.hr.vo.train;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 资源管理-讲师历程列表vo
 *
 * @author sie sie
 * @since 1.0.0 2022-04-27
 */
@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "资源管理-讲师历程列表vo")
public class TrainBaseTeacherProgressVO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键")
	private Long id;

	@ApiModelProperty(value = "讲师id")
	private Long teacherId;

	@ApiModelProperty(value = " 讲师级别常量id")
	private Integer level;

	@ApiModelProperty(value = " 讲师级别名称")
	private String levelName;

	@ApiModelProperty(value = "开始时间")
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
	private Date startTime;

	@ApiModelProperty(value = "结束时间")
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
	private Date endTime;

	@ApiModelProperty(value = "课酬标准（元/学时）")
	private Integer fee;

	@ApiModelProperty(value = "说明")
	private String instructions;
}