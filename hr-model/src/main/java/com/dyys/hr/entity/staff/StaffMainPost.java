package com.dyys.hr.entity.staff;

import com.dagongma.kernel.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * <p>
 * 标准岗位表
 * </p>
 *
 * @author yangye
 * @since 2022-08-23
 */
@Data
@Table(name = "staff_main_post")
@ApiModel(value = "StaffMainPost对象", description = "标准岗位表")
public class StaffMainPost extends BaseEntity<String> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("主键ID")
    private String id;

    @ApiModelProperty("集合id")
    @Column(name = "set_id")
    private String setId;

    @ApiModelProperty("岗位编码")
    @Column(name = "post_code")
    private String postCode;

    @ApiModelProperty("岗位名称")
    @Column(name = "descr")
    private String descr;

    @ApiModelProperty("简称")
    @Column(name = "descr_short")
    private String descrShort;

    @ApiModelProperty("生效日期")
    @Column(name = "eff_date")
    private Date effDate;

    @ApiModelProperty("生效状态")
    @Column(name = "eff_status")
    private String effStatus;

    @ApiModelProperty("业务单位")
    @Column(name = "business_unit")
    private String businessUnit;

    @ApiModelProperty("职序")
    @Column(name = "posn_sec")
    private String posnSec;

    @ApiModelProperty("职类")
    @Column(name = "posn_type")
    private String posnType;

    @ApiModelProperty("职族")
    @Column(name = "profession")
    private String profession;

    @ApiModelProperty("业务类型")
    @Column(name = "bus_type")
    private String busType;

    @ApiModelProperty("创建人")
    @Column(name = "creator")
    private String creator;

    @ApiModelProperty("修改人")
    @Column(name = "updater")
    private String updater;

    @ApiModelProperty("创建时间")
    @Column(name = "create_date")
    private Date createDate;

    @ApiModelProperty("修改时间")
    @Column(name = "update_date")
    private Date updateDate;


}
