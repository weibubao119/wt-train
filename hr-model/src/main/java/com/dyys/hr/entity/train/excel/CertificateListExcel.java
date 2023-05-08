package com.dyys.hr.entity.train.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CertificateListExcel {
    @ColumnWidth(15)
    @ExcelProperty(value = "员工工号",index = 0)
    private String emplId;

    @ColumnWidth(15)
    @ExcelProperty(value = "员工姓名",index = 1)
    private String emplName;

    @ColumnWidth(20)
    @ExcelProperty(value = "培训项目名称",index = 2)
    private String trainName;

    @ColumnWidth(15)
    @ExcelProperty(value = "公司编码",index = 3)
    private String company;

    @ColumnWidth(15)
    @ExcelProperty(value = "公司名称",index = 4)
    private String companyDescr;

    @ColumnWidth(15)
    @ExcelProperty(value = "部门名称",index = 5)
    private String deptDescr;

    @ColumnWidth(15)
    @ExcelProperty(value = "职族",index = 6)
    private String professionDescr;

    @ColumnWidth(15)
    @ExcelProperty(value = "职序",index = 7)
    private String secDescr;

    @ColumnWidth(15)
    @ExcelProperty(value = "职级",index = 8)
    private String gradeDescr;

    @ColumnWidth(15)
    @ExcelProperty(value = "证书名称",index = 9)
    private String certificateName;

    @ColumnWidth(15)
    @ExcelProperty(value = "证书编号",index = 10)
    private String certificateNo;

    @ColumnWidth(15)
    @ExcelProperty(value = "证书等级",index = 11)
    private String certificateLevel;

    @ColumnWidth(15)
    @ExcelProperty(value = "发证机构",index = 12)
    private String issuingAgencyName;

    @ColumnWidth(15)
    @ExcelProperty(value = "获证日期",index = 13)
    private String startDate;

    @ColumnWidth(15)
    @ExcelProperty(value = "到期日期",index = 14)
    private String endDate;
}
