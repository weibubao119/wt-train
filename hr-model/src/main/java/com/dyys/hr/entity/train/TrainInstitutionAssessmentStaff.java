package com.dyys.hr.entity.train;

import com.dagongma.kernel.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;

/**
 * <p>
 * 培训机构评估参与人员
 * </p>
 *
 * @author yangye
 * @since 2022-05-11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "培训机构评估记录")
@Table(name = "train_institution_assessment_staff")
public class TrainInstitutionAssessmentStaff extends BaseEntity<Long> {

    @Id
    @ApiModelProperty("主键ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "assessment_id")
    @ApiModelProperty("评估记录ID")
    private Long assessmentId;

    @Column(name = "questionnaire_id")
    @ApiModelProperty("评估问卷ID")
    private Long questionnaireId;

    @Column(name = "evaluator_empl_id")
    @ApiModelProperty("评估人ID")
    private String evaluatorEmplId;

    @Column(name = "evaluator_company_id")
    @ApiModelProperty("评估人所属公司ID")
    private String evaluatorCompanyId;

    @Column(name = "evaluator_dept_id")
    @ApiModelProperty("评估人所属部门ID")
    private String evaluatorDeptId;

    @Column(name = "evaluator_job_code")
    @ApiModelProperty("评估人所在岗位ID")
    private String evaluatorJobCode;

    @Column(name = "answer_id")
    @ApiModelProperty("答卷ID")
    private Long answerId;

    @Column(name = "is_finished")
    @ApiModelProperty("是否完成评估：0未完成(默认)，1已完成")
    private Integer isFinished;

    @Column(name = "is_delete")
    @ApiModelProperty("是否移除：0未删(默认)，1已移除")
    private Integer isDelete;

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
