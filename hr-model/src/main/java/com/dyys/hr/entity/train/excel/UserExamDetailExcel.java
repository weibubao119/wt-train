package com.dyys.hr.entity.train.excel;

import com.alibaba.excel.annotation.ExcelIgnore;
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
public class UserExamDetailExcel {
    @ColumnWidth(30)
    @ExcelProperty(value = "参考人员姓名",index = 0)
    private String emplName;

    @ColumnWidth(30)
    @ExcelProperty(value = "题目内容",index = 1)
    private String title;

    @ColumnWidth(30)
    @ExcelProperty(value = "题目类型",index = 1)
    @ExcelIgnore
    private Integer quType;

    @ColumnWidth(30)
    @ExcelProperty(value = "题目类型",index = 2)
    private String typeName;

    @ColumnWidth(30)
    @ExcelProperty(value = "题目分数",index = 3)
    private Integer score;

    @ColumnWidth(30)
    @ExcelProperty(value = "正确答案",index = 4)
    private String rightAnswer;

    @ColumnWidth(30)
    @ExcelProperty(value = "用户答案",index = 5)
    private String userAnswer;

    @ColumnWidth(30)
    @ExcelProperty(value = "答题结果",index = 6)
    private String result;

}
