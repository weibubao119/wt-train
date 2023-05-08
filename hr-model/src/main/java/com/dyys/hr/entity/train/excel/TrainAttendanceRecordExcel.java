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
public class TrainAttendanceRecordExcel {
    @ColumnWidth(30)
    @ExcelProperty(value = "培训项目名称",index = 0)
    private String trainName;

    @ColumnWidth(30)
    @ExcelProperty(value = "课程名称",index = 1)
    private String courseName;

    @ColumnWidth(30)
    @ExcelProperty(value = "工号",index = 2)
    private String emplId;

    @ColumnWidth(30)
    @ExcelProperty(value = "学员姓名",index = 3)
    private String emplName;

    @ColumnWidth(30)
    @ExcelProperty(value = "公司名称",index = 4)
    private String companyDescr;

    @ColumnWidth(30)
    @ExcelProperty(value = "部门名称",index = 5)
    private String deptDescr;

    @ColumnWidth(30)
    @ExcelProperty(value = "考勤日期",index = 6)
    private String date;

    @ColumnWidth(30)
    @ExcelProperty(value = "开始时间",index = 7)
    private String startTime;

    @ColumnWidth(30)
    @ExcelProperty(value = "结束时间",index = 8)
    private String endTime;

    @ColumnWidth(30)
    @ExcelProperty(value = "签到时间",index = 9)
    private String signedInTime;

    @ColumnWidth(30)
    @ExcelProperty(value = "签到状态名称",index = 10)
    private String statusName;
}
