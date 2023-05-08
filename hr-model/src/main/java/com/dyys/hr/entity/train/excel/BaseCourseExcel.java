package com.dyys.hr.entity.train.excel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseCourseExcel {
    @ColumnWidth(15)
    @ExcelProperty(value = "课程名称\n(必填)",index = 0)
    @ApiModelProperty(value = "课程名称")
    private String name;

    @ColumnWidth(15)
    @ExcelProperty(value = "课程类别\n(必填)",index = 1)
    @ApiModelProperty(value = "课程类别")
    private String cateName;

    @ColumnWidth(25)
    @ExcelProperty(value = "课程来源(必填)\n1-外部，2-自有",index = 2)
    @ApiModelProperty(value = "课程来源")
    private Integer courseSource;

    @ColumnWidth(10)
    @ExcelProperty(value = "课时数\n(必填)",index = 3)
    @ApiModelProperty(value = "课时数")
    private BigDecimal classHours;

    @ColumnWidth(15)
    @ExcelProperty(value = "课程学分\n(必填)",index = 4)
    @ApiModelProperty(value = "课程学分")
    private BigDecimal credit;

    @ColumnWidth(25)
    @ExcelProperty(value = "课程简介",index = 5)
    @ApiModelProperty(value = "课程简介")
    private String instructions;

    @ColumnWidth(20)
    @ExcelProperty(value = "可授课讲师\n工号/编号",index = 6)
    @ApiModelProperty(value = "可授课讲师工号/编号")
    private String teacherNumber;

    @ColumnWidth(20)
    @ExcelProperty(value = "可授课讲师\n姓名",index = 7)
    @ApiModelProperty(value = "可授课讲师姓名")
    private String teacherName;

    @ExcelIgnore
    @ApiModelProperty(value = "提示语")
    private String errMsg;
}
