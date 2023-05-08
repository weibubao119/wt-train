package com.dyys.hr.vo.train;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Desc
 * Create by yangye
 * Date 2022/9/18 17:01
 */
@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "员工机构评估记录VO")
public class EmplInstitutionAssessVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("评估记录ID")
    private Long assessmentId;

    @ApiModelProperty("培训机构ID")
    private Long institutionId;

    @ApiModelProperty("培训机构名称")
    private String institutionName;

    @ApiModelProperty("评估标题")
    private String title;

    @ApiModelProperty("问卷模板ID")
    private Long questionnaireId;

    @ApiModelProperty(value = "问卷模板名称")
    private String questionnaireName;

    @ApiModelProperty("年度")
    private String yearly;

    @ApiModelProperty("评估开始时间")
    private Date startTime;

    @ApiModelProperty("评估结束时间")
    private Date endTime;

    @ApiModelProperty("提交状态：0未完成 1.已完成")
    private Integer status;

    @ApiModelProperty("提交状态名称")
    private String statusName;
}
