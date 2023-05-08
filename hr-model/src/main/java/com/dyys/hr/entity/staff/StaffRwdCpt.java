package com.dyys.hr.entity.staff;

import com.dagongma.kernel.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * <p>
 * 奖惩信息 - 技能竞赛信息表
 * </p>
 *
 * @author yangye
 * @since 2022-08-23
 */
@Data
@Table(name = "staff_rwd_cpt")
@ApiModel(value = "StaffRwdCpt对象", description = "奖惩信息 - 技能竞赛信息表")
public class StaffRwdCpt extends BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("工号")
    @Column(name = "empl_id")
    private String emplId;

    @ApiModelProperty("竞赛级别")
    @Column(name = "grade")
    private String grade;

    @ApiModelProperty("竞赛名称")
    @Column(name = "name")
    private String name;

    @ApiModelProperty("获奖名词")
    @Column(name = "rwd_rank")
    private String rwdRank;

    @ApiModelProperty("获奖日期")
    @Column(name = "rwd_date")
    private Date rwdDate;

    @ApiModelProperty("组织单位")
    @Column(name = "org_business")
    private String orgBusiness;

    @ApiModelProperty("发证机构")
    @Column(name = "grant_business")
    private String grantBusiness;

    @ApiModelProperty("更新时间")
    @Column(name = "update_time")
    private Date updateTime;


}
