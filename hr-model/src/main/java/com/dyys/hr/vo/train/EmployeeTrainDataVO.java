package com.dyys.hr.vo.train;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "员工培训数据")
public class EmployeeTrainDataVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "外部培训信息")
    private List<EmployeeTrainProgramsDataVO> externalList;

    @ApiModelProperty(value = "内部培训信息")
    private List<EmployeeTrainProgramsDataVO> internalList;

    @ApiModelProperty(value = "证书信息")
    private List<EmplCertificateListVO> certificateList;
}
