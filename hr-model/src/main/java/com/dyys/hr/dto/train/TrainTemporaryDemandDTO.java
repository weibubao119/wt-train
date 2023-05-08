package com.dyys.hr.dto.train;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 员工临时需求创建DTO
 *
 * @author sie sie
 * @since 1.0.0 2022-04-28
 */
@Data
@ApiModel(value = "员工页面-临时需求创建DTO")
public class TrainTemporaryDemandDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "临时需求ID")
	@NotNull(message = "临时需求ID不能为空", groups = {Update.class})
	private Long id;

	@ApiModelProperty(value = "公司code")
	@NotBlank(message = "公司code不能为空", groups = {Insert.class,Update.class})
	private String companyCode;

	@ApiModelProperty(value = "部门code")
	@NotBlank(message = "部门code不能为空", groups = {Insert.class,Update.class})
	private String departmentCode;

	@ApiModelProperty(value = "申请人ID")
	@NotBlank(message = "申请人ID不能为空", groups = {Insert.class,Update.class})
	private String initiator;

	@ApiModelProperty(value = "申请时间")
	private Date initiationTime;

	@ApiModelProperty(value = "附件文件列表")
	private List<FileDTO> fileList;

	@ApiModelProperty(value = "培训目的")
	private String trainingPurpose;

	@ApiModelProperty(value = "关联计划ID")
	@NotNull(message = "关联计划ID不能为空", groups = {Insert.class,Update.class})
	private Long planId;

	@ApiModelProperty(value = "需求详情数据")
	private TrainPlanDetailDTO planDetail;

	@ApiIgnore
	public interface Insert{}
	@ApiIgnore
	public interface Update{}
}