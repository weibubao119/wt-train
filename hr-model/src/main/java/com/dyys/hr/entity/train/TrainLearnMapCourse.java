package com.dyys.hr.entity.train;

import com.dagongma.kernel.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;

/**
 * <p>
 * 学习地图-课程
 * </p>
 *
 * @author yangye
 * @since 2022-09-06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "TrainLearnMapCourse对象", description = "学习地图-课程")
@Table(name = "train_learn_map_course")
public class TrainLearnMapCourse extends BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @ApiModelProperty("主键ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty("地图ID")
    @Column(name = "map_id")
    private Long mapId;

    @ApiModelProperty("地图编码")
    @Column(name = "map_code")
    private String mapCode;

    @ApiModelProperty("职级编码")
    @Column(name = "posn_grade_code")
    private String posnGradeCode;

    @ApiModelProperty("学习方向")
    @Column(name = "sbl_id")
    private Integer sblId;

    @ApiModelProperty("课程模块名称")
    @Column(name = "module_name")
    private String moduleName;

    @ApiModelProperty("课程编号")
    @Column(name = "course_number")
    private String courseNumber;

    @ApiModelProperty("必修标识：1必修；0选修（默认）")
    @Column(name = "required_flag")
    private Integer requiredFlag;

    @ApiModelProperty("数据源类型：0系统添加；1批量导入")
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
