package com.dyys.hr.entity.train;

import com.dagongma.kernel.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;

/**
 * <p>
 * 学员地图-学习方向
 * </p>
 *
 * @author yangye
 * @since 2022-09-06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "TrainLearnMapStaffSbl对象", description = "学员地图-学习方向")
@Table(name = "train_learn_map_staff_sbl")
public class TrainLearnMapStaffSbl extends BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @ApiModelProperty("主键ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty("学员地图ID")
    @Column(name = "map_staff_id")
    private Long mapStaffId;

    @ApiModelProperty("学员工号")
    @Column(name = "empl_id")
    private String emplId;

    @ApiModelProperty("学习方向ID")
    @Column(name = "sbl_id")
    private Integer sblId;

    @ApiModelProperty("创建人")
    @Column(name = "create_user")
    private String createUser;

    @ApiModelProperty("创建时间")
    @Column(name = "create_time")
    private Long createTime;


}
