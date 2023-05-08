package com.dyys.hr.entity.staff;

import com.dagongma.kernel.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * <p>
 * 技能信息 - 外语水平表
 * </p>
 *
 * @author yangye
 * @since 2022-08-23
 */
@Data
@Table(name = "staff_skills_lang")
@ApiModel(value = "StaffSkillsLang对象", description = "技能信息 - 外语水平表")
public class StaffSkillsLang extends BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("工号")
    @Column(name = "empl_id")
    private String emplId;

    @ApiModelProperty("外语语种")
    @Column(name = "languages")
    private String languages;

    @ApiModelProperty("获证日期")
    @Column(name = "cert_date")
    private Date certDate;

    @ApiModelProperty("等级")
    @Column(name = "ctfct_class")
    private String ctfctClass;

    @ApiModelProperty("分数")
    @Column(name = "score")
    private String score;

    @ApiModelProperty("发证机构")
    @Column(name = "ctfct_dept")
    private String ctfctDept;

    @ApiModelProperty("说明")
    @Column(name = "rmk")
    private String rmk;

    @ApiModelProperty("更新时间")
    @Column(name = "update_time")
    private Date updateTime;


}
