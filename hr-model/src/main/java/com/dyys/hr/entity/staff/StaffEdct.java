package com.dyys.hr.entity.staff;

import com.dagongma.kernel.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * <p>
 * 教育信息表
 * </p>
 *
 * @author yangye
 * @since 2022-08-23
 */
@Data
@Table(name = "staff_edct")
@ApiModel(value = "StaffEdct对象", description = "教育信息表")
public class StaffEdct extends BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("工号")
    @Column(name = "empl_id")
    private String emplId;

    @ApiModelProperty("教育方式")
    @Column(name = "edct_lrng_style")
    private String edctLrngStyle;

    @ApiModelProperty("入学日期")
    @Column(name = "start_date")
    private Date startDate;

    @ApiModelProperty("毕业日期")
    @Column(name = "end_date")
    private Date endDate;

    @ApiModelProperty("毕业学校")
    @Column(name = "edct_schl")
    private String edctSchl;

    @ApiModelProperty("学历")
    @Column(name = "edct_bakg")
    private String edctBakg;

    @ApiModelProperty("学位")
    @Column(name = "edct_dege")
    private String edctDege;

    @ApiModelProperty("专业")
    @Column(name = "edct_prfn")
    private String edctPrfn;

    @ApiModelProperty("院校类型")
    @Column(name = "schl_type")
    private String schlType;

    @ApiModelProperty("更新时间")
    @Column(name = "update_time")
    private Date updateTime;


}
