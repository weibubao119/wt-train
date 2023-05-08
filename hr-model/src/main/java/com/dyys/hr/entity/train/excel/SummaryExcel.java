package com.dyys.hr.entity.train.excel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.ALWAYS)
public class SummaryExcel {
    @ColumnWidth(15)
    @ExcelProperty(value = "学员工号\n(必填)",index = 0)
    @ApiModelProperty(value = "学员工号")
    private String emplId;

    @ColumnWidth(15)
    @ExcelProperty(value = "学员姓名\n(必填)",index = 1)
    @ApiModelProperty(value = "学员姓名")
    private String emplName;

    @ColumnWidth(15)
    @ExcelProperty(value = "培训总成绩\n(必填)",index = 2)
    @ApiModelProperty(value = "培训总成绩")
    private String totalScore;

    @ColumnWidth(15)
    @ExcelProperty(value = "是否获证\n(1是，0否)",index = 3)
    @ApiModelProperty(value = "是否获证")
    private Integer isObtain;

    @ColumnWidth(20)
    @ExcelProperty(value = "证书名称\n(获证必填)",index = 4)
    @ApiModelProperty(value = "证书名称")
    private String certificateName;

    @ColumnWidth(15)
    @ExcelProperty(value = "证书编号",index = 5)
    @ApiModelProperty(value = "证书编号")
    private String certificateNo;

    @ColumnWidth(15)
    @ExcelProperty(value = "证书等级",index = 6)
    @ApiModelProperty(value = "证书等级")
    private String certificateLevel;

    @ColumnWidth(20)
    @ExcelProperty(value = "发证机构名称\n(获证必填)",index = 7)
    @ApiModelProperty(value = "发证机构名称")
    private String issuingAgencyName;

    @ColumnWidth(30)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @ExcelProperty(value = "获证日期(获证必填)\n格式例如：2023/1/1",index = 8)
    @ApiModelProperty(value = "获证日期")
    private String startDate;

    @ColumnWidth(30)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @ExcelProperty(value = "到期日期\n格式例如：2023/1/1",index = 9)
    @ApiModelProperty(value = "到期日期")
    private String endDate;

    @ExcelIgnore
    @ApiModelProperty(value = "提示语")
    private String errMsg;
}
