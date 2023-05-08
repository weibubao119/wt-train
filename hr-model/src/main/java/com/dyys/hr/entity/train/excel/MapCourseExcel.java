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
 * Date 2022/9/13 16:15
 */
@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
public class MapCourseExcel {
    @ColumnWidth(20)
    @ExcelProperty(value = "地图职级\n(必选)",index = 0)
    @ApiModelProperty(value = "地图职级")
    private String gradeCodeName;

    @ColumnWidth(20)
    @ExcelProperty(value = "学习方向\n(必选)",index = 1)
    @ApiModelProperty(value = "学习方向")
    private String sblIdName;

    @ColumnWidth(25)
    @ExcelProperty(value = "课程模块名称\n(必填)",index = 2)
    @ApiModelProperty(value = "课程模块名称")
    private String moduleName;

    @ColumnWidth(15)
    @ExcelProperty(value = "课程编号\n(必填)",index = 3)
    @ApiModelProperty(value = "课程编号")
    private String courseNumber;

    @ColumnWidth(15)
    @ExcelProperty(value = "课程名称\n(必填)",index = 4)
    @ApiModelProperty(value = "课程名称")
    private String courseName;

    @ColumnWidth(25)
    @ExcelProperty(value = "必修标识(必选)\n(1是，0否)",index = 5)
    @ApiModelProperty(value = "必修标识")
    private Integer requiredFlag;

    @ColumnWidth(20)
    @ExcelProperty(value = "同等课程编号",index = 6)
    @ApiModelProperty(value = "同等课程编号")
    private String tantaCourseNumber;

    @ColumnWidth(20)
    @ExcelProperty(value = "同等课程名称",index = 7)
    @ApiModelProperty(value = "同等课程名称")
    private String tantaCourseName;

    @ExcelIgnore
    @ApiModelProperty(value = "提示语")
    private String errMsg;
}
