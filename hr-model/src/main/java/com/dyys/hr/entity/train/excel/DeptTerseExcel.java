package com.dyys.hr.entity.train.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Desc
 * Create by yangye
 * Date 2023/3/27 11:30
 */
@Data
public class DeptTerseExcel {
    @ColumnWidth(30)
    @ExcelProperty(value = "组织单位编码", index = 0)
    @ApiModelProperty(value = "组织单位编码")
    private String deptId;

    @ColumnWidth(30)
    @ExcelProperty(value = "组织单位名称", index = 1)
    @ApiModelProperty(value = "组织单位名称")
    private String deptName;
}
