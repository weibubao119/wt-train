package com.dyys.hr.entity.train.excel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.fasterxml.jackson.annotation.JsonFormat;
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
public class CertificateExcel {
    @ColumnWidth(15)
    @ExcelProperty(value = "员工工号\n(必填)",index = 0)
    @ApiModelProperty(value = "员工工号")
    private String emplId;

    @ColumnWidth(15)
    @ExcelProperty(value = "员工姓名\n(必填)",index = 1)
    @ApiModelProperty(value = "员工姓名")
    private String emplName;

    @ColumnWidth(20)
    @ExcelProperty(value = "证书名称\n(必填)",index = 2)
    @ApiModelProperty(value = "证书名称")
    private String certificateName;

    @ColumnWidth(15)
    @ExcelProperty(value = "证书编号",index = 3)
    @ApiModelProperty(value = "证书编号")
    private String certificateNo;

    @ColumnWidth(15)
    @ExcelProperty(value = "证书等级",index = 4)
    @ApiModelProperty(value = "证书等级")
    private String certificateLevel;

    @ColumnWidth(20)
    @ExcelProperty(value = "发证机构名称\n(必填)",index = 5)
    @ApiModelProperty(value = "发证机构名称")
    private String issuingAgencyName;

    @ColumnWidth(30)
    @ExcelProperty(value = "获证日期(必填)\n格式例如：2023/1/1",index = 6)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "获证日期")
    private String startDate;

    @ColumnWidth(30)
    @ExcelProperty(value = "到期日期\n格式例如：2023/1/1",index = 7)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "到期日期")
    private String endDate;

    @ExcelIgnore
    @ApiModelProperty(value = "提示语")
    private String errMsg;
}
