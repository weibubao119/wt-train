package com.dyys.hr.entity.train;

import com.dagongma.kernel.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * <p>
 * 学员地图
 * </p>
 *
 * @author yangye
 * @since 2022-09-06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "TrainLearnMapStaff对象", description = "学员地图")
@Table(name = "train_learn_map_staff")
public class TrainLearnMapStaff extends BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @ApiModelProperty("主键ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty("员工ID")
    @Column(name = "empl_id")
    private String emplId;

    @ApiModelProperty("学习地图ID")
    @Column(name = "map_id")
    private Long mapId;

    @ApiModelProperty("学习地图编码")
    @Column(name = "map_code")
    private String mapCode;

    @ApiModelProperty("学习地图职序编码")
    @Column(name = "posn_sec_code")
    private String posnSecCode;

    @ApiModelProperty("学习地图职级编码")
    @Column(name = "posn_grade_code")
    private String posnGradeCode;

    @ApiModelProperty("数据来源：0添加，1导入")
    @Column(name = "source_type")
    private Integer sourceType;

    @ApiModelProperty("创建人")
    @Column(name = "create_user")
    private String createUser;

    @ApiModelProperty("创建时间")
    @Column(name = "create_time")
    private Long createTime;

    @ApiModelProperty("更新人")
    @Column(name = "update_user")
    private String updateUser;

    @ApiModelProperty("更新时间")
    @Column(name = "update_time")
    private Long updateTime;


}
