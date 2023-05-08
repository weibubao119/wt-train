package com.dyys.hr.vo.train;

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
 * 培训证书接收数据实体类
 * @author JUCHUAN LI
 * @date 2019/06/26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "证书管理")
public class TrainBaseCertificateVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "证书编号")
    private String certificateNo;

    @ApiModelProperty(value = "证书名称")
    private String certificateName;

    @ApiModelProperty(value = "颁发机构")
    private String issuedInstitutions;

    @ApiModelProperty(value = "证书期限")
    private Date certificatePeriod;

    @ApiModelProperty(value = "文件列表")
    private String fileList;
}
