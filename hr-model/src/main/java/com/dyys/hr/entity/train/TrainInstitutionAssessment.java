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
 * 培训机构评估记录
 * </p>
 *
 * @author yangye
 * @since 2022-05-11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "培训机构评估记录")
@Table(name = "train_institution_assessment")
public class TrainInstitutionAssessment extends BaseEntity<Long> {

    @Id
    @ApiModelProperty("主键ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "institution_id")
    @ApiModelProperty("机构ID")
    private Long institutionId;

    @Column(name = "title")
    @ApiModelProperty("评估标题")
    private String title;

    @Column(name = "questionnaire_id")
    @ApiModelProperty("问卷ID")
    private Long questionnaireId;

    @Column(name = "yearly")
    @ApiModelProperty("年度")
    private String yearly;

    @Column(name = "start_time")
    @ApiModelProperty("评估开始时间")
    private Date startTime;

    @Column(name = "end_time")
    @ApiModelProperty("评估结束时间")
    private Date endTime;

    @Column(name = "score")
    @ApiModelProperty("评估得分")
    private BigDecimal score;

    @Column(name = "status")
    @ApiModelProperty("状态：1未开始，2进行中，3已结束，4已取消")
    private Integer status;

    @Column(name = "create_user")
    @ApiModelProperty("创建人ID")
    private String createUser;

    @Column(name = "create_time")
    @ApiModelProperty("创建时间")
    private Long createTime;

    @Column(name = "update_user")
    @ApiModelProperty("更新人ID")
    private String updateUser;

    @Column(name = "update_time")
    @ApiModelProperty("更新时间")
    private Long updateTime;


}
