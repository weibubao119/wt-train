package com.dyys.hr.entity.train.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * Desc
 * Create by yangye
 * Date 2022/12/15 16:12
 */
@Data
public class TaughtCourseExcel {
    @ColumnWidth(25)
    @ExcelProperty(value = "讲师工号/编号", index = 0)
    private String teacherNumber;

    @ColumnWidth(15)
    @ExcelProperty(value = "讲师姓名", index = 1)
    private String teacherName;

    @ColumnWidth(20)
    @ExcelProperty(value = "培训项目", index = 2)
    private String trainName;

    @ColumnWidth(15)
    @ExcelProperty(value = "课程编号", index = 3)
    private String courseNumber;

    @ColumnWidth(15)
    @ExcelProperty(value = "课程名称", index = 4)
    private String courseName;

    @ColumnWidth(15)
    @ExcelProperty(value = "课程类别", index = 5)
    private String courseCateName;

    @ColumnWidth(15)
    @ExcelProperty(value = "培训开始时间", index = 6)
    @JsonFormat(pattern = "yyyy-MM-dd HH:ii")
    private String startTime;

    @ColumnWidth(15)
    @ExcelProperty(value = "培训结束时间", index = 7)
    @JsonFormat(pattern = "yyyy-MM-dd HH:ii")
    private String endTime;

    @ColumnWidth(15)
    @ExcelProperty(value = "课时数", index = 8)
    private String classHour;

    @ColumnWidth(15)
    @ExcelProperty(value = "讲师评分", index = 9)
    private String teacherScore;
}
