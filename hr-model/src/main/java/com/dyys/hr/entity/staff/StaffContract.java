package com.dyys.hr.entity.staff;

import com.dagongma.kernel.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * <p>
 * 劳动合同信息表
 * </p>
 *
 * @author yangye
 * @since 2022-08-23
 */
@Data
@Table(name = "staff_contract")
@ApiModel(value = "StaffContract对象", description = "劳动合同信息表")
public class StaffContract extends BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("工号")
    @Column(name = "empl_id")
    private String emplId;

    @ApiModelProperty("起始日期")
    @Column(name = "contract_begin_date")
    private Date contractBeginDate;

    @ApiModelProperty("结束日期")
    @Column(name = "contract_end_date")
    private Date contractEndDate;

    @ApiModelProperty("签约类型")
    @Column(name = "contract_type")
    private String contractType;

    @ApiModelProperty("签约单位")
    @Column(name = "company")
    private String company;

    @ApiModelProperty("终解日期")
    @Column(name = "end_date")
    private Date endDate;

    @ApiModelProperty("更新时间")
    @Column(name = "update_time")
    private Date updateTime;


}
