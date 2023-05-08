package com.dyys.hr.entity.staff;

import com.dagongma.kernel.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * <p>
 * 资格等级信息 - 职称信息表
 * </p>
 *
 * @author yangye
 * @since 2022-08-23
 */
@Data
@Table(name = "staff_qua_title")
@ApiModel(value = "StaffQuaTitle对象", description = "资格等级信息 - 职称信息表")
public class StaffQuaTitle extends BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("工号")
    @Column(name = "empl_id")
    private String emplId;

    @ApiModelProperty("资格等级")
    @Column(name = "grade")
    private String grade;

    @ApiModelProperty("职称名称")
    @Column(name = "name")
    private String name;

    @ApiModelProperty("专业")
    @Column(name = "professional")
    private String professional;

    @ApiModelProperty("取得时间")
    @Column(name = "start_date")
    private Date startDate;

    @ApiModelProperty("发证机关")
    @Column(name = "ctfct_dept")
    private String ctfctDept;

    @ApiModelProperty("更新时间")
    @Column(name = "update_time")
    private Date updateTime;


}
