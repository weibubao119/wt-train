package com.dyys.hr.vo.train;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author wsj
 * @projectName hr-recruit
 * @date 2023/2/914:55
 */
@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "讲师课程明细vo")
public class TaughtCourseTeacherDetailsVO implements Serializable {
    @ColumnWidth(15)
    @ExcelProperty(value = "序号",index = 0)
    @ApiModelProperty(value = "序号")
    private Long id;

    @ColumnWidth(15)
    @ExcelProperty(value = "组织单位",index = 1)
    @ApiModelProperty(value = "组织单位")
    private String companyDescr;

    @ColumnWidth(15)
    @ExcelProperty(value = "需求部门",index = 2)
    @ApiModelProperty(value = "需求部门")
    private String deptDescr;

    @ColumnWidth(15)
    @ExcelProperty(value = "课程编号",index = 3)
    @ApiModelProperty(value = "课程编号")
    private String courseNumber;

    @ColumnWidth(15)
    @ExcelProperty(value = "课程名称",index = 4)
    @ApiModelProperty(value = "课程名称")
    private String courseName;

    @ColumnWidth(15)
    @ExcelProperty(value = "课程课时",index = 5)
    @ApiModelProperty(value = "课程课时")
    private BigDecimal classHours;

    @ColumnWidth(15)
    @ExcelProperty(value = "课程学分",index = 6)
    @ApiModelProperty(value = "课程学分")
    private BigDecimal credit;

    @ColumnWidth(15)
    @ExcelProperty(value = "培训项目名称",index = 7)
    @ApiModelProperty(value = "培训项目名称")
    private String trainName;

    @ColumnWidth(15)
    @ExcelProperty(value = "课程开始日期",index = 8)
    @ApiModelProperty(value = "课程开始日期")
    private String courseStartTime;

    @ColumnWidth(15)
    @ExcelProperty(value = "课程结束日期",index = 9)
    @ApiModelProperty(value = "课程结束日期")
    private String courseEndTime;

    @ColumnWidth(15)
    @ExcelProperty(value = "讲师工号",index = 10)
    @ApiModelProperty(value = "讲师工号")
    private String teacherNumber;

    @ColumnWidth(15)
    @ExcelProperty(value = "讲师姓名",index = 11)
    @ApiModelProperty(value = "讲师姓名")
    private String teacherName;

    @ColumnWidth(15)
    @ExcelProperty(value = "课程类别",index = 12)
    @ApiModelProperty(value = "课程类别")
    private String categoryName;

    @ColumnWidth(15)
    @ExcelProperty(value = "培训形式",index = 13)
    @ApiModelProperty(value = "培训形式")
    private String trainShape;

    @ColumnWidth(15)
    @ExcelProperty(value = "学习形式",index = 14)
    @ApiModelProperty(value = "学习形式")
    private String learningForm;

    @ColumnWidth(15)
    @ExcelProperty(value = "学员人数",index = 15)
    @ApiModelProperty(value = "学员人数")
    private Integer participantsTotal;

    @ColumnWidth(15)
    @ExcelProperty(value = "课程评分",index = 16)
    @ApiModelProperty(value = "课程评分")
    private String courseScore;

    @ColumnWidth(15)
    @ExcelProperty(value = "考核方法",index = 17)
    @ApiModelProperty(value = "考核方法")
    private String examineForm;

    @ColumnWidth(15)
    @ExcelProperty(value = "考核成绩",index = 18)
    @ApiModelProperty(value = "考核成绩")
    private String actualResults;

    @ColumnWidth(15)
    @ExcelProperty(value = "培训机构",index = 19)
    @ApiModelProperty(value = "培训机构")
    private String institutionName;

    @ColumnWidth(15)
    @ExcelProperty(value = "受训对象",index = 20)
    @ApiModelProperty(value = "受训对象")
    private String trainSubjects;

    @ColumnWidth(15)
    @ExcelProperty(value = "培训费用",index = 21)
    @ApiModelProperty(value = "培训费用")
    private String trainFee;
}