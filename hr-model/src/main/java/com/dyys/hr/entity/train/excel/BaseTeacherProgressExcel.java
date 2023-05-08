package com.dyys.hr.entity.train.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * Desc
 * Create by yangye
 * Date 2022/12/15 15:49
 */
@Data
public class BaseTeacherProgressExcel {
    @ColumnWidth(25)
    @ExcelProperty(value = "讲师工号/编号", index = 0)
    private String teacherNumber;

    @ColumnWidth(15)
    @ExcelProperty(value = "讲师姓名", index = 1)
    private String teacherName;

    @ColumnWidth(15)
    @ExcelProperty(value = "讲师等级", index = 2)
    private String levelName;

    @ColumnWidth(15)
    @ExcelProperty(value = "开始时间 ", index = 3)
    private String startTime;

    @ColumnWidth(15)
    @ExcelProperty(value = "结束时间", index = 4)
    private String endTime;

    @ColumnWidth(15)
    @ExcelProperty(value = "课酬标准\n(元/学时)", index = 5)
    private Integer fee;

    @ColumnWidth(30)
    @ExcelProperty(value = "说明", index = 6)
    private String instructions;
}
