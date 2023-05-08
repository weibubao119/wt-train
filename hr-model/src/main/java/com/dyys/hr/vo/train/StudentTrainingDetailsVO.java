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
@ApiModel(value = "学员培训明细vo")
public class StudentTrainingDetailsVO implements Serializable {
    @ColumnWidth(15)
    @ExcelProperty(value = "序号",index = 0)
    @ApiModelProperty(value = "序号")
    private Long id;

    @ColumnWidth(15)
    @ExcelProperty(value = "受训人工号",index = 1)
    @ApiModelProperty(value = "受训人工号")
    private String emplId;

    @ColumnWidth(15)
    @ExcelProperty(value = "受训人姓名",index = 2)
    @ApiModelProperty(value = "受训人姓名")
    private String emplName;

    @ColumnWidth(15)
    @ExcelProperty(value = "公司代码",index = 3)
    @ApiModelProperty(value = "公司代码")
    private String companyCode;

    @ColumnWidth(15)
    @ExcelProperty(value = "公司",index = 4)
    @ApiModelProperty(value = "公司")
    private String companyDescr;

    @ColumnWidth(15)
    @ExcelProperty(value = "所在部门",index = 5)
    @ApiModelProperty(value = "所在部门")
    private String deptDescr;

    @ColumnWidth(15)
    @ExcelProperty(value = "岗位",index = 6)
    @ApiModelProperty(value = "岗位")
    private String postDescr;

    @ColumnWidth(15)
    @ExcelProperty(value = "职族",index = 7)
    @ApiModelProperty(value = "职族")
    private String professionDescr;

    @ColumnWidth(15)
    @ExcelProperty(value = "职序",index = 8)
    @ApiModelProperty(value = "职序")
    private String secDescr;

    @ColumnWidth(15)
    @ExcelProperty(value = "职级",index = 9)
    @ApiModelProperty(value = "职级")
    private String gradeDescr;

    @ColumnWidth(15)
    @ExcelProperty(value = "培训项目编号",index = 10)
    @ApiModelProperty(value = "培训项目编号")
    private String trainNumber;

    @ColumnWidth(15)
    @ExcelProperty(value = "培训名称",index = 11)
    @ApiModelProperty(value = "培训名称")
    private String trainName;

    @ColumnWidth(15)
    @ExcelProperty(value = "项目开始时间",index = 12)
    @ApiModelProperty(value = "项目开始时间")
    private String startTime;

    @ColumnWidth(15)
    @ExcelProperty(value = "项目结束时间",index = 13)
    @ApiModelProperty(value = "项目结束时间")
    private String endTime;

    @ColumnWidth(15)
    @ExcelProperty(value = "培训形式",index = 14)
    @ApiModelProperty(value = "培训形式")
    private String trainShapeName;

    @ColumnWidth(15)
    @ExcelProperty(value = "项目成绩",index = 15)
    @ApiModelProperty(value = "项目成绩")
    private String totalScore;

    @ColumnWidth(15)
    @ExcelProperty(value = "是否获证",index = 16)
    @ApiModelProperty(value = "是否获证")
    private String isObtainName;

    @ColumnWidth(15)
    @ExcelProperty(value = "课程编号",index = 17)
    @ApiModelProperty(value = "课程编号")
    private String courseNumber;

    @ColumnWidth(15)
    @ExcelProperty(value = "课程名称",index = 18)
    @ApiModelProperty(value = "课程名称")
    private String courseName;

    @ColumnWidth(15)
    @ExcelProperty(value = "课程来源",index = 19)
    @ApiModelProperty(value = "课程来源")
    private String courseSourceName;

    @ColumnWidth(15)
    @ExcelProperty(value = "课程类别",index = 20)
    @ApiModelProperty(value = "课程类别")
    private String courseCategoryName;

    @ColumnWidth(15)
    @ExcelProperty(value = "学习形式",index = 21)
    @ApiModelProperty(value = "学习形式")
    private String learningFormName;

    @ColumnWidth(15)
    @ExcelProperty(value = "课程结束时间",index = 22)
    @ApiModelProperty(value = "课程结束时间")
    private String courseStartTime;

    @ColumnWidth(15)
    @ExcelProperty(value = "课程结束时间",index = 23)
    @ApiModelProperty(value = "课程结束时间")
    private String courseEndTime;

    @ColumnWidth(15)
    @ExcelProperty(value = "课时数",index = 24)
    @ApiModelProperty(value = "课时数")
    private BigDecimal courseClassHours;

    @ColumnWidth(15)
    @ExcelProperty(value = "获得学分",index = 25)
    @ApiModelProperty(value = "获得学分")
    private BigDecimal courseCredit;

    @ColumnWidth(15)
    @ExcelProperty(value = "授课讲师",index = 26)
    @ApiModelProperty(value = "授课讲师")
    private String teacherName;

    @ColumnWidth(15)
    @ExcelProperty(value = "考核形式",index = 27)
    @ApiModelProperty(value = "考核形式")
    private String examineFormName;

    @ColumnWidth(15)
    @ExcelProperty(value = "课程成绩",index = 28)
    @ApiModelProperty(value = "课程成绩")
    private String courseScore;

    @ColumnWidth(15)
    @ExcelProperty(value = "参训人数",index = 29)
    @ApiModelProperty(value = "参训人数")
    private String participantsNum;

    @ColumnWidth(15)
    @ExcelProperty(value = "课程总培训费",index = 30)
    @ApiModelProperty(value = "课程总培训费")
    private String courseOutlay;

    @ColumnWidth(15)
    @ExcelProperty(value = "个人培训费",index = 31)
    @ApiModelProperty(value = "个人培训费")
    private String avgFee;
}