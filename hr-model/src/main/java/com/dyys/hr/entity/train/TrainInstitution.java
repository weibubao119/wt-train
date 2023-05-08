package com.dyys.hr.entity.train;

import com.dagongma.kernel.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;

/**
 * <p>
 * 培训机构
 * </p>
 *
 * @author yangye
 * @since 2022-05-11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "培训机构")
@Table(name = "train_institution")
public class TrainInstitution extends BaseEntity<Long> {

    @Id
    @ApiModelProperty("主键ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @ApiModelProperty("机构名称")
    private String name;

    @Column(name = "cate_id")
    @ApiModelProperty("机构类型ID")
    private Integer cateId;

    @Column(name = "principal_name")
    @ApiModelProperty("负责人姓名")
    private String principalName;

    @Column(name = "principal_phone")
    @ApiModelProperty("负责人电话")
    private String principalPhone;

    @Column(name = "address")
    @ApiModelProperty("机构地址")
    private String address;

    @Column(name = "rating_year")
    @ApiModelProperty("评级年度")
    private String ratingYear;

    @Column(name = "grade_id")
    @ApiModelProperty("等级ID")
    private Integer gradeId;

    @Column(name = "is_import")
    @ApiModelProperty("导入标识：1导入，0添加")
    private Integer isImport;

    @Column(name = "create_user")
    @ApiModelProperty("创建人ID")
    private String createUser;

    @Column(name = "create_time")
    @ApiModelProperty("创建时间")
    private Long createTime;

    @Column(name = "update_user")
    @ApiModelProperty("更新人ID")
    private String updateUser;

    @Column(name = "update_time")
    @ApiModelProperty("更新时间")
    private Long updateTime;
}
