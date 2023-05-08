package com.dyys.hr.entity.staff;

import com.dagongma.kernel.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * <p>
 * 部门表
 * </p>
 *
 * @author yangye
 * @since 2022-08-23
 */
@Data
@Table(name = "staff_department")
@ApiModel(value = "StaffDepartment对象", description = "部门表")
public class StaffDepartment extends BaseEntity<String> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("主键ID")
    private String id;

    @ApiModelProperty("集合ID")
    @Column(name = "set_id")
    private String setId;

    @ApiModelProperty("生效时间")
    @Column(name = "eff_date")
    private Date effDate;

    @ApiModelProperty("生效状态")
    @Column(name = "eff_status")
    private String effStatus;

    @ApiModelProperty("描述(全名)")
    @Column(name = "descr")
    private String descr;

    @ApiModelProperty("简短描述(简称)")
    @Column(name = "descr_short")
    private String descrShort;

    @ApiModelProperty("所属公司ID")
    @Column(name = "company")
    private String company;

    @ApiModelProperty("单位")
    @Column(name = "business_unit")
    private String businessUnit;

    @ApiModelProperty("地点")
    @Column(name = "location")
    private String location;

    @ApiModelProperty("管理者ID")
    @Column(name = "manager_id")
    private String managerId;

    @ApiModelProperty("管理者姓名")
    @Column(name = "manager_name")
    private String managerName;

    @ApiModelProperty("管理者职位")
    @Column(name = "manager_posn")
    private String managerPosn;

    @ApiModelProperty("部门编码")
    @Column(name = "dept_code")
    private String deptCode;

    @ApiModelProperty("部门层级")
    @Column(name = "dept_level")
    private String deptLevel;

    @ApiModelProperty("父级部门ID")
    @Column(name = "parent_deptid")
    private String parentDeptid;

    @ApiModelProperty("部门ID路径(','号分割)")
    @Column(name = "dept_id_path")
    private String deptIdPath;

    @ApiModelProperty("部门名称路径('-'号分割)")
    @Column(name = "dept_name_path")
    private String deptNamePath;

    @ApiModelProperty("创建者")
    @Column(name = "creator")
    private String creator;

    @ApiModelProperty("创建时间")
    @Column(name = "create_date")
    private Date createDate;

    @ApiModelProperty("修改者")
    @Column(name = "updater")
    private String updater;

    @ApiModelProperty("修改时间")
    @Column(name = "update_date")
    private Date updateDate;


}
