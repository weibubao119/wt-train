package com.dyys.hr.entity.staff;

import com.dagongma.kernel.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * <p>
 * 职族管理表
 * </p>
 *
 * @author yangye
 * @since 2022-08-23
 */
@Data
@Table(name = "staff_profession")
@ApiModel(value = "StaffProfession对象", description = "职族管理表")
public class StaffProfession extends BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("集合ID")
    @Column(name = "set_id")
    private String setId;

    @ApiModelProperty("职族编码")
    @Column(name = "code")
    private String code;

    @ApiModelProperty("职族名称")
    @Column(name = "descr")
    private String descr;

    @ApiModelProperty("简称")
    @Column(name = "descr_short")
    private String descrShort;

    @ApiModelProperty("业务单位")
    @Column(name = "business_unit")
    private String businessUnit;

    @ApiModelProperty("生效时间")
    @Column(name = "eff_date")
    private Date effDate;

    @ApiModelProperty("生效状态")
    @Column(name = "eff_status")
    private String effStatus;

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
