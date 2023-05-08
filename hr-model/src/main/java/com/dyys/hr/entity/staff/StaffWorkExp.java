package com.dyys.hr.entity.staff;

import com.dagongma.kernel.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * <p>
 * 工作履历信息 - 外部工作经历表
 * </p>
 *
 * @author yangye
 * @since 2022-08-31
 */
@Data
@Table(name = "staff_work_exp")
@ApiModel(value = "StaffWorkExp对象", description = "工作履历信息 - 外部工作经历表")
public class StaffWorkExp extends BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("工号")
    @Column(name = "empl_id")
    private String emplId;

    @ApiModelProperty("开始日期")
    @Column(name = "start_date")
    private Date startDate;

    @ApiModelProperty("结束日期")
    @Column(name = "end_date")
    private Date endDate;

    @ApiModelProperty("工作单位")
    @Column(name = "work_units")
    private String workUnits;

    @ApiModelProperty("所在部门")
    @Column(name = "dept")
    private String dept;

    @ApiModelProperty("岗位/职务")
    @Column(name = "pos")
    private String pos;

    @ApiModelProperty("工作描述")
    @Column(name = "work_exp_des")
    private String workExpDes;

    @ApiModelProperty("离职原因")
    @Column(name = "leaving_reason")
    private String leavingReason;

    @ApiModelProperty("更新时间")
    @Column(name = "update_time")
    private Date updateTime;


}
