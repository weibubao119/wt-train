package com.dyys.hr.entity.staff;

import com.dagongma.kernel.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * <p>
 * 公司表
 * </p>
 *
 * @author yangye
 * @since 2022-08-23
 */
@Data
@Table(name = "staff_company")
@ApiModel(value = "StaffCompany对象", description = "公司表")
public class StaffCompany extends BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("父ID")
    @Column(name = "parent_id")
    private String parentId;

    @ApiModelProperty("描述")
    @Column(name = "descr")
    private String descr;

    @ApiModelProperty("简称")
    @Column(name = "descr_short")
    private String descrShort;

    @ApiModelProperty("生效时间")
    @Column(name = "eff_date")
    private Date effDate;

    @ApiModelProperty("生效状态")
    @Column(name = "eff_status")
    private String effStatus;

    @ApiModelProperty("公司注册地址")
    @Column(name = "regist_address")
    private String registAddress;

    @ApiModelProperty("公司办公地址")
    @Column(name = "office_address")
    private String officeAddress;

    @ApiModelProperty("创建人")
    @Column(name = "creator")
    private String creator;

    @ApiModelProperty("创建时间")
    @Column(name = "create_date")
    private Date createDate;

    @ApiModelProperty("修改人")
    @Column(name = "updater")
    private String updater;

    @ApiModelProperty("修改时间")
    @Column(name = "update_date")
    private Date updateDate;


}
