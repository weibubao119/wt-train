package com.dyys.hr.entity.staff;

import com.dagongma.kernel.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * <p>
 * 奖惩信息 - 获奖信息表
 * </p>
 *
 * @author yangye
 * @since 2022-08-23
 */
@Data
@Table(name = "staff_rwd")
@ApiModel(value = "StaffRwd对象", description = "奖惩信息 - 获奖信息表")
public class StaffRwd extends BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("工号")
    @Column(name = "empl_id")
    private String emplId;

    @ApiModelProperty("获奖年度")
    @Column(name = "prd_id")
    private String prdId;

    @ApiModelProperty("获奖日期")
    @Column(name = "rwd_date")
    private Date rwdDate;

    @ApiModelProperty("获奖名称")
    @Column(name = "name")
    private String name;

    @ApiModelProperty("获奖级别")
    @Column(name = "level")
    private String level;

    @ApiModelProperty("获奖等级")
    @Column(name = "empl_id")
    private String grade;

    @ApiModelProperty("获奖原因")
    @Column(name = "reason")
    private String reason;

    @ApiModelProperty("授予单位")
    @Column(name = "grant_business")
    private String grantBusiness;

    @ApiModelProperty("授予部门")
    @Column(name = "grant_dept")
    private String grantDept;

    @ApiModelProperty("更新时间")
    @Column(name = "update_time")
    private Date updateTime;


}
