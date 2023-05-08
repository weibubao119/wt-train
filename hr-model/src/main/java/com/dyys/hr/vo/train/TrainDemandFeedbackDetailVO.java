package com.dyys.hr.vo.train;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "需求反馈数据填写接收前端参数VO")
public class TrainDemandFeedbackDetailVO implements Serializable {
    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "需求Id")
    private Long demandId;

    @ApiModelProperty(value = "需求单位编码")
    private String companyCode;

    @ApiModelProperty(value = "需求单位名称")
    private String companyName;

    @ApiModelProperty(value = "需求部门编码")
    private String departmentCode;

    @ApiModelProperty(value = "需求部门编码")
    private String departmentName;

    @ApiModelProperty(value = "培训名称")
    private String trainName;

    @ApiModelProperty(value = "课程类型常量")
    private Integer courseType;

    @ApiModelProperty(value = "课程类型名称")
    private String courseTypeName;

    @ApiModelProperty(value = "培训形式 1.内部 2.外部")
    private Integer trainShape;

    @ApiModelProperty(value = "培训形式名称")
    private String trainShapeName;

    @ApiModelProperty(value = "受训对象")
    private String trainSubject;

    @ApiModelProperty(value = "培训完成时间")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date trainCompleteTime;

    @ApiModelProperty(value = "培训需求依据常量id ")
    private Integer trainRequirements;

    @ApiModelProperty(value = "培训需求依据名称 ")
    private String trainRequirementsName;

    @ApiModelProperty(value = "参训人数")
    private Integer participantsNum;

    @ApiModelProperty(value = "培训讲师ID")
    private Long teacherId;

    @ApiModelProperty(value = "培训讲师名称")
    private String teacherName;

    @ApiModelProperty(value = "考核方法常量id ")
    private Integer assessmentMethod;

    @ApiModelProperty(value = "考核方法名称 ")
    private String assessmentMethodName;

    @ApiModelProperty(value = "经费预算")
    private BigDecimal outlay;

    @ApiModelProperty(value = "反馈人Id")
    private String feedbackUserId;

    @ApiModelProperty(value = "反馈人姓名")
    private String feedbackUserName;

    @ApiModelProperty(value = "备注")
    private String remark;

}
