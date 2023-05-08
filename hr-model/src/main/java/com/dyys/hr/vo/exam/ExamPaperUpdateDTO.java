package com.dyys.hr.vo.exam;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import java.util.List;

@Data
public class ExamPaperUpdateDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("试卷名称")
    @Column(name = "name")
    private String name;

    @ApiModelProperty("试卷描述")
    @Column(name = "content")
    private String content;

    @ApiModelProperty("试卷总分")
    @Column(name = "totle")
    private String totle;

    @ApiModelProperty("及格线分数")
    @Column(name = "pass_grade")
    private String passGrade;

    @ApiModelProperty("考试时长(分钟)")
    @Column(name = "duration")
    private String duration;

    @ApiModelProperty("是否限时(0:否, 1:是)")
    @Column(name = "is_limite")
    private Boolean isLimite;

    @ApiModelProperty("考试开始时间")
    @Column(name = "start_time")
    private Date startTime;

    @ApiModelProperty("考试结束时间")
    @Column(name = "end_time")
    private Date endTime;

    @ApiModelProperty("状态(1:进行中, 2:结束)")
    @Column(name = "status")
    private Integer status;

    @ApiModelProperty("是否删除(0:否, 1:是)")
    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @ApiModelProperty("创建人")
    @Column(name = "creator")
    private String creator;

    @ApiModelProperty("创建时间")
    @Column(name = "create_date")
    private Date createDate;

    @ApiModelProperty("创建人")
    @Column(name = "updater")
    private String updater;

    @ApiModelProperty("创建时间")
    @Column(name = "update_date")
    private Date updateDate;

    @ApiModelProperty("试题id列表")
    private List<Long> questionList;
}
