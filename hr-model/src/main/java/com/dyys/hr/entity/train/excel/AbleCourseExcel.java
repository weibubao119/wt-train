package com.dyys.hr.entity.train.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

/**
 * Desc
 * Create by yangye
 * Date 2022/12/15 16:01
 */
@Data
public class AbleCourseExcel {
    @ColumnWidth(25)
    @ExcelProperty(value = "讲师工号/编号", index = 0)
    private String teacherNumber;

    @ColumnWidth(15)
    @ExcelProperty(value = "讲师姓名", index = 1)
    private String teacherName;

    @ColumnWidth(15)
    @ExcelProperty(value = "课程编号", index = 2)
    private String courseNumber;

    @ColumnWidth(15)
    @ExcelProperty(value = "课程名称", index = 3)
    private String courseName;

    @ColumnWidth(15)
    @ExcelProperty(value = "课程类别", index = 4)
    private String courseCateName;

    @ColumnWidth(15)
    @ExcelProperty(value = "课时数", index = 5)
    private Integer classHours;

    @ColumnWidth(15)
    @ExcelProperty(value = "课程介绍", index = 6)
    private String instructions;
}
