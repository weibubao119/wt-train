package com.dyys.hr.dto.train;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;


/**
 * 过程管理-员工证书dto
 *
 * @author sie sie
 * @since 1.0.0 2022-04-28
 */
@Data
@ApiModel(value = "过程管理-员工证书dto")
public class TrainEmployeeCertificateDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "证书id")
	@NotNull(message = "证书id不能为空", groups = {Update.class})
	private Long id;

	@ApiModelProperty(value = "员工工号")
	@NotBlank(message = "员工工号不能为空", groups = {Insert.class,Update.class})
	private String emplId;

	@ApiModelProperty(value = "员工姓名")
	@NotBlank(message = "员工姓名不能为空", groups = {Insert.class,Update.class})
	private String emplName;

	@ApiModelProperty(value = "证书编号")
	// @NotBlank(message = "证书编号不能为空", groups = {Insert.class,Update.class})
	private String certificateNo;

	@ApiModelProperty(value = "证书名称")
	@NotBlank(message = "证书名称不能为空", groups = {Insert.class,Update.class})
	private String certificateName;

	@ApiModelProperty(value = "证书等级")
	// @NotBlank(message = "证书等级不能为空", groups = {Insert.class,Update.class})
	private String certificateLevel;

	@ApiModelProperty(value = "发证机构名称")
	@NotBlank(message = "发证机构名称不能为空", groups = {Insert.class,Update.class})
	private String issuingAgencyName;

	@ApiModelProperty(value = "获证日期")
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
	@NotNull(message = "获证日期不能为空",groups = {Insert.class, Update.class})
	private Date startDate;

	@ApiModelProperty(value = "到期日期")
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
	// @NotNull(message = "到期日期不能为空",groups = {Insert.class, Update.class})
	private Date endDate;

	@ApiModelProperty(value = "培训项目编号")
	private String trainNumber;

	@ApiModelProperty(value = "培训项目名称")
	private String trainName;

	@ApiIgnore
	public interface Insert{}
	@ApiIgnore
	public interface Update{}
}