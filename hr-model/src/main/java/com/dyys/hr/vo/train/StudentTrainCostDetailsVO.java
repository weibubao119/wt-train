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
@ApiModel(value = "学员培训费用明细vo")
public class StudentTrainCostDetailsVO implements Serializable {
    @ColumnWidth(15)
    @ExcelProperty(value = "序号",index = 0)
    @ApiModelProperty(value = "序号")
    private Long id;

    @ColumnWidth(15)
    @ExcelProperty(value = "工号",index = 1)
    @ApiModelProperty(value = "工号")
    private String emplId;

    @ColumnWidth(15)
    @ExcelProperty(value = "姓名",index = 2)
    @ApiModelProperty(value = "姓名")
    private String emplName;

    @ColumnWidth(15)
    @ExcelProperty(value = "所在公司",index = 3)
    @ApiModelProperty(value = "所在公司")
    private String companyDescr;

    @ColumnWidth(15)
    @ExcelProperty(value = "所在部门",index = 4)
    @ApiModelProperty(value = "所在部门")
    private String deptDescr;

    @ColumnWidth(15)
    @ExcelProperty(value = "职族",index = 5)
    @ApiModelProperty(value = "职族")
    private String professionDescr;

    @ColumnWidth(15)
    @ExcelProperty(value = "职序",index = 6)
    @ApiModelProperty(value = "职序")
    private String secDescr;

    @ColumnWidth(15)
    @ExcelProperty(value = "职位层级",index = 7)
    @ApiModelProperty(value = "职位层级")
    private String gradeDescr;

    @ColumnWidth(15)
    @ExcelProperty(value = "职位等级",index = 8)
    @ApiModelProperty(value = "职位等级")
    private String levelDescr;

    @ColumnWidth(15)
    @ExcelProperty(value = "年度",index = 9)
    @ApiModelProperty(value = "年度")
    private String planYear;

    @ColumnWidth(15)
    @ExcelProperty(value = "培训项目编号",index = 10)
    @ApiModelProperty(value = "培训项目编号")
    private String trainNumber;

    @ColumnWidth(15)
    @ExcelProperty(value = "培训项目名称",index = 11)
    @ApiModelProperty(value = "培训项目名称")
    private String trainName;

    @ColumnWidth(15)
    @ExcelProperty(value = "培训形式",index = 12)
    @ApiModelProperty(value = "培训形式")
    private String trainShapeName;

    @ColumnWidth(15)
    @ExcelProperty(value = "实际参训人数",index = 13)
    @ApiModelProperty(value = "实际参训人数")
    private Integer participantsNum;

    @ColumnWidth(15)
    @ExcelProperty(value = "项目培训总费用",index = 14)
    @ApiModelProperty(value = "项目培训总费用")
    private String outlay;

    @ColumnWidth(15)
    @ExcelProperty(value = "个人培训费用",index = 15)
    @ApiModelProperty(value = "个人培训费用")
    private String avgFee;
}