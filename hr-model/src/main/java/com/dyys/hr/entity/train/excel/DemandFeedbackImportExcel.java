package com.dyys.hr.entity.train.excel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.enums.BooleanEnum;
import com.alibaba.excel.enums.poi.HorizontalAlignmentEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Desc
 * Create by yangye
 * Date 2022/11/21 11:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.ALWAYS)
public class DemandFeedbackImportExcel {
    @ColumnWidth(20)
    @ExcelProperty(value = "反馈人工号\n(必填)",index = 0)
    @ApiModelProperty(value = "反馈人工号")
    private String feedbackUserId;

    @ColumnWidth(20)
    @ExcelProperty(value = "反馈人姓名\n(必填)",index = 1)
    @ApiModelProperty(value = "反馈人姓名")
    private String feedbackUserName;

    @ColumnWidth(20)
    @ExcelProperty(value = "培训名称\n(必填)",index = 2)
    @ApiModelProperty(value = "培训名称")
    private String trainName;

    @ColumnWidth(15)
    @ExcelProperty(value = "培训形式\n(必填)",index = 3)
    @ApiModelProperty(value = "培训形式")
    private String trainShapeName;

    @ColumnWidth(20)
    @ExcelProperty(value = "课程类别\n(必填)",index = 4)
    @ApiModelProperty(value = "课程类别")
    private String courseTypeName;

    @ColumnWidth(15)
    @ExcelProperty(value = "受训对象\n(必填)",index = 5)
    @ApiModelProperty(value = "受训对象")
    private String trainSubject;

    @ColumnWidth(30)
    @ExcelProperty(value = "拟完成时间(必填)\n格式例如：2023/1/1",index = 6)
    @ApiModelProperty(value = "拟完成时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String trainCompleteTime;

    @ColumnWidth(25)
    @ExcelProperty(value = "培训需求依据\n(必填)",index = 7)
    @ApiModelProperty(value = "培训需求依据")
    private String trainRequirementsName;

    @ColumnWidth(15)
    @ExcelProperty(value = "参训人数\n(必填)",index = 8)
    @ApiModelProperty(value = "参训人数")
    private Integer participantsNum;

    @ColumnWidth(25)
    @ExcelProperty(value = "培训讲师编号",index = 9)
    @ApiModelProperty(value = "培训讲师编号")
    private String teacherNum;

    @ColumnWidth(25)
    @ExcelProperty(value = "培训讲师姓名",index = 10)
    @ApiModelProperty(value = "培训讲师姓名")
    private String teacherName;

    @ColumnWidth(15)
    @ExcelProperty(value = "考核方法",index = 11)
    @ApiModelProperty(value = "考核方法")
    private String assessmentMethodName;

    @ColumnWidth(15)
    @ExcelProperty(value = "经费预算\n(必填)",index = 12)
    private BigDecimal outlay;

    @ColumnWidth(30)
    @ExcelProperty(value = "备注",index = 13)
    private String remark;

    @ExcelIgnore
    @ApiModelProperty(value = "提示语")
    private String errMsg;
}
