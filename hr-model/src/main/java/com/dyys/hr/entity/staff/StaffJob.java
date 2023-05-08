package com.dyys.hr.entity.staff;

import com.dagongma.kernel.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * <p>
 * 职务数据表
 * </p>
 *
 * @author yangye
 * @since 2022-08-23
 */
@Data
@Table(name = "staff_job")
@ApiModel(value = "StaffJob对象", description = "职务数据表")
public class StaffJob extends BaseEntity<String> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("主键ID")
    private String id;

    @ApiModelProperty("员工id")
    @Column(name = "empl_id")
    private String emplId;

    @ApiModelProperty("员工姓名")
    @Column(name = "empl_name")
    private String emplName;

    @ApiModelProperty("公司id")
    @Column(name = "company")
    private String company;

    @ApiModelProperty("公司名称")
    @Column(name = "company_descr")
    private String companyDescr;

    @ApiModelProperty("部门id")
    @Column(name = "dept_id")
    private String deptId;

    @ApiModelProperty("部门名称")
    @Column(name = "dept_descr")
    private String deptDescr;

    @ApiModelProperty("职位编码")
    @Column(name = "post_code")
    private String postCode;

    @ApiModelProperty("职位名称")
    @Column(name = "post_descr")
    private String postDescr;

    @ApiModelProperty("职级编码")
    @Column(name = "grade_code")
    private String gradeCode;

    @ApiModelProperty("职级名称")
    @Column(name = "grade_descr")
    private String gradeDescr;

    @ApiModelProperty("职等编码")
    @Column(name = "level_code")
    private String levelCode;

    @ApiModelProperty("职等名称")
    @Column(name = "level_descr")
    private String levelDescr;

    @ApiModelProperty("序列编码")
    @Column(name = "sec_code")
    private String secCode;

    @ApiModelProperty("序列名称")
    @Column(name = "sec_descr")
    private String secDescr;

    @ApiModelProperty("性别")
    @Column(name = "sex")
    private String sex;

    @ApiModelProperty("年龄")
    @Column(name = "age")
    private String age;

    @ApiModelProperty("生效时间")
    @Column(name = "eff_date")
    private Date effDate;

    @ApiModelProperty("hr状态：A有效，I无效")
    @Column(name = "hr_status")
    private String hrStatus;

    @ApiModelProperty("创建人")
    @Column(name = "creator")
    private String creator;

    @ApiModelProperty("创建时间")
    @Column(name = "create_date")
    private Date createDate;

    @ApiModelProperty("创建人")
    @Column(name = "updater")
    private String updater;

    @ApiModelProperty("创建时间")
    @Column(name = "update_date")
    private Date updateDate;


}
