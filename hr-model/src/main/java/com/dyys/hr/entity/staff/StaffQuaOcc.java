package com.dyys.hr.entity.staff;

import com.dagongma.kernel.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * <p>
 * 资格等级信息 - 职称资格表
 * </p>
 *
 * @author yangye
 * @since 2022-08-23
 */
@Data
@Table(name = "staff_qua_occ")
@ApiModel(value = "StaffQuaOcc对象", description = "资格等级信息 - 职称资格表")
public class StaffQuaOcc extends BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("工号")
    @Column(name = "empl_id")
    private String emplId;

    @ApiModelProperty("证书类型")
    @Column(name = "ctfct_type")
    private String ctfctType;

    @ApiModelProperty("证书名称")
    @Column(name = "name")
    private String name;

    @ApiModelProperty("证书等级")
    @Column(name = "level")
    private String level;

    @ApiModelProperty("工种")
    @Column(name = "vqc_type")
    private String vqcType;

    @ApiModelProperty("获证日期")
    @Column(name = "start_date")
    private Date startDate;

    @ApiModelProperty("发证机关")
    @Column(name = "ctfct_dept")
    private String ctfctDept;

    @ApiModelProperty("更新时间")
    @Column(name = "update_time")
    private Date updateTime;


}
