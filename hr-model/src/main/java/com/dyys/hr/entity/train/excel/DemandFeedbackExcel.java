package com.dyys.hr.entity.train.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DemandFeedbackExcel {
    @ColumnWidth(30)
    @ExcelProperty(value = "需求单位",index = 0)
    private String companyDescr;

    @ColumnWidth(30)
    @ExcelProperty(value = "需求部门",index = 1)
    private String deptDescr;

    @ColumnWidth(30)
    @ExcelProperty(value = "反馈人编号",index = 2)
    private String feedbackUserId;

    @ColumnWidth(30)
    @ExcelProperty(value = "反馈人姓名",index = 3)
    private String feedbackUserName;

    @ColumnWidth(30)
    @ExcelProperty(value = "培训名称",index = 4)
    private String trainName;

    @ColumnWidth(30)
    @ExcelProperty(value = "课程类别ID",index = 5)
    private Integer courseType;

    @ColumnWidth(30)
    @ExcelProperty(value = "课程类别名称",index = 6)
    private String courseTypeName;

    @ColumnWidth(30)
    @ExcelProperty(value = "培训形式  1.内部 2.外部",index = 7)
    private Integer trainShape;

    @ColumnWidth(30)
    @ExcelProperty(value = "受训对象",index = 8)
    private String trainSubject;

    @ColumnWidth(30)
    @ExcelProperty(value = "培训完成时间",index = 9)
    private String trainCompleteTime;

    @ColumnWidth(30)
    @ExcelProperty(value = "培训需求依据ID",index = 10)
    private Integer trainRequirements;

    @ColumnWidth(30)
    @ExcelProperty(value = "培训需求依据名称",index = 11)
    private String trainRequirementsName;

    @ColumnWidth(30)
    @ExcelProperty(value = "参训人数",index = 12)
    private Integer participantsNum;

    @ColumnWidth(30)
    @ExcelProperty(value = "培训讲师id",index = 13)
    private Integer teacherId;

    @ExcelProperty(value = "培训讲师姓名",index = 14)
    private String teacherName;

    @ColumnWidth(30)
    @ExcelProperty(value = "考核方法ID",index = 15)
    private Integer assessmentMethod;

    @ColumnWidth(30)
    @ExcelProperty(value = "考核方法名称",index = 16)
    private String assessmentMethodName;

    @ColumnWidth(30)
    @ExcelProperty(value = "经费预算",index = 17)
    private BigDecimal outlay;

    @ColumnWidth(30)
    @ExcelProperty(value = "备注",index = 18)
    private String remark;
}
