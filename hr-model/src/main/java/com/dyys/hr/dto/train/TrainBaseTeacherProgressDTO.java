package com.dyys.hr.dto.train;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 资源管理-讲师历程
 *
 * @author sie sie
 * @since 1.0.0 2022-04-27
 */
@Data
@ApiModel(value = "资源管理-讲师历程dto")
public class TrainBaseTeacherProgressDTO implements Serializable {
    private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = " 讲师级别")
	// @NotNull(message = "请选择讲师级别", groups = { TrainBaseTeacherDTO.Insert.class,TrainBaseTeacherDTO.Update.class})
	private Integer level;

	@ApiModelProperty(value = "开始时间")
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
	// @NotNull(message = "请选择开始时间", groups = { TrainBaseTeacherDTO.Insert.class,TrainBaseTeacherDTO.Update.class})
	private Date startTime;

	@ApiModelProperty(value = "结束时间")
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
	// @NotNull(message = "请选择结束时间", groups = { TrainBaseTeacherDTO.Insert.class,TrainBaseTeacherDTO.Update.class})
	private Date endTime;

	@ApiModelProperty(value = "课酬标准（元/学时）")
	// @NotNull(message = "课酬标准不能为空", groups = { TrainBaseTeacherDTO.Insert.class,TrainBaseTeacherDTO.Update.class})
	private Integer fee;

	@ApiModelProperty(value = "说明")
	// @NotBlank(message = "说明不能为空", groups = { TrainBaseTeacherDTO.Insert.class,TrainBaseTeacherDTO.Update.class})
	private String instructions;
}