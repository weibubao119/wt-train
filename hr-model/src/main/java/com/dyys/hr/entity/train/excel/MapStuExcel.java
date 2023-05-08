package com.dyys.hr.entity.train.excel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Desc
 * Create by yangye
 * Date 2022/9/20 14:12
 */
@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
public class MapStuExcel {
    @ColumnWidth(15)
    @ExcelProperty(value = "学员工号\n(必填)",index = 0)
    @ApiModelProperty(value = "学员工号")
    private String emplId;

    @ColumnWidth(15)
    @ExcelProperty(value = "学员姓名\n(必填)",index = 1)
    @ApiModelProperty(value = "学员工号")
    private String emplName;

    @ColumnWidth(20)
    @ExcelProperty(value = "学习地图编码\n(必填)",index = 2)
    @ApiModelProperty(value = "学习地图编码")
    private String mapCode;

    @ColumnWidth(20)
    @ExcelProperty(value = "学习地图名称\n(必填)",index = 3)
    @ApiModelProperty(value = "学习地图名称")
    private String mapName;

    @ColumnWidth(20)
    @ExcelProperty(value = "学习职级编码\n(必填)",index = 4)
    @ApiModelProperty(value = "学习职级编码")
    private String posnGradeCode;

    @ColumnWidth(20)
    @ExcelProperty(value = "学习职级名称\n(必填)",index = 5)
    @ApiModelProperty(value = "学习职级名称")
    private String posnGradeName;

    @ColumnWidth(20)
    @ExcelProperty(value = "学习方向ID\n(必填)",index = 6)
    @ApiModelProperty(value = "学习方向ID")
    private Integer sblId;

    @ColumnWidth(20)
    @ExcelProperty(value = "学习方向名称\n(必填)",index = 7)
    @ApiModelProperty(value = "学习方向名称")
    private String sblName;

    @ExcelIgnore
    @ApiModelProperty(value = "提示语")
    private String errMsg;
}
