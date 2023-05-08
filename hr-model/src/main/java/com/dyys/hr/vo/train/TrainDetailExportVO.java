package com.dyys.hr.vo.train;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "excel导出反馈明细前端接收参数VO")
public class TrainDetailExportVO implements Serializable {
    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "公司名称")
    private String organizationName;

    @ApiModelProperty(value = "部门名称")
    private String deptName;

    /**
     * 培训类型
     */
    @ApiModelProperty(value = "培训类型")
    private Integer trainType;

    /**
     * 培训名称
     */
    @ApiModelProperty(value = "培训名称")
    private String trainName;

    /**
     * 培训时间
     */
    @ApiModelProperty(value = "培训时间")
    private String trainTime;

    /**
     * 培训地址
     */
    @ApiModelProperty(value = "培训地址")
    private String trainAddress;

    /**
     * 培训形式（内部培训/外部培训）
     */
    @ApiModelProperty(value = "培训形式（内部培训/外部培训）")
    private Integer trainShape;

    /**
     * 培训内容
     */
    @ApiModelProperty(value = "培训内容")
    private String trainContent;

    /**
     * 学习人数
     */
    @ApiModelProperty(value = "学习人数")
    private Integer studyPersonnum;

    /**
     * 培训总学时
     */
    @ApiModelProperty(value = "培训总学时")
    private String courseHour;

    /**
     * 经费预算
     */
    @ApiModelProperty(value = "经费预算")
    private BigDecimal outlay;

    /**
     * 反馈人
     */
    @ApiModelProperty(value = "反馈人")
    private String userName;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 格式化日期
     */
    @ApiModelProperty(value = "格式化日期")
    private String formatDate;

}
