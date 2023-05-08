package com.dyys.hr.dto.train;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * 追加反馈人视图
 *
 * @author sie sie
 * @since 1.0.0 2022-04-28
 */
@Data
@ApiModel(value = "关联子需求视图")
public class TrainDemandRelateDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "需求ID")
	@NotNull(message = "需求ID不能为空", groups = {Insert.class})
	private Long demandId;

	@ApiModelProperty(value = "子需求ID")
	@NotNull(message = "子需求ID不能为空", groups = {Insert.class})
	private Long relateDemandId;

	@ApiIgnore
	public interface Insert{}
}