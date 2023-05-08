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
 * 学习地图
 * </p>
 *
 * @author yangye
 * @since 2022-09-06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "TrainLearnMap对象", description = "学习地图")
@Table(name = "train_learn_map")
public class TrainLearnMap extends BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @ApiModelProperty("主键ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty("地图编号")
    @Column(name = "map_code")
    private String mapCode;

    @ApiModelProperty("地图名称")
    @Column(name = "map_name")
    private String mapName;

    @ApiModelProperty("职序编码")
    @Column(name = "posn_sec_code")
    private String posnSecCode;

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
