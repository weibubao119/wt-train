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
 * 学习地图-职级
 * </p>
 *
 * @author yangye
 * @since 2022-09-06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "TrainLearnMapPosnGrade对象", description = "学习地图-职级")
@Table(name = "train_learn_map_posn_grade")
public class TrainLearnMapPosnGrade extends BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @ApiModelProperty("主键ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty("地图主键ID")
    @Column(name = "map_id")
    private Long mapId;

    @ApiModelProperty("地图编码")
    @Column(name = "map_code")
    private String mapCode;

    @ApiModelProperty("职级编码")
    @Column(name = "posn_grade_code")
    private String posnGradeCode;

    @ApiModelProperty("高一级职级编码")
    @Column(name = "p_posn_grade_code")
    private String pPosnGradeCode;

    @ApiModelProperty("创建人ID")
    @Column(name = "create_user")
    private String createUser;

    @ApiModelProperty("创建时间戳")
    @Column(name = "create_time")
    private Long createTime;

    @ApiModelProperty("更新人ID")
    @Column(name = "update_user")
    private String updateUser;

    @ApiModelProperty("更新时间戳")
    @Column(name = "update_time")
    private Long updateTime;


}
