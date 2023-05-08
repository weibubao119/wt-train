package com.dyys.hr.dto.train;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;

import java.io.Serializable;

/**
 * 过程管理-培训项目管理员总结表
 *
 * @author sie sie
 * @since 1.0.0 2022-06-14
 */
@Data
@ApiModel(value = "过程管理-培训项目管理员总结表")
public class TrainAdminSummaryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "培训项目id")
	private Long programsId;

	@ApiModelProperty(value = "目标参培率")
	private String targetParticipationRate;

	@ApiModelProperty(value = "实际参培率")
	private String actualParticipationRate;

	@ApiModelProperty(value = "目标成绩")
	private String targetResults;

	@ApiModelProperty(value = "实际成绩")
	private String actualResults;

	@ApiModelProperty(value = "培训总结")
	private String summary;

	@ApiIgnore
	public interface Update{}
}