package com.dyys.hr.vo.train;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author wsj
 * @projectName hr-recruit
 * @date 2023/2/914:55
 */
@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "培训项目费用明细vo")
public class TrainCostDetailsVO implements Serializable {
    @ColumnWidth(15)
    @ExcelProperty(value = "序号",index = 0)
    @ApiModelProperty(value = "序号")
    private Long id;

    @ColumnWidth(15)
    @ExcelProperty(value = "年度",index = 1)
    @ApiModelProperty(value = "年度")
    private String planYear;

    @ColumnWidth(15)
    @ExcelProperty(value = "所属计划",index = 2)
    @ApiModelProperty(value = "所属计划")
    private String planName;

    @ColumnWidth(15)
    @ExcelProperty(value = "公司名称",index = 3)
    @ApiModelProperty(value = "公司名称")
    private String companyDescr;

    @ColumnWidth(15)
    @ExcelProperty(value = "部门名称",index = 4)
    @ApiModelProperty(value = "部门名称")
    private String deptDescr;

    @ColumnWidth(15)
    @ExcelProperty(value = "培训项目编号",index = 5)
    @ApiModelProperty(value = "培训项目编号")
    private String trainNumber;

    @ColumnWidth(15)
    @ExcelProperty(value = "培训名称",index = 6)
    @ApiModelProperty(value = "培训名称")
    private String trainName;

    @ColumnWidth(15)
    @ExcelProperty(value = "培训形式",index = 7)
    @ApiModelProperty(value = "培训形式")
    private String trainShape;

    @ColumnWidth(15)
    @ExcelProperty(value = "实际参训人数",index = 8)
    @ApiModelProperty(value = "实际参训人数")
    private Integer actualParticipantsNum;

    @ColumnWidth(15)
    @ExcelProperty(value = "培训总费用",index = 9)
    @ApiModelProperty(value = "培训总费用")
    private String totalAmount;

    @ExcelIgnore
    @ApiModelProperty(value = "培训项目序号")
    private Long programsId;

    @ExcelIgnore
    @ApiModelProperty(value = "培训科目费用列表")
    private List<Map<String,Object>> subjectAmountList;
}