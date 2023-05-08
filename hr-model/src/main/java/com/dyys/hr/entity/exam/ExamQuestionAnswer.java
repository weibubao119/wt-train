package com.dyys.hr.entity.exam;

import com.dagongma.kernel.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * <p>
 * 问题答案选项
 * </p>
 *
 * @author lidaan
 * @since 2022-04-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "exam_question_answer")
@ApiModel(value = "ExamQuestionAnswer对象", description = "问题答案选项")
public class ExamQuestionAnswer extends BaseEntity<Long> {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("问题ID")
    @Column(name = "qu_id")
    private String quId;

    @ApiModelProperty("是否正确答案(1:是，0:否)")
    @Column(name = "is_right")
    private Integer isRight;

    @ApiModelProperty("选项图片")
    @Column(name = "image")
    private String image;

    @ApiModelProperty("答案内容")
    @Column(name = "content")
    private String content;

    @ApiModelProperty("答案分析")
    @Column(name = "analysis")
    private String analysis;

    @ApiModelProperty("排序")
    @Column(name = "sort")
    private Integer sort;

    @ApiModelProperty("选项标识(abcd)")
    @Column(name = "item_index")
    private String itemIndex;
}
