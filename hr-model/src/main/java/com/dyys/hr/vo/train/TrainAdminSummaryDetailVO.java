package com.dyys.hr.vo.train;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "培训管理员总结详情VO")
public class TrainAdminSummaryDetailVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "总结id")
    private Long id;

    @ApiModelProperty(value = "培训项目id")
    private Long programsId;

    @ApiModelProperty(value = "目标参培率")
    private String targetParticipationRate;

    @ApiModelProperty(value = "实际参培率")
    private String actualParticipationRate;

    @ApiModelProperty(value = "目标成绩")
    private String targetResults;

    @ApiModelProperty(value = "实际成绩")
    private String actualResults;

    @ApiModelProperty(value = "培训总结")
    private String summary;
}
