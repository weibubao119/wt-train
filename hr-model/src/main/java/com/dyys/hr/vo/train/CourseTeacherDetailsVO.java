package com.dyys.hr.vo.train;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wsj
 * @projectName hr-recruit
 * @date 2023/2/914:55
 */
@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "讲师课程明细vo")
public class CourseTeacherDetailsVO implements Serializable {
    @ColumnWidth(15)
    @ExcelProperty(value = "序号",index = 0)
    @ApiModelProperty(value = "序号")
    private Long id;

    @ColumnWidth(15)
    @ExcelProperty(value = "单位",index = 1)
    @ApiModelProperty(value = "单位")
    private String companyDescr;

    @ColumnWidth(15)
    @ExcelProperty(value = "部门",index = 2)
    @ApiModelProperty(value = "部门")
    private String deptDescr;

    @ColumnWidth(15)
    @ExcelProperty(value = "讲师工号",index = 3)
    @ApiModelProperty(value = "讲师工号")
    private String number;

    @ColumnWidth(15)
    @ExcelProperty(value = "讲师姓名",index = 4)
    @ApiModelProperty(value = "讲师姓名")
    private String teacherName;

    @ColumnWidth(15)
    @ExcelProperty(value = "讲师类型",index = 5)
    @ApiModelProperty(value = "讲师类型")
    private String typeName;

    @ColumnWidth(15)
    @ExcelProperty(value = "讲师等级",index = 6)
    @ApiModelProperty(value = "讲师等级")
    private String levelName;

    @ColumnWidth(15)
    @ExcelProperty(value = "课程名称",index = 7)
    @ApiModelProperty(value = "课程名称")
    private String courseName;

    @ColumnWidth(15)
    @ExcelProperty(value = "已授课次数",index = 8)
    @ApiModelProperty(value = "已授课次数")
    private Integer haveTaughtNum;

    @ColumnWidth(15)
    @ExcelProperty(value = "已授课课时数",index = 9)
    @ApiModelProperty(value = "已授课课时数")
    private String haveTaughtNumClassHours;

    @ColumnWidth(15)
    @ExcelProperty(value = "讲师评分",index = 10)
    @ApiModelProperty(value = "讲师评分")
    private String teacherScore;
}