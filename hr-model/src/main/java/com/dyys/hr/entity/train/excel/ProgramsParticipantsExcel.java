package com.dyys.hr.entity.train.excel;

import com.alibaba.excel.annotation.ExcelIgnore;
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
public class ProgramsParticipantsExcel {
    @ColumnWidth(30)
    @ExcelIgnore
    @ExcelProperty(value = "记录id", index = 0)
    private String id;

    @ColumnWidth(30)
    @ExcelProperty(value = "工号", index = 0)
    private String emplId;

    @ColumnWidth(30)
    @ExcelProperty(value = "姓名", index = 1)
    private String emplName;

    @ColumnWidth(30)
    @ExcelProperty(value = "单位编码", index = 2)
    @ExcelIgnore
    private String company;

    @ColumnWidth(30)
    @ExcelProperty(value = "单位名称", index = 2)
    private String companyDescr;

    @ColumnWidth(30)
    @ExcelProperty(value = "部门编码", index = 3)
    @ExcelIgnore
    private String deptId;

    @ColumnWidth(30)
    @ExcelProperty(value = "部门名称", index = 3)
    private String deptDescr;

    @ColumnWidth(30)
    @ExcelProperty(value = "岗位编码", index = 4)
    @ExcelIgnore
    private String postCode;

    @ColumnWidth(30)
    @ExcelProperty(value = "岗位名称", index = 4)
    private String postDescr;

    @ColumnWidth(30)
    @ExcelProperty(value = "职序编码", index = 5)
    @ExcelIgnore
    private String secCode;

    @ColumnWidth(30)
    @ExcelProperty(value = "职序名称", index = 5)
    private String secDescr;

    @ColumnWidth(30)
    @ExcelProperty(value = "职级编码", index = 6)
    @ExcelIgnore
    private String gradeCode;

    @ColumnWidth(30)
    @ExcelProperty(value = "职级名称", index = 6)
    private String gradeDescr;

    @ColumnWidth(30)
    @ExcelProperty(value = "状态", index = 7)
    @ExcelIgnore
    private Integer status;

    @ColumnWidth(30)
    @ExcelProperty(value = "状态名称", index = 7)
    private String statusName;

}
