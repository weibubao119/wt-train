package com.dyys.hr.entity.staff;

import com.dagongma.kernel.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * <p>
 * 在职信息表
 * </p>
 *
 * @author yangye
 * @since 2022-08-23
 */
@Data
@Table(name = "staff_on_job")
@ApiModel(value = "StaffOnJob对象", description = "在职信息表")
public class StaffOnJob extends BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("工号")
    @Column(name = "empl_id")
    private String emplId;

    @ApiModelProperty("任职公司")
    @Column(name = "company")
    private String company;

    @ApiModelProperty("任职部门")
    @Column(name = "dept_id")
    private String deptId;

    @ApiModelProperty("任职岗位")
    @Column(name = "position")
    private String position;

    @ApiModelProperty("入职日期")
    @Column(name = "entry_date")
    private Date entryDate;

    @ApiModelProperty("在职状态")
    @Column(name = "job_status")
    private String jobStatus;

    @ApiModelProperty("员工类别")
    @Column(name = "staff_class")
    private String staffClass;

    @ApiModelProperty("招聘方式")
    @Column(name = "recruit_way")
    private String recruitWay;

    @ApiModelProperty("招聘渠道")
    @Column(name = "recruit_chan")
    private String recruitChan;

    @ApiModelProperty("推荐人")
    @Column(name = "recommend")
    private String recommend;

    @ApiModelProperty("用工性质")
    @Column(name = "belong_to")
    private String belongTo;

    @ApiModelProperty("工作厂区")
    @Column(name = "location")
    private String location;

    @ApiModelProperty("离职日期")
    @Column(name = "quit_job_date")
    private Date quitJobDate;

    @ApiModelProperty("签约单位")
    @Column(name = "contract")
    private String contract;

    @ApiModelProperty("签约起始日期")
    @Column(name = "contract_begin_date")
    private Date contractBeginDate;

    @ApiModelProperty("签约结束日期")
    @Column(name = "contract_end_date")
    private Date contractEndDate;

    @ApiModelProperty("更新时间")
    @Column(name = "update_time")
    private Date updateTime;


}
