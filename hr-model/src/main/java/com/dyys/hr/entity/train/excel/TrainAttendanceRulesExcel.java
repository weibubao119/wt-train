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
public class TrainAttendanceRulesExcel {
    @ColumnWidth(30)
    @ExcelProperty(value = "课程名称",index = 0)
    private String courseName;

    @ColumnWidth(30)
    @ExcelProperty(value = "上课时间",index = 1)
    private String courseTime;

    @ColumnWidth(30)
    @ExcelProperty(value = "考勤日期",index = 2)
    private String date;

    @ColumnWidth(30)
    @ExcelProperty(value = "签到开始时间",index = 3)
    private String startTime;

    @ColumnWidth(30)
    @ExcelProperty(value = "签到结束时间",index = 4)
    private String endTime;

    @ColumnWidth(30)
    @ExcelProperty(value = "考勤人数",index = 5)
    private Integer totalNum;

    @ColumnWidth(30)
    @ExcelProperty(value = "正常出勤人数",index = 6)
    private Integer attendanceNum;

    @ColumnWidth(30)
    @ExcelProperty(value = "缺勤人数",index = 7)
    private Integer absentNum;

    @ColumnWidth(30)
    @ExcelProperty(value = "迟到人数",index = 8)
    private Integer lateNum;
}
