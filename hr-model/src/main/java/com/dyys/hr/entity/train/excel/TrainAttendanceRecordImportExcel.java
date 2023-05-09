package com.dyys.hr.entity.train.excel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.dyys.hr.annotation.IsNeeded;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Desc
 * Create by yangye
 * Date 2022/9/16 11:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrainAttendanceRecordImportExcel {
    @ColumnWidth(15)
    @ExcelProperty(value = "学员工号\n(必填)",index = 0)
    @ApiModelProperty(value = "学员工号")
    @IsNeeded
    private String emplId;

    @ColumnWidth(15)
    @ExcelProperty(value = "学员姓名\n(必填)",index = 1)
    @ApiModelProperty(value = "学员姓名")
    @IsNeeded
    private String emplName;

    @ColumnWidth(15)
    @ExcelProperty(value = "考勤状态\n(必填)",index = 2)
    @ApiModelProperty(value = "考勤状态")
    @IsNeeded
    private String status;

    @ColumnWidth(30)
    @ExcelProperty(value = "签到时间(不必填)\n格式例如：12:00:00",index = 3)
    @ApiModelProperty(value = "签到时间")
    private String signedInTime;

    @ExcelIgnore
    @ApiModelProperty(value = "提示语")
    private String errMsg;
}
