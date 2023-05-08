package com.dyys.hr.dto.train;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * 需求管理-需求反馈人员表
 *
 * @author sie sie
 * @since 1.0.0 2022-04-28
 */
@Data
@ApiModel(value = "需求管理-需求反馈人员dto")
public class TrainDemandFeedbackDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "反馈人ID")
	@NotNull(message = "反馈人ID不能为空", groups = {TrainDemandCollectDTO.Insert.class,TrainDemandCollectDTO.Update.class})
	private String feedbackUserId;

	@ApiModelProperty(value = "公司编码")
	@NotNull(message = "公司编码不能为空", groups = {TrainDemandCollectDTO.Insert.class,TrainDemandCollectDTO.Update.class})
	private String companyCode;

	@ApiModelProperty(value = "岗位编码")
	@NotNull(message = "岗位编码不能为空", groups = {TrainDemandCollectDTO.Insert.class,TrainDemandCollectDTO.Update.class})
	private String departmentCode;

	@ApiIgnore
	public interface Insert{}
	@ApiIgnore
	public interface Update{}
}