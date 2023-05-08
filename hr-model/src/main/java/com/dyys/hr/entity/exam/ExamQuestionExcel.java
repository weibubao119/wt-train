package com.dyys.hr.entity.exam;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
public class ExamQuestionExcel {

    @ColumnWidth(25)
    @ExcelProperty(value = "题目类型(必选)\n(1单选/2多选\n/3判断/4主观)",index = 0)
    @ApiModelProperty(value = "题目类型")
    private Integer quType;

    @ColumnWidth(25)
    @ExcelProperty(value = "题目内容(标题)\n(必填)",index = 1)
    @ApiModelProperty(value = "题目内容(标题)")
    private String content;

    @ColumnWidth(30)
    @ExcelProperty(value = "题目分数\n(单选/多选/判断必填)",index = 2)
    @ApiModelProperty(value = "题目分数")
    private Integer score;

    @ColumnWidth(20)
    @ExcelProperty(value = "整题解析",index = 3)
    @ApiModelProperty(value = "整题解析")
    private String analysis;

    @ColumnWidth(45)
    @ExcelProperty(value = "选项标识和选项内容\n(单选/多选必填)\n(选项标识使用小写英文字母a~g)\n(选项标识与选项内容用“.”隔开)\n(各选项之间以换行隔开,例如：)\na.选项内容1\nb.选项内容2\nc.选项内容3\n...",index = 4)
    @ApiModelProperty(value = "选项标识和选项内容")
    private String answers;

    @ColumnWidth(50)
    @ExcelProperty(value = "正确答案(单选/多选/判断必填)\n(选择题使用小写英文字母a~g)\n(多选题答案用“、”隔开，例如：a、b)\n(判断题只填1或0，1对、0错)",index = 5)
    @ApiModelProperty(value = "正确答案")
    private String rightAnswer;

    @ExcelIgnore
    @ApiModelProperty(value = "提示语")
    private String errMsg;
}
