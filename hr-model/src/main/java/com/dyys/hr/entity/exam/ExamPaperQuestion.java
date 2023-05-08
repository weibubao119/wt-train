package com.dyys.hr.entity.exam;

import com.dagongma.kernel.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

/**
 * <p>
 * 试卷试题
 * </p>
 *
 * @author lidaan
 * @since 2022-04-21
 */
@Data
@Table(name = "exam_paper_question")
@ApiModel(value = "ExamPaperQuestion对象", description = "试卷试题")
public class ExamPaperQuestion extends BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("试卷id")
    @Column(name = "paper_id")
    private String paperId;

    @ApiModelProperty("题目ID")
    @Column(name = "qu_id")
    private String quId;

    @ApiModelProperty("排序")
    @Column(name = "sort")
    private Integer sort;
}
