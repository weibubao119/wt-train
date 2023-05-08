package com.dyys.hr.vo.exam;

import com.dyys.hr.entity.exam.ExamQuestionAnswer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author: Daan li
 * @Date: 2022/4/15 15:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ExamQuestionVO对象", description = "试题")
public class ExamQuestionVO implements Serializable {
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("试卷ID")
    private String paperId;

    @ApiModelProperty("题目类型(1:单选，2:多选，3:判断，4:主观)")
    private Integer quType;

    @ApiModelProperty("题目分数")
    private Integer score;

    @ApiModelProperty("题目排序")
    private Integer sort;

    @ApiModelProperty("题目图片")
    private String image;

    @ApiModelProperty("题目内容")
    private String content;

    @ApiModelProperty("题目备注")
    private String remark;

    @ApiModelProperty("整题解析")
    private String analysis;

    @ApiModelProperty("正确答案")
    private String rightAnswer;

    @ApiModelProperty("是否删除(0:否, 1:是)")
    private Integer isDeleted;

    @ApiModelProperty("创建人")
    private String creator;

    @ApiModelProperty("创建时间")
    private Date createDate;

    @ApiModelProperty("答案列表")
    private List<ExamQuestionAnswer> answers;
}
