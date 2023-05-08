package com.dyys.hr.dto.train;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * 过程管理-培训项目讲师表
 *
 * @author sie sie
 * @since 1.0.0 2022-05-18
 */
@Data
@ApiModel(value = "过程管理-培训项目讲师表")
public class TrainProgramsTeacherDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "讲师ID")
	@NotNull(message = "讲师ID不能为空", groups = {TrainProgramsDTO.Insert.class,TrainProgramsDTO.Update.class})
	private Long teacherId;
}