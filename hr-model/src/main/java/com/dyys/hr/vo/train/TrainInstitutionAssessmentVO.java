package com.dyys.hr.vo.train;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "机构评估记录展示参数")
public class TrainInstitutionAssessmentVO {

    @ApiModelProperty("评估记录ID")
    private Long id;

    @ApiModelProperty("机构ID")
    private Long institutionId;

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

    @ApiModelProperty("评估得分")
    private BigDecimal score;

    @ApiModelProperty("状态：1未开始，2进行中，3已完成，4已取消")
    private Integer status;
}
