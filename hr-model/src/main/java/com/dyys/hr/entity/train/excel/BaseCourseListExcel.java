package com.dyys.hr.entity.train.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseCourseListExcel {
    @ColumnWidth(15)
    @ExcelProperty(value = "课程编号", index = 0)
    private String number;

    @ColumnWidth(20)
    @ExcelProperty(value = "课程名称", index = 1)
    private String name;

    @ColumnWidth(20)
    @ExcelProperty(value = "课程类别名称", index = 2)
    private String categoryName;

    @ColumnWidth(10)
    @ExcelProperty(value = "课时数", index = 3)
    private Integer classHours;

    @ColumnWidth(15)
    @ExcelProperty(value = "课程来源", index = 4)
    private String courseSourceName;

}