package com.dyys.hr.vo.train;

import com.dyys.hr.dto.train.FileDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 需求管理-需求信息表
 *
 * @author sie sie
 * @since 1.0.0 2022-04-28
 */
@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "员工页面-临时需求详情vo")
public class TrainTemporaryDemandDetailVO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "需求id")
	private Long id;

	@ApiModelProperty(value = "计划详情id")
	private Long planDetailId;

	@ApiModelProperty(value = "公司编码")
	private String companyCode;

	@ApiModelProperty(value = "公司名称")
	private String companyName;

	@ApiModelProperty(value = "部门编码")
	private String departmentCode;

	@ApiModelProperty(value = "部门名称")
	private String departmentName;

	@ApiModelProperty(value = "关联计划id")
	private Long planId;

	@ApiModelProperty(value = "计划标题")
	private String planTitle;

	@ApiModelProperty(value = "申请人")
	private String initiator;

	@ApiModelProperty(value = "申请人姓名")
	private String initiatorName;

	@ApiModelProperty(value = "申请时间")
	private Date initiationTime;

	@ApiModelProperty(value = "培训目的")
	private String trainingPurpose;

	@ApiModelProperty(value = "审核状态 1.审批中 2.已通过 3已驳回")
	private Integer status;

	@ApiModelProperty(value = "附件列表")
	private List<FileDTO> fileList;

	@ApiModelProperty(value = "需求详情数据")
	private TrainPlanDetailVO planDetail;

	@ApiModelProperty(value = "审批进度")
	private List<TrainApproveVO> approveList;

}