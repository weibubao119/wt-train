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
public class QuestionnaireStaticsExcel {
    @ColumnWidth(60)
    @ExcelProperty(value = "名称",index = 0)
    private String title;

    @ColumnWidth(30)
    @ExcelProperty(value = "人数",index = 1)
    private String total;

    @ColumnWidth(30)
    @ExcelProperty(value = "平均分",index = 2)
    private String avg;
}
