package com.dyys.hr.entity.train.excel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.dyys.hr.utils.DateConverter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "培训项目考试明细列表excel")
public class ExamDetailListExcel implements Serializable{
    @ColumnWidth(30)
    @ExcelProperty(value = "课程id",index = 0)
    @ExcelIgnore
    private String courseId;

    @ColumnWidth(30)
    @ExcelProperty(value = "课程名称",index = 0)
    private String courseName;

    @ColumnWidth(30)
    @ExcelProperty(value = "考试标题",index = 1)
    private String title;

    @ColumnWidth(30)
    @ExcelProperty(value = "考试类型",index = 2)
    @ExcelIgnore
    private Integer type;

    @ColumnWidth(30)
    @ExcelProperty(value = "考试类型",index = 2)
    private String typeName;

    @ColumnWidth(30)
    @ExcelProperty(value = "考试模板id",index = 3)
    @ExcelIgnore
    private Long paperId;

    @ColumnWidth(30)
    @ExcelProperty(value = "考试模板",index = 3)
    private String paperName;

    @ColumnWidth(30)
    @ExcelProperty(value = "开始时间",index = 4,converter = DateConverter.class)
    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @ColumnWidth(30)
    @ExcelProperty(value = "结束时间",index = 5,converter = DateConverter.class)
    @ApiModelProperty(value = "结束时间")
    private Date endTime;

    @ColumnWidth(30)
    @ExcelProperty(value = "考试总时长",index = 6)
    private String duration;

    @ColumnWidth(30)
    @ExcelProperty(value = "考试总分",index = 7)
    private String totalScore;

    @ColumnWidth(30)
    @ExcelProperty(value = "考试及格分",index = 8)
    private String passScore;

    @ColumnWidth(30)
    @ExcelProperty(value = "考试次数限制",index = 9)
    private Integer examCount;

    @ColumnWidth(30)
    @ExcelProperty(value = "状态",index = 10)
    @ExcelIgnore
    private Integer status;

    @ColumnWidth(30)
    @ExcelProperty(value = "状态名称",index = 10)
    private String statusName;

    @ColumnWidth(30)
    @ExcelProperty(value = "工号",index = 11)
    private String emplId;

    @ColumnWidth(30)
    @ExcelProperty(value = "姓名",index = 12)
    private String emplName;

    @ColumnWidth(30)
    @ExcelProperty(value = "公司",index = 13)
    private String companyDescr;

    @ColumnWidth(30)
    @ExcelProperty(value = "部门",index = 14)
    private String deptDescr;

    @ColumnWidth(30)
    @ExcelProperty(value = "岗位",index = 15)
    private String jobDescr;

    @ColumnWidth(30)
    @ExcelProperty(value = "考试次数",index = 16)
    private Integer examNum;

    @ColumnWidth(30)
    @ExcelProperty(value = "最高分",index = 17)
    private String highestScore;

    @ColumnWidth(30)
    @ExcelProperty(value = "是否通过",index = 18)
    private String  isPass;

    @ColumnWidth(30)
    @ExcelProperty(value = "最后考试时间",index = 19,converter = DateConverter.class)
    private Date finalTime;


}
