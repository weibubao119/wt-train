package com.dyys.hr.vo.train;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author WSJ
 * @date 2022/06/16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "培训项目员工证书列表vo")
public class TrainEmployeeCertificatePageVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "员工工号")
    private String emplId;

    @ApiModelProperty(value = "员工姓名")
    private String emplName;

    @ApiModelProperty(value = "所属组织编码")
    private String deptId;

    @ApiModelProperty(value = "所属组织名称")
    private String deptPathName;

    @ApiModelProperty(value = "公司编码")
    private String company;

    @ApiModelProperty(value = "公司名称")
    private String companyDescr;

    @ApiModelProperty(value = "证书编号")
    private String certificateNo;

    @ApiModelProperty(value = "证书名称")
    private String certificateName;

    @ApiModelProperty(value = "证书等级")
    private String certificateLevel;

    @ApiModelProperty(value = "发证机构名称")
    private String issuingAgencyName;

    @ApiModelProperty(value = "培训项目名称")
    private String trainName;

    @ApiModelProperty(value = "证书开始时期")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date startDate;

    @ApiModelProperty(value = "证书截止时期")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date endDate;
}
