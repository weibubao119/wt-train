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
 * 学习地图-同等课程
 * </p>
 *
 * @author yangye
 * @since 2022-09-06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "TrainLearnMapCourseTanta对象", description = "学习地图-同等课程")
@Table(name = "train_learn_map_course_tanta")
public class TrainLearnMapCourseTanta extends BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @ApiModelProperty("主键ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty("地图课程ID")
    @Column(name = "map_course_id")
    private Long mapCourseId;

    @ApiModelProperty("地图课程编号")
    @Column(name = "map_course_number")
    private String mapCourseNumber;

    @ApiModelProperty("同等课程编号")
    @Column(name = "tanta_course_number")
    private String tantaCourseNumber;

    @ApiModelProperty("创建人")
    @Column(name = "create_user")
    private String createUser;

    @ApiModelProperty("创建时间")
    @Column(name = "create_time")
    private Long createTime;


}
