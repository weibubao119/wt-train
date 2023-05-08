package com.dyys.hr.entity.train.excel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
public class TrainHistoryDataExcel {
    //培训项目表
    @ColumnWidth(15)
    @ExcelProperty(value = "学员工号\n(必填)",index = 0)
    @ApiModelProperty(value = "学员工号")
    private String emplId;

    @ColumnWidth(15)
    @ExcelProperty(value = "学员姓名",index = 1)
    @ApiModelProperty(value = "学员姓名")
    private String emplName;

    @ColumnWidth(15)
    @ExcelProperty(value = "项目名称\n(必填)",index = 2)
    @ApiModelProperty(value = "项目名称")
    private String trainName;

    @ColumnWidth(20)
    @ExcelProperty(value = "组织单位编号\n(必填)",index = 3)
    @ApiModelProperty(value = "组织单位编号")
    private String deptId;

    @ColumnWidth(20)
    @ExcelProperty(value = "组织单位名称\n(必填)",index = 4)
    @ApiModelProperty(value = "组织单位名称")
    private String deptName;

    @ColumnWidth(25)
    @ExcelProperty(value = "培训形式(必填)\n 1-内部/2-外部",index = 5)
    @ApiModelProperty(value = "培训形式")
    private Integer trainShape;

    @ColumnWidth(30)
    @ExcelProperty(value = "项目开始日期(必填)\n格式例如：2023/1/1",index = 6)
    @ApiModelProperty(value = "项目开始日期")
    private String startTime;

    @ColumnWidth(20)
    @ExcelProperty(value = "项目结束日期(必填)\n格式例如：2023/1/1",index = 7)
    @ApiModelProperty(value = "项目结束日期")
    private String endTime;

    @ColumnWidth(20)
    @ExcelProperty(value = "项目实际参训人数",index = 8)
    @ApiModelProperty(value = "项目实际参训人数")
    private Integer participantsNum;

    @ColumnWidth(20)
    @ExcelProperty(value = "项目使用经费",index = 9)
    @ApiModelProperty(value = "项目使用经费")
    private String outlay;

    @ColumnWidth(20)
    @ExcelProperty(value = "项目负责人工号",index = 10)
    @ApiModelProperty(value = "项目负责人工号")
    private String principalId;

    @ColumnWidth(20)
    @ExcelProperty(value = "项目负责人姓名",index = 11)
    @ApiModelProperty(value = "项目负责人姓名")
    private String principalName;

    //培训项目总结表
    @ColumnWidth(20)
    @ExcelProperty(value = "项目总成绩",index = 12)
    @ApiModelProperty(value = "项目总成绩")
    private String totalScore;

    @ColumnWidth(25)
    @ExcelProperty(value = "是否取证(必填)\n1-是/0-否",index = 13)
    @ApiModelProperty(value = "是否取证")
    private Integer isObtain;

    //课程表
    @ColumnWidth(15)
    @ExcelProperty(value = "课程名称\n(必填)",index = 14)
    @ApiModelProperty(value = "课程名称")
    private String courseName;

    @ColumnWidth(15)
    @ExcelProperty(value = "课程类别\n(必填)",index = 15)
    @ApiModelProperty(value = "课程类别")
    private String courseCategoryName;

    @ColumnWidth(20)
    @ExcelProperty(value = "课程开始日期(必填)\n格式例如：2023/1/1",index = 16)
    @ApiModelProperty(value = "课程开始日期")
    private String courseStartTime;

    @ColumnWidth(20)
    @ExcelProperty(value = "课程结束日期(必填)\n格式例如：2023/1/1",index = 17)
    @ApiModelProperty(value = "课程结束日期")
    private String courseEndTime;

    @ColumnWidth(10)
    @ExcelProperty(value = "课时数",index = 18)
    @ApiModelProperty(value = "课时数")
    private BigDecimal courseClassHours;

    @ColumnWidth(15)
    @ExcelProperty(value = "课时学分",index = 19)
    @ApiModelProperty(value = "课时学分")
    private BigDecimal courseCredit;

    @ColumnWidth(15)
    @ExcelProperty(value = "课程简介",index = 20)
    @ApiModelProperty(value = "课程简介")
    private String courseInstructions;

    @ColumnWidth(15)
    @ExcelProperty(value = "学习方式\n(必填)",index = 21)
    @ApiModelProperty(value = "学习方式")
    private String learningFormName;

    @ColumnWidth(20)
    @ExcelProperty(value = "考核方式(必填)\n1-考试/2-考察",index = 22)
    @ApiModelProperty(value = "考核方式")
    private Integer examineForm;

    @ColumnWidth(35)
    @ExcelProperty(value = "课程成绩\n考核方式为1则必填\n考核方式为2则非必填",index = 23)
    @ApiModelProperty(value = "课程成绩")
    private String courseScore;

    //讲师表
    @ColumnWidth(25)
    @ExcelProperty(value = "讲师类型\n1-内部/2-外部\n有讲师信息必填",index = 24)
    @ApiModelProperty(value = "讲师类型")
    private Integer teacherType;

    @ColumnWidth(35)
    @ExcelProperty(value = "讲师工号\n有讲师信息且内部讲师必填",index = 25)
    @ApiModelProperty(value = "讲师工号")
    private String teacherNumber;

    @ColumnWidth(25)
    @ExcelProperty(value = "讲师姓名\n有讲师信息必填",index = 26)
    @ApiModelProperty(value = "讲师姓名")
    private String teacherName;

    @ColumnWidth(20)
    @ExcelProperty(value = "讲师性别\nM-男/F-女",index = 27)
    @ApiModelProperty(value = "讲师性别")
    private String teacherSex;

    @ColumnWidth(15)
    @ExcelProperty(value = "讲师电话",index = 28)
    @ApiModelProperty(value = "讲师电话")
    private String teacherContactPhone;

    @ColumnWidth(15)
    @ExcelProperty(value = "讲师邮箱",index = 29)
    @ApiModelProperty(value = "讲师邮箱")
    private String teacherEmail;

    @ColumnWidth(25)
    @ExcelProperty(value = "讲师所属机构名称",index = 30)
    @ApiModelProperty(value = "讲师所属机构名称")
    private String teacherOrganizationName;

    @ExcelIgnore
    @ApiModelProperty(value = "提示语")
    private String errMsg;
}
