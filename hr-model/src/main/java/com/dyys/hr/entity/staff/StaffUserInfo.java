package com.dyys.hr.entity.staff;

import com.dagongma.kernel.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;


/**
 * <p>
 * 员工基本信息表
 * </p>
 *
 * @author yangye
 * @since 2022-08-31
 */
@Data
@Table(name = "staff_user_info")
@ApiModel(value = "StaffUserInfo对象", description = "员工基本信息表")
public class StaffUserInfo extends BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("工号")
    @Column(name = "empl_id")
    private String emplId;

    @ApiModelProperty("姓名")
    @Column(name = "name")
    private String name;

    @ApiModelProperty("手机号码")
    @Column(name = "phone_number")
    private String phoneNumber;

    @ApiModelProperty("邮箱")
    @Column(name = "email")
    private String email;

    @ApiModelProperty("性别")
    @Column(name = "sex")
    private String sex;

    @ApiModelProperty("婚姻状况")
    @Column(name = "mar_status")
    private String marStatus;

    @ApiModelProperty("出生年月")
    @Column(name = "birth_date")
    private Date birthDate;

    @ApiModelProperty("籍贯")
    @Column(name = "native_place")
    private String nativePlace;

    @ApiModelProperty("民族")
    @Column(name = "nation")
    private String nation;

    @ApiModelProperty("最高学历")
    @Column(name = "edct_bakg")
    private String edctBakg;

    @ApiModelProperty("最高职称")
    @Column(name = "title_nm")
    private String titleNm;

    @ApiModelProperty("政治面貌")
    @Column(name = "political_sta_chn")
    private String politicalStaChn;

    @ApiModelProperty("生育状况")
    @Column(name = "birth_status")
    private String birthStatus;

    @ApiModelProperty("更新时间")
    @Column(name = "update_time")
    private Date updateTime;


}
