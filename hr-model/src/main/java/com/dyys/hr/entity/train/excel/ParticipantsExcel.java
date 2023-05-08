package com.dyys.hr.entity.train.excel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.ALWAYS)
public class ParticipantsExcel {
    @ColumnWidth(15)
    @ExcelProperty(value = "员工工号(必填)",index = 0)
    @ApiModelProperty(value = "员工工号")
    private String emplId;

    @ColumnWidth(15)
    @ExcelProperty(value = "员工姓名(必填)",index = 1)
    @ApiModelProperty(value = "员工姓名")
    private String emplName;

    @ExcelIgnore
    @ApiModelProperty(value = "单位编号")
    private String companyCode;

    @ExcelIgnore
    @ApiModelProperty(value = "单位名称")
    private String companyName;

    @ExcelIgnore
    @ApiModelProperty(value = "部门编号")
    private String departmentCode;

    @ExcelIgnore
    @ApiModelProperty(value = "部门名称")
    private String departmentName;

    @ExcelIgnore
    @ApiModelProperty(value = "部门编号")
    private String deptCode;

    @ExcelIgnore
    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @ExcelIgnore
    @ApiModelProperty(value = "职位/岗位编号")
    private String jobCode;

    @ExcelIgnore
    @ApiModelProperty(value = "职位/岗位名称")
    private String jobName;

    @ExcelIgnore
    @ApiModelProperty(value = "职位/岗位编号")
    private String postCode;

    @ExcelIgnore
    @ApiModelProperty(value = "职位/岗位名称")
    private String postName;

    @ExcelIgnore
    @ApiModelProperty(value = "错误提示语")
    private String errMsg;
}
