package com.dyys.hr.dto.train;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * 追加反馈人视图
 *
 * @author sie sie
 * @since 1.0.0 2022-04-28
 */
@Data
@ApiModel(value = "需求管理-追加反馈人dto")
public class TrainDemandAddFeedbackDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "需求ID")
	@NotNull(message = "需求ID不能为空", groups = {Insert.class,Update.class})
	private Long demandId;

	@ApiModelProperty(value = "反馈人ID")
	@NotBlank(message = "反馈人ID不能为空", groups = {Insert.class,Update.class})
	private String feedbackUserId;

	@ApiModelProperty(value = "公司编码")
	@NotBlank(message = "公司编码不能为空", groups = {Insert.class,Update.class})
	private String companyCode;

	@ApiModelProperty(value = "岗位编码")
	@NotBlank(message = "岗位编码不能为空", groups = {Insert.class,Update.class})
	private String departmentCode;

	@ApiIgnore
	public interface Insert{}
	@ApiIgnore
	public interface Update{}
}