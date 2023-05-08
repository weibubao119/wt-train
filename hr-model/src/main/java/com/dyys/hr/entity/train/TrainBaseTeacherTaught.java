package com.dyys.hr.entity.train;

import java.math.BigDecimal;
import java.util.Date;

import com.dagongma.kernel.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;

/**
 * <p>
 * 讲师已授课程记录
 * </p>
 *
 * @author yangye
 * @since 2023-01-10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "TrainBaseTeacherTaught对象", description = "讲师已授课程记录")
@Table(name = "train_base_teacher_taught")
public class TrainBaseTeacherTaught extends BaseEntity<Long> {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("讲师ID")
    @Column(name = "teacher_id")
    private Long teacherId;

    @ApiModelProperty("培训项目ID")
    @Column(name = "programs_id")
    private Long programsId;

    @ApiModelProperty("课程ID")
    @Column(name = "course_id")
    private Long courseId;

    @ApiModelProperty("课程开始日期")
    @Column(name = "course_start_time")
    private Date courseStartTime;

    @ApiModelProperty("课程结束日期")
    @Column(name = "course_end_time")
    private Date courseEndTime;

    @ApiModelProperty("学习形式")
    @Column(name = "learning_form")
    private Integer learningForm;

    @ApiModelProperty("考核方式：1考试，2考察")
    @Column(name = "examine_form")
    private Integer examineForm;

    @ApiModelProperty("参训人数")
    @Column(name = "participants_total")
    private Integer participantsTotal;

    @ApiModelProperty("课程评分")
    @Column(name = "course_score")
    private BigDecimal courseScore;

    @ApiModelProperty("讲师评分")
    @Column(name = "teacher_score")
    private BigDecimal teacherScore;

    @ApiModelProperty("培训机构ID")
    @Column(name = "institution_id")
    private Long institutionId;

    @ApiModelProperty("课程培训费用")
    @Column(name = "train_fee")
    private BigDecimal trainFee;

    @ApiModelProperty("创建人")
    @Column(name = "create_user")
    private String createUser;

    @ApiModelProperty("创建时间")
    @Column(name = "create_time")
    private Long createTime;


}
