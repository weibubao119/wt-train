package com.dyys.hr.entity.staff;

import com.dagongma.kernel.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * <p>
 * 技能信息 - 计算机水平表
 * </p>
 *
 * @author yangye
 * @since 2022-08-23
 */
@Data
@Table(name = "staff_skills_pc")
@ApiModel(value = "StaffSkillsPc对象", description = "技能信息 - 计算机水平表")
public class StaffSkillsPc extends BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("工号")
    @Column(name = "empl_id")
    private String emplId;

    @ApiModelProperty("计算机技能")
    @Column(name = "com_skills")
    private String comSkills;

    @ApiModelProperty("获证日期")
    @Column(name = "start_date")
    private Date startDate;

    @ApiModelProperty("等级")
    @Column(name = "level")
    private String level;

    @ApiModelProperty("分数")
    @Column(name = "score")
    private String score;

    @ApiModelProperty("发证机构")
    @Column(name = "agency")
    private String agency;

    @ApiModelProperty("说明")
    @Column(name = "instructions")
    private String instructions;

    @ApiModelProperty("更新时间")
    @Column(name = "update_time")
    private Date updateTime;


}
