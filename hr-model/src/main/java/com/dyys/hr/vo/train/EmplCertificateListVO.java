package com.dyys.hr.vo.train;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "学员档案-获证记录VO")
public class EmplCertificateListVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "证书编号")
    private String certificateNo;

    @ApiModelProperty(value = "证书名称")
    private String certificateName;

    @ApiModelProperty(value = "证书等级")
    private String certificateLevel;

    @ApiModelProperty(value = "获证日期")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date startDate;

    @ApiModelProperty(value = "到期日期")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date endDate;

    @ApiModelProperty(value = "发证机构")
    private String issuingAgencyName;
}
