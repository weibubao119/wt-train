package com.dyys.hr.entity.exam;

import com.dagongma.kernel.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

/**
 * <p>
 * 员工答卷备选答案
 * </p>
 *
 * @author lidaan
 * @since 2022-04-20
 */
@Data
@Table(name="exam_user_answer")
@ApiModel(value = "ExamUserAnswer对象", description = "用户考试提交答案")
public class ExamUserAnswer extends BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("考试记录id")
    @Column(name = "exam_id")
    private String examId;

    @ApiModelProperty("试卷id")
    @Column(name = "paper_id")
    private String paperId;

    @ApiModelProperty("员工答卷id")
    @Column(name = "details_id")
    private String detailsId;

    @ApiModelProperty("回答项")
    @Column(name = "answer")
    private String answer;

    @ApiModelProperty("题目ID")
    @Column(name = "qu_id")
    private String quId;

    @ApiModelProperty("员工ID")
    @Column(name = "user_id")
    private String userId;

    @ApiModelProperty("是否正确项")
    @Column(name = "is_right")
    private Integer isRight;

    @ApiModelProperty("主观题答案")
    @Column(name = "content")
    private String content;
}
