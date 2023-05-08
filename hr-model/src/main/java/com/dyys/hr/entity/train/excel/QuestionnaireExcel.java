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
public class QuestionnaireExcel {
    @ColumnWidth(30)
    @ExcelProperty(value = "评估名称",index = 0)
    private String title;

    @ColumnWidth(30)
    @ExcelProperty(value = "评估内容",index = 1)
    private String value;

    @ColumnWidth(30)
    @ExcelProperty(value = "说明",index = 2)
    private String descr;
}
