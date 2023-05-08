package com.dyys.hr.entity.train.excel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.dyys.hr.annotation.IsNeeded;
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
 * Date 2022/9/16 11:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlanDetailExcel {
    @ColumnWidth(15)
    @ExcelProperty(value = "反馈人工号\n(必填)",index = 0)
    @ApiModelProperty(value = "反馈人工号")
    @IsNeeded
    private String feedbackUserId;

    @ColumnWidth(15)
    @ExcelProperty(value = "反馈人姓名\n(必填)",index = 1)
    @ApiModelProperty(value = "反馈人姓名")
    @IsNeeded
    private String feedbackUserName;

    @ColumnWidth(15)
    @ExcelProperty(value = "培训名称\n(必填)",index = 2)
    @ApiModelProperty(value = "培训名称")
    @IsNeeded
    private String trainName;

    @ColumnWidth(15)
    @ExcelProperty(value = "课程类别\n(必填)",index = 3)
    @ApiModelProperty(value = "课程类别")
    @IsNeeded
    private String courseTypeName;

    @ColumnWidth(15)
    @ExcelProperty(value = "培训形式\n(必填)",index = 4)
    @ApiModelProperty(value = "培训形式")
    @IsNeeded
    private String trainShapeName;

    @ColumnWidth(15)
    @ExcelProperty(value = "受训对象\n(必填)",index = 5)
    @ApiModelProperty(value = "受训对象")
    @IsNeeded
    private String trainSubject;

    @ColumnWidth(30)
    @ExcelProperty(value = "拟完成时间(必填)\n格式例如：2023/1/1",index = 6)
    @ApiModelProperty(value = "拟完成时间")
    @IsNeeded
    private String trainCompleteTime;

    @ColumnWidth(20)
    @ExcelProperty(value = "培训需求依据",index = 7)
    @ApiModelProperty(value = "培训需求依据")
    @IsNeeded
    private String trainRequirementsName;

    @ColumnWidth(15)
    @ExcelProperty(value = "参训人数\n(必填)",index = 8)
    @ApiModelProperty(value = "参训人数")
    @IsNeeded
    private Integer participantsNum;

    @ColumnWidth(30)
    @ExcelProperty(value = "培训讲师编码\n若填写培训讲师则必填",index = 9)
    @ApiModelProperty(value = "培训讲师编码")
    @IsNeeded
    private String teacherNumber;

    @ColumnWidth(20)
    @ExcelProperty(value = "培训讲师姓名",index = 10)
    @ApiModelProperty(value = "培训讲师姓名")
    @IsNeeded
    private String teacherName;

    @ColumnWidth(15)
    @ExcelProperty(value = "经费预算\n(必填)",index = 11)
    @ApiModelProperty(value = "经费预算")
    @IsNeeded
    private BigDecimal outlay;

    @ColumnWidth(15)
    @ExcelProperty(value = "考核方法",index = 12)
    @ApiModelProperty(value = "考核方法")
    @IsNeeded
    private String assessmentMethodName;

    @ColumnWidth(40)
    @ExcelProperty(value = "协办单位编码\n多个单位编码之间用\",\"号隔开\n例如：1000001,1000002",index = 13)
    @ApiModelProperty(value = "协办单位编码")
    @IsNeeded
    private String orgDeptIds;

    @ColumnWidth(20)
    @ExcelProperty(value = "备注",index = 14)
    @ApiModelProperty(value = "备注")
    @IsNeeded
    private String remark;

    @ColumnWidth(35)
    @ExcelProperty(value = "参训人员工号\n多个工号之间用\",\"号隔开\n例如：00001,00002",index = 15)
    @ApiModelProperty(value = "参训人员工号")
    @IsNeeded
    private String participants;

    @ExcelIgnore
    @ApiModelProperty(value = "提示语")
    private String errMsg;
}
