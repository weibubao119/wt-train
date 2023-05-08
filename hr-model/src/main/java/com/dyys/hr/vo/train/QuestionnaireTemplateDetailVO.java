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
@ApiModel(value = "评估问卷-模板详情vo")
public class QuestionnaireTemplateDetailVO implements Serializable {
    @ApiModelProperty(value = "计划编号")
    private Long id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "公司编码")
    private String companyCode;

    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @ApiModelProperty(value = "发起人")
    private String initiator;

    @ApiModelProperty(value = "发起人姓名")
    private String initiatorName;

    @ApiModelProperty(value = "计划类型常量ID")
    private Integer type;

    @ApiModelProperty(value = "计划类型名称")
    private String typeName;

    @ApiModelProperty(value = "计划年度")
    private Date planYear;

    @ApiModelProperty(value = "附件列表")
    private List<FileDTO> fileList;

    @ApiModelProperty(value = "计划详情列表")
    private List<TrainPlanDetailVO> planDetailList;

}
