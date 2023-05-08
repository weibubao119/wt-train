package com.dyys.hr.vo.train;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author wsj
 * @projectName hr-recruit
 * @date 2023/2/914:55
 */
@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "年度培训计划执行vo")
public class TrainPlanYearImplementVO implements Serializable {
    @ColumnWidth(15)
    @ExcelProperty(value = "序号",index = 0)
    @ApiModelProperty(value = "序号")
    private Long id;

    @ColumnWidth(15)
    @ExcelProperty(value = "组织单位",index = 1)
    @ApiModelProperty(value = "组织单位")
    private String companyDescr;

    @ColumnWidth(15)
    @ExcelProperty(value = "需求部门",index = 2)
    @ApiModelProperty(value = "需求部门")
    private String deptDescr;

    @ColumnWidth(15)
    @ExcelProperty(value = "年度",index = 3)
    @ApiModelProperty(value = "年度")
    private String planYear;

    @ColumnWidth(15)
    @ExcelProperty(value = "培训项目编号",index = 4)
    @ApiModelProperty(value = "培训项目编号")
    private String number;

    @ColumnWidth(15)
    @ExcelProperty(value = "培训项目名称",index = 5)
    @ApiModelProperty(value = "培训项目名称")
    private String trainName;

    @ColumnWidth(15)
    @ExcelProperty(value = "计划参训人数",index = 6)
    @ApiModelProperty(value = "计划参训人数")
    private Integer participantsNum;

    @ColumnWidth(15)
    @ExcelProperty(value = "实际参加人数",index = 7)
    @ApiModelProperty(value = "实际参加人数")
    private Integer actualParticipantsNum;

    @ColumnWidth(15)
    @ExcelProperty(value = "拟完成时间",index = 8)
    @ApiModelProperty(value = "拟完成时间")
    private String trainCompleteTime;

    @ColumnWidth(15)
    @ExcelProperty(value = "课程完成时间",index = 9)
    @ApiModelProperty(value = "课程完成时间")
    private String courseEndTime;

    @ColumnWidth(15)
    @ExcelProperty(value = "进度",index = 10)
    @ApiModelProperty(value = "进度")
    private String statusName;

    @ColumnWidth(15)
    @ExcelProperty(value = "经费预算",index = 11)
    @ApiModelProperty(value = "经费预算")
    private BigDecimal outlay;

    @ColumnWidth(15)
    @ExcelProperty(value = "实际费用",index = 12)
    @ApiModelProperty(value = "实际费用")
    private BigDecimal actualCost;

    @ColumnWidth(15)
    @ExcelProperty(value = "费用执行率",index = 13)
    @ApiModelProperty(value = "费用执行率")
    private String costRate;
}