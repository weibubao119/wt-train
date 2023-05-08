package com.dyys.hr.entity.staff;

import com.dagongma.kernel.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * <p>
 * 奖惩信息 - 惩罚信息表
 * </p>
 *
 * @author yangye
 * @since 2022-08-23
 */
@Data
@Table(name = "staff_rwd_pns")
@ApiModel(value = "StaffRwdPns对象", description = "奖惩信息 - 惩罚信息表")
public class StaffRwdPns extends BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("工号")
    @Column(name = "empl_id")
    private String emplId;

    @ApiModelProperty("惩罚年度")
    @Column(name = "prd_id")
    private String prdId;

    @ApiModelProperty("惩罚日期")
    @Column(name = "rwd_date")
    private Date rwdDate;

    @ApiModelProperty("惩罚名称")
    @Column(name = "name")
    private String name;

    @ApiModelProperty("惩罚级别")
    @Column(name = "level")
    private String level;

    @ApiModelProperty("惩罚等级")
    @Column(name = "grade")
    private String grade;

    @ApiModelProperty("惩罚金额")
    @Column(name = "pns_account")
    private String pnsAccount;

    @ApiModelProperty("惩罚单位")
    @Column(name = "grant_business")
    private String grantBusiness;

    @ApiModelProperty("惩罚部门")
    @Column(name = "grant_dept")
    private String grantDept;

    @ApiModelProperty("更新时间")
    @Column(name = "update_time")
    private Date updateTime;


}
