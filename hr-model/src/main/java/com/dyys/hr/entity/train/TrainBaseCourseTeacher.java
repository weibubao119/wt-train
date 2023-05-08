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
 * 课程关联授课讲师表实体
 * @author WSJ
 * @date 2022/04/26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "课程关联授课讲师表")
@Table(name = "train_base_course_teacher")
public class TrainBaseCourseTeacher extends BaseEntity<Long> {
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
     * 讲师ID
     */
    private Long teacherId;
    /**
     * 讲师编号
     */
    private String number;
    /**
     * 姓名
     */
    private String name;
    /**
     * 类别
     * 1.内部讲师
     * 2.外部讲师
     */
    private Integer type;
    /**
     * 机构名称
     */
    private String organizationName;
}