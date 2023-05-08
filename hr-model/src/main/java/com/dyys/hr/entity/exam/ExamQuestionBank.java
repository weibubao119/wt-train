package com.dyys.hr.entity.exam;

import com.dagongma.kernel.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 题库信息
 * </p>
 *
 * @author lidaan
 * @since 2022-04-20
 */
@Data
@Table(name = "exam_question_bank")
@ApiModel(value = "ExamQuestionBank对象", description = "题库信息")
public class ExamQuestionBank extends BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("题库名称")
    @Column(name = "title")
    private String title;

    @ApiModelProperty("单选题数量")
    @Column(name = "radio_count")
    private Integer radioCount;

    @ApiModelProperty("多选题数量")
    @Column(name = "multi_count")
    private Integer multiCount;

    @ApiModelProperty("判断题数量")
    @Column(name = "judge_count")
    private Integer judgeCount;

    @ApiModelProperty("主观题数量")
    @Column(name = "subjective_count")
    private Integer subjectiveCount;

    @ApiModelProperty("是否删除(0:否, 1:是)")
    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @ApiModelProperty("创建人")
    @Column(name  = "creator")
    private String creator;

    @ApiModelProperty("创建时间")
    @Column(name  = "create_date")
    private Date createDate;

    @ApiModelProperty("更新人")
    @Column(name = "updater")
    private String updater;

    @ApiModelProperty("更新时间")
    @Column(name = "update_date")
    private Date updateDate;
}
