package com.dyys.hr.entity.train;

import com.dagongma.kernel.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * 课程关联材料表实体
 * @author WSJ
 * @date 2022/05/15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "课程关联材料表")
@Table(name = "train_base_course_materials")
public class TrainBaseCourseMaterials extends BaseEntity<Long> {
    private static final long serialVersionUID = 1L;
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 课程ID
     */
    private Long courseId;
    /**
     * 材料分类
     */
    private String category;
    /**
     * 材料名称
     */
    private String filename;
    /**
     * 材料地址
     */
    private String src;
    /**
     * 类别
     * 1.音视频
     * 2.其他
     */
    private Integer type;
    /**
     * 时长
     */
    private String duration;
}