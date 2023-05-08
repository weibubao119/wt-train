package com.dyys.hr.vo.train;

import com.dyys.hr.dto.train.FileDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "员工相关数据vo")
public class EmployeeRelateDataVO implements Serializable {
    @ApiModelProperty(value = "进行中培训数量")
    private Integer trainingInProgressNum ;

    @ApiModelProperty(value = "已完成培训数量")
    private Integer trainingCompletedNum ;

    @ApiModelProperty(value = "已获证书数量")
    private Integer obtainedCertificatesNum  ;

    @ApiModelProperty(value = "到期证书数量")
    private Integer dueCertificatesNum ;

}
