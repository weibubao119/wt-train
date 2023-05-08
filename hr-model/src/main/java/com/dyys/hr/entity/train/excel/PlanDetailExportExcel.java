package com.dyys.hr.entity.train.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Desc
 * Create by yangye
 * Date 2022/11/29 16:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlanDetailExportExcel {
    @ColumnWidth(20)
    @ExcelProperty(value = "组织单位编码",index = 0)
    private String companyCode;

    @ColumnWidth(20)
    @ExcelProperty(value = "组织单位名称",index = 1)
    private String companyName;

    @ColumnWidth(15)
    @ExcelProperty(value = "部门编码",index = 2)
    private String departmentCode;

    @ColumnWidth(15)
    @ExcelProperty(value = "部门名称",index = 3)
    private String departmentName;

    @ColumnWidth(15)
    @ExcelProperty(value = "反馈人工号",index = 4)
    private String feedbackUserId;

    @ColumnWidth(15)
    @ExcelProperty(value = "反馈人姓名",index = 5)
    private String feedbackUserName;

    @ColumnWidth(15)
    @ExcelProperty(value = "培训名称",index = 6)
    private String trainName;

    @ColumnWidth(15)
    @ExcelProperty(value = "课程类别",index = 7)
    private String courseTypeName;

    @ColumnWidth(15)
    @ExcelProperty(value = "培训形式",index = 8)
    private String trainShapeName;

    @ColumnWidth(15)
    @ExcelProperty(value = "受训对象",index = 9)
    private String trainSubject;

    @ColumnWidth(15)
    @ExcelProperty(value = "拟完成时间",index = 10)
    private String  trainCompleteTime;

    @ColumnWidth(20)
    @ExcelProperty(value = "培训需求依据",index = 11)
    private String trainRequirementsName;

    @ColumnWidth(15)
    @ExcelProperty(value = "参训人数",index = 12)
    private Integer participantsNum;

    @ColumnWidth(20)
    @ExcelProperty(value = "培训讲师编码",index = 13)
    private String teacherNumber;

    @ColumnWidth(20)
    @ExcelProperty(value = "培训讲师姓名",index = 14)
    private String teacherName;

    @ColumnWidth(15)
    @ExcelProperty(value = "经费预算",index = 15)
    private String outlay;

    @ColumnWidth(15)
    @ExcelProperty(value = "考核方法",index = 16)
    private String assessmentMethodName;

    @ColumnWidth(20)
    @ExcelProperty(value = "备注",index = 17)
    private String remark;
}
