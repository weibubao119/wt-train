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
 * Date 2022/9/25 14:40
 */
@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
public class InstitutionExcel {
    @ColumnWidth(15)
    @ExcelProperty(value = "机构名称\n(必填)",index = 0)
    @ApiModelProperty(value = "机构名称")
    private String name;

    @ColumnWidth(15)
    @ExcelProperty(value = "机构类型\n(必选)",index = 1)
    @ApiModelProperty(value = "机构类型")
    private String cateIdName;

    @ColumnWidth(20)
    @ExcelProperty(value = "机构联系人\n姓名(必填)",index = 2)
    @ApiModelProperty(value = "机构联系人姓名")
    private String principalName;

    @ColumnWidth(20)
    @ExcelProperty(value = "机构联系人\n电话(必填)",index = 3)
    @ApiModelProperty(value = "机构联系人电话")
    private String principalPhone;

    @ColumnWidth(15)
    @ExcelProperty(value = "机构地址",index = 4)
    @ApiModelProperty(value = "机构地址")
    private String address;

    @ColumnWidth(15)
    @ExcelProperty(value = "评级年度",index = 5)
    @ApiModelProperty(value = "评级年度")
    private String ratingYear;

    @ColumnWidth(15)
    @ExcelProperty(value = "评级等级",index = 6)
    @ApiModelProperty(value = "评级等级")
    private String gradeIdName;

    @ColumnWidth(20)
    @ExcelProperty(value = "评级说明",index = 7)
    @ApiModelProperty(value = "评级说明")
    private String memo;

    @ExcelIgnore
    @ApiModelProperty(value = "提示语")
    private String errMsg;
}
