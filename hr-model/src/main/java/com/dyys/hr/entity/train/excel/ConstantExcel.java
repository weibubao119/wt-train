package com.dyys.hr.entity.train.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

/**
 * Desc
 * Create by yangye
 * Date 2023/4/3 9:59
 */
@Data
public class ConstantExcel {
    @ColumnWidth(15)
    @ExcelProperty(value = "档案类型",index = 0)
    private String typeName;

    @ColumnWidth(15)
    @ExcelProperty(value = "ID(编码)值",index = 1)
    private String id;

    @ColumnWidth(15)
    @ExcelProperty(value = "名称",index = 2)
    private String name;

    @ColumnWidth(15)
    @ExcelProperty(value = "所属职序",index = 3)
    private String secCodeName;
}
