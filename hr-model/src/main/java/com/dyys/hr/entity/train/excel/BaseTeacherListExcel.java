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
public class BaseTeacherListExcel {
    @ColumnWidth(25)
    @ExcelProperty(value = "讲师工号/编号", index = 0)
    private String number;

    @ColumnWidth(15)
    @ExcelProperty(value = "讲师姓名", index = 1)
    private String name;

    @ColumnWidth(25)
    @ExcelProperty(value = "所属公司/机构", index = 2)
    private String organizationName;

    @ColumnWidth(15)
    @ExcelProperty(value = "讲师等级 ", index = 3)
    private String levelName;

    @ColumnWidth(15)
    @ExcelProperty(value = "讲师类别", index = 4)
    private String typeName;

    @ColumnWidth(15)
    @ExcelProperty(value = "讲师状态", index = 5)
    private String statusName;
}