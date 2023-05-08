package com.dyys.hr.dto.train;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 证书表
 *
 * @author sie sie
 * @since 1.0.0 2022-04-24
 */
@Data
@ApiModel(value = "证书表")
public class TrainBaseCertificateDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "证书ID")
	@NotNull(message = "证书ID不能为空", groups = { Update.class})
	private Integer id;

	@ApiModelProperty(value = "证书名称")
	@NotBlank(message = "证书名称不能为空", groups = {Update.class, Insert.class})
	private String certificateName;

	@ApiModelProperty(value = "证书编号")
	@NotBlank(message = "证书编号不能为空", groups = {Update.class, Insert.class})
	private String certificateNo;

	@ApiModelProperty(value = "颁发机构")
	@NotBlank(message = "颁发机构不能为空", groups = {Update.class, Insert.class})
	private String issuedInstitutions;

	@ApiModelProperty(value = "证书期限")
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
	private Date certificatePeriod;

	@ApiModelProperty(value = "证书文件列表")
	@Valid
	private List<FileDTO> fileList;

	@ApiIgnore
	public interface Insert{}
	@ApiIgnore
	public interface Update{}
}