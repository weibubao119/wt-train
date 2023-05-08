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
@ApiModel(value = "管理员负责培训项目列表vo")
public class AdminResponsibleProgramsVO implements Serializable{
    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "培训名称")
    private String trainName;

    @ApiModelProperty(value = "培训形式 1.内部 2.外部")
    private Integer trainShape;

    @ApiModelProperty(value = "培训形式名称")
    private String trainShapeName;

    @ApiModelProperty(value = "开始时间")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date endTime;

    @ApiModelProperty(value = "学习人数")
    private Integer participantsNum;
}
