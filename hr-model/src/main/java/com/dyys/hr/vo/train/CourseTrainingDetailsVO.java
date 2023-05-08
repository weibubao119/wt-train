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
@ApiModel(value = "课程培训明细vo")
public class CourseTrainingDetailsVO implements Serializable {
    @ColumnWidth(15)
    @ExcelProperty(value = "序号",index = 0)
    @ApiModelProperty(value = "序号")
    private Long id;

    @ColumnWidth(15)
    @ExcelProperty(value = "培训课程",index = 1)
    @ApiModelProperty(value = "培训课程")
    private String courseName;

    @ColumnWidth(15)
    @ExcelProperty(value = "组织单位",index = 2)
    @ApiModelProperty(value = "组织单位")
    private String companyDescr;

    @ColumnWidth(15)
    @ExcelProperty(value = "部门",index = 3)
    @ApiModelProperty(value = "部门")
    private String deptDescr;

    @ColumnWidth(15)
    @ExcelProperty(value = "年度",index = 4)
    @ApiModelProperty(value = "年度")
    private String planYear;

    @ColumnWidth(15)
    @ExcelProperty(value = "培训形式",index = 5)
    @ApiModelProperty(value = "培训形式")
    private String trainShape;

    @ColumnWidth(15)
    @ExcelProperty(value = "培训项目",index = 6)
    @ApiModelProperty(value = "培训项目")
    private String trainName;

    @ColumnWidth(15)
    @ExcelProperty(value = "培训次数",index = 7)
    @ApiModelProperty(value = "培训次数")
    private Integer trainNum;

    @ColumnWidth(15)
    @ExcelProperty(value = "参训人次",index = 8)
    @ApiModelProperty(value = "参训人次")
    private Integer participantsNum;

    @ColumnWidth(15)
    @ExcelProperty(value = "培训课时",index = 9)
    @ApiModelProperty(value = "培训课时")
    private String classHour;

    @ColumnWidth(15)
    @ExcelProperty(value = "培训总学时",index = 10)
    @ApiModelProperty(value = "培训总学时")
    private Integer totalCourseClassHours;

    @ColumnWidth(15)
    @ExcelProperty(value = "培训费",index = 11)
    @ApiModelProperty(value = "培训费")
    private BigDecimal outlay;

    @ColumnWidth(15)
    @ExcelProperty(value = "开始时间",index = 12)
    @ApiModelProperty(value = "开始时间")
    private String startTime;

    @ColumnWidth(15)
    @ExcelProperty(value = "结束时间",index = 13)
    @ApiModelProperty(value = "结束时间")
    private String endTime;
}