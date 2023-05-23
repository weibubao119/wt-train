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
public class TrainExamResultImportExcel {
    @ColumnWidth(15)
    @ExcelProperty(value = "参考人工号\n(必填)",index = 0)
    @ApiModelProperty(value = "参考人工号")
    @IsNeeded
    private String userId;

    @ColumnWidth(15)
    @ExcelProperty(value = "参考人姓名\n(必填)",index = 1)
    @ApiModelProperty(value = "参考人姓名")
    @IsNeeded
    private String userName;

    @ColumnWidth(15)
    @ExcelProperty(value = "考试分数\n(必填)",index = 2)
    @ApiModelProperty(value = "考试分数")
    @IsNeeded
    private String score;

    @ColumnWidth(30)
    @ExcelProperty(value = "考试时间(必填)\n格式例如：2023-05-23 08:00:00",index = 3)
    @ApiModelProperty(value = "考试时间")
    private String examTime;

    @ColumnWidth(30)
    @ExcelProperty(value = "考试耗时(单位：分 必填)\n格式例如：60",index = 4)
    @ApiModelProperty(value = "考试耗时")
    private String useTime;

    @ExcelIgnore
    @ApiModelProperty(value = "提示语")
    private String errMsg;
}
