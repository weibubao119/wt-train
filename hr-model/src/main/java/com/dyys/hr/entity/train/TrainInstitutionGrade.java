package com.dyys.hr.entity.train;

import com.dagongma.kernel.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;

/**
 * <p>
 * 培训机构等级
 * </p>
 *
 * @author yangye
 * @since 2022-05-23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "培训机构等级")
@Table(name = "train_institution_grade")
public class TrainInstitutionGrade  extends BaseEntity<Long> {

    @Id
    @ApiModelProperty("主键ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "institution_id")
    @ApiModelProperty("机构ID")
    private Long institutionId;

    @Column(name = "yearly")
    @ApiModelProperty("年度")
    private String yearly;

    @Column(name = "grade_id")
    @ApiModelProperty("等级ID")
    private Integer gradeId;

    @Column(name = "memo")
    @ApiModelProperty("说明")
    private String memo;

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
