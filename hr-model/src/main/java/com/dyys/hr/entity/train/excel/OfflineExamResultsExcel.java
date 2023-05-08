package com.dyys.hr.entity.train.excel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OfflineExamResultsExcel {
    @ColumnWidth(15)
    @ExcelProperty(value = "学员工号\n(必填)",index = 0)
    @ApiModelProperty(value = "学员工号")
    private String emplId;

    @ColumnWidth(15)
    @ExcelProperty(value = "学员姓名",index = 1)
    @ApiModelProperty(value = "学员姓名")
    private String emplName;

    @ColumnWidth(15)
    @ExcelProperty(value = "最高分\n(必填)",index = 2)
    @ApiModelProperty(value = "最高分")
    private String highestScore;

    @ColumnWidth(25)
    @ExcelProperty(value = "是否通过(必填)\n 1是；0否",index = 3)
    @ApiModelProperty(value = "是否通过(1是；0否)")
    private Integer status;

    @ExcelIgnore
    @ApiModelProperty(value = "提示语")
    private String errMsg;
}
