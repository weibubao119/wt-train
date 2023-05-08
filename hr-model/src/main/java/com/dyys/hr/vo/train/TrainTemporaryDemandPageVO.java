package com.dyys.hr.vo.train;

import com.dyys.hr.dto.train.PersonDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "临时需求列表vo")
public class TrainTemporaryDemandPageVO implements Serializable{
    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "申请内容")
    private String trainingPurpose;

    @ApiModelProperty(value = "申请时间")
    private Date initiationTime;

    @ApiModelProperty(value = "申请状态")
    private Integer  status;

    @ApiModelProperty(value = "状态名称")
    private String statusName;
}
