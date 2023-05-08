package com.dyys.hr.vo.train;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "员工参加培训列表vo")
public class EmployeeProgramsVO implements Serializable{
    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "培训名称")
    private String trainName;

    @ApiModelProperty(value = "培训形式 1.内部 2.外部")
    private Integer trainShape;

    @ApiModelProperty(value = "培训形式名称")
    private String trainShapeName;

    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    private Date endTime;
}
