package com.dyys.hr.entity.train.excel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Desc
 * Create by yangye
 * Date 2022/9/26 10:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrainSiteExcel {
    @ColumnWidth(20)
    @ExcelProperty(value = "场地名称\n(必填)",index = 0)
    @ApiModelProperty(value = "场地名称")
    private String siteName;

    @ColumnWidth(20)
    @ExcelProperty(value = "组织编码\n(必填)",index = 1)
    @ApiModelProperty(value = "组织编码")
    private String deptId;

    @ColumnWidth(20)
    @ExcelProperty(value = "组织名称\n(必填)",index = 2)
    @ApiModelProperty(value = "组织名称")
    private String deptName;

    @ColumnWidth(20)
    @ExcelProperty(value = "负责人姓名\n(必填)",index = 3)
    @ApiModelProperty(value = "负责人姓名")
    private String principal;

    @ColumnWidth(20)
    @ExcelProperty(value = "联系电话\n(必填)",index = 4)
    @ApiModelProperty(value = "联系电话")
    private String contactPhone;

    @ColumnWidth(20)
    @ExcelProperty(value = "可容纳人数\n(必填)",index = 5)
    @ApiModelProperty(value = "可容纳人数")
    private Integer maxCapacity;

    @ColumnWidth(20)
    @ExcelProperty(value = "场地地址\n(必填)",index = 6)
    @ApiModelProperty(value = "场地地址")
    private String address;

    @ExcelIgnore
    @ApiModelProperty(value = "提示语")
    private String errMsg;
}
