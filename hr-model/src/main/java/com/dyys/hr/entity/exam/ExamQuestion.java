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
 * 试题信息
 * </p>
 *
 * @author lidaan
 * @since 2022-04-20
 */
@Data
@Table(name = "exam_question")
@ApiModel(value = "ExamQuestion对象", description = "试题信息")
public class ExamQuestion extends BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("题目类型(1:单选，2:多选，3:判断，4:主观)")
    @Column(name = "qu_type")
    private Integer quType;

    @ApiModelProperty("题目分数")
    @Column(name = "score")
    private Integer score;

    @ApiModelProperty("题目图片")
    @Column(name = "image")
    private String image;

    @ApiModelProperty("题目内容")
    @Column(name = "content")
    private String content;

    @ApiModelProperty("题目备注")
    @Column(name = "remark")
    private String remark;

    @ApiModelProperty("整题解析")
    @Column(name = "analysis")
    private String analysis;

    @ApiModelProperty("正确答案")
    @Column(name = "right_answer")
    private String rightAnswer;

    @ApiModelProperty("是否删除(0:否, 1:是)")
    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @ApiModelProperty("创建人")
    @Column(name = "creator")
    private String creator;

    @ApiModelProperty("创建时间")
    @Column(name = "create_date")
    private Date createDate;
}
