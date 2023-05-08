package com.dyys.hr.dto.train;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * 评估问卷-主表
 *
 * @author sie sie
 * @since 1.0.0 2022-06-20
 */
@Data
@ApiModel(value = "评估问卷-主表dto")
public class QuestionnaireDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键ID")
	@NotNull(message = "主键ID不能为空", groups = {Update.class})
	private Long id;

	@ApiModelProperty(value = "评估标题")
	@NotBlank(message = "评估标题不能为空", groups = {Insert.class, Update.class})
	private String title;

	@ApiModelProperty(value = "评估说明")
	private String instructions;

	@ApiModelProperty(value = "模板json")
	@NotBlank(message = "模板json不能为空", groups = {Insert.class, Update.class})
	private String templateJson;

	@ApiModelProperty(value = "状态 1.发布 0.未发布(保存)")
	@NotNull(message = "状态", groups = {Update.class})
	private Integer status;

	@ApiIgnore
	public interface Insert{}
	@ApiIgnore
	public interface Update{}
}