package com.dyys.hr.entity.staff;

import com.dagongma.kernel.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * <p>
 * 项目经历表
 * </p>
 *
 * @author yangye
 * @since 2022-08-23
 */
@Data
@Table(name = "staff_project")
@ApiModel(value = "StaffProject对象", description = "项目经历表")
public class StaffProject extends BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("工号")
    @Column(name = "empl_id")
    private String emplId;

    @ApiModelProperty("项目经历类型（10入职前，20入职后）")
    @Column(name = "project_type")
    private Integer projectType;

    @ApiModelProperty("开始日期")
    @Column(name = "start_date")
    private Date startDate;

    @ApiModelProperty("结束日期")
    @Column(name = "end_date")
    private Date endDate;

    @ApiModelProperty("项目/任务名称")
    @Column(name = "project_name")
    private String projectName;

    @ApiModelProperty("本人角色")
    @Column(name = "role")
    private String role;

    @ApiModelProperty("主要业绩")
    @Column(name = "main_results")
    private String mainResults;

    @ApiModelProperty("职责描述")
    @Column(name = "job_descr")
    private String jobDescr;

    @ApiModelProperty("项目描述")
    @Column(name = "project_descr")
    private String projectDescr;

    @ApiModelProperty("更新时间")
    @Column(name = "update_time")
    private Date updateTime;


}
