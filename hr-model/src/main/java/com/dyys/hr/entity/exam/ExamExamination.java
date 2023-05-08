package com.dyys.hr.entity.exam;

import com.dagongma.kernel.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * <p>
 * 考试信息
 * </p>
 *
 * @author lidaan
 * @since 2022-04-21
 */
@Data
@Table(name = "exam_examination")
@ApiModel(value = "ExamExamination对象", description = "考试信息")
public class ExamExamination extends BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("试卷id")
    @Column(name = "paper_id")
    private String paperId;

    @ApiModelProperty("考试标题")
    @Column(name = "title")
    private String title;

    @ApiModelProperty("考试类型(0:线上考试，1:线下考试)")
    @Column(name = "type")
    private Integer type;

    @ApiModelProperty("关联课程id")
    @Column(name = "class_id")
    private String classId;

    @ApiModelProperty("考试总时长")
    @Column(name = "duration")
    private String duration;

    @ApiModelProperty("考试及格分")
    @Column(name = "pass_score")
    private Float passScore;

    @ApiModelProperty("考试总分")
    @Column(name = "totle_score")
    private Float totleScore;

    @ApiModelProperty("状态(1:正在进行，2:结束)")
    @Column(name = "status")
    private Integer status;

    @ApiModelProperty("人数")
    @Column(name = "user_count")
    private Integer userCount;

    @ApiModelProperty("是否限时(0: 不限时，1: 限时)")
    @Column(name = "is_limit")
    private Boolean isLimit;

    @ApiModelProperty("开始时间")
    @Column(name = "start_time")
    private Date startTime;

    @ApiModelProperty("结束时间")
    @Column(name = "end_time")
    private Date endTime;

    @ApiModelProperty("考试次数")
    @Column(name = "exam_count")
    private Integer examCount;

    @ApiModelProperty("创建人")
    @Column(name = "creator")
    private String creator;

    @ApiModelProperty("创建时间")
    @Column(name = "creat_time")
    private Date creatTime;

    @ApiModelProperty("修改人")
    @Column(name = "updater")
    private String updater;

    @ApiModelProperty("修改时间")
    @Column(name = "update_time")
    private Date updateTime;


}
