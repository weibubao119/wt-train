package com.dyys.hr.entity.train.excel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseTeacherExcel {
    @ColumnWidth(25)
    @ExcelProperty(value = "讲师类别(必填)\n1内部，2外部", index = 0)
    @ApiModelProperty(value = "讲师类别")
    private Integer type;

    @ColumnWidth(20)
    @ExcelProperty(value = "讲师工号\n(内部讲师必填)", index = 1)
    @ApiModelProperty(value = "讲师工号")
    private String number;

    @ColumnWidth(15)
    @ExcelProperty(value = "讲师姓名\n(必填)", index = 2)
    @ApiModelProperty(value = "讲师姓名")
    private String name;

    @ColumnWidth(30)
    @ExcelProperty(value = "讲师状态(必填)\n1有效，0无效", index = 3)
    @ApiModelProperty(value = "讲师状态")
    private Integer status;

    @ColumnWidth(15)
    @ExcelProperty(value = "讲师性别\nM男，F女", index = 4)
    @ApiModelProperty(value = "讲师性别")
    private String sex;

    @ColumnWidth(15)
    @ExcelProperty(value = "讲师年龄", index = 5)
    @ApiModelProperty(value = "讲师年龄")
    private Integer age;

    @ColumnWidth(20)
    @ExcelProperty(value = "公司/机构名称", index = 6)
    @ApiModelProperty(value = "公司/机构名称")
    private String organizationName;

    @ColumnWidth(15)
    @ExcelProperty(value = "联系电话", index = 7)
    @ApiModelProperty(value = "联系电话")
    private String contactPhone;

    @ColumnWidth(15)
    @ExcelProperty(value = "邮箱地址", index = 8)
    @ApiModelProperty(value = "邮箱地址")
    private String email;

    @ColumnWidth(15)
    @ExcelProperty(value = "文化程度/\n背景信息", index = 9)
    @ApiModelProperty(value = "文化程度/背景信息")
    private String educationLevelName;

    @ColumnWidth(30)
    @ExcelProperty(value = "成长历程-讲师等级\n有成长历程必填", index = 10)
    @ApiModelProperty(value = "成长历程-讲师等级")
    private String levelName;

    @ColumnWidth(30)
    @ExcelProperty(value = "成长历程-开始日期\n格式例如：2023/1/1\n有成长历程必填", index = 11)
    @ApiModelProperty(value = "成长历程-开始日期")
    private String startTime;

    @ColumnWidth(30)
    @ExcelProperty(value = "成长历程-结束日期\n格式例如：2023/1/1\n有成长历程必填", index = 12)
    @ApiModelProperty(value = "成长历程-结束日期")
    private String endTime;

    @ColumnWidth(25)
    @ExcelProperty(value = "成长历程\n课酬标准(元/学时)", index = 13)
    @ApiModelProperty(value = "成长历程-课酬标准")
    private Integer fee;

    @ColumnWidth(15)
    @ExcelProperty(value = "成长历程\n说明", index = 14)
    @ApiModelProperty(value = "成长历程-说明")
    private String instructions;

    @ExcelIgnore
    @ApiModelProperty(value = "提示语")
    private String errMsg;
}
