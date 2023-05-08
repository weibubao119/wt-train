package com.dyys.hr.entity.train.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

/**
 * Desc
 * Create by yangye
 * Date 2022/12/19 9:28
 */
@Data
public class AbleTeacherExcel {
    @ColumnWidth(15)
    @ExcelProperty(value = "课程编号", index = 0)
    private String courseNumber;

    @ColumnWidth(20)
    @ExcelProperty(value = "课程名称", index = 1)
    private String courseName;

    @ColumnWidth(25)
    @ExcelProperty(value = "讲师工号/编号", index = 2)
    private String teacherNumber;

    @ColumnWidth(15)
    @ExcelProperty(value = "讲师姓名", index = 3)
    private String teacherName;

    @ColumnWidth(15)
    @ExcelProperty(value = "讲师类型", index = 4)
    private String teacherTypeName;

    @ColumnWidth(15)
    @ExcelProperty(value = "所属公司/机构", index = 5)
    private String organizationName;
}
