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
 * 培训材料学习记录表实体
 * @author WSJ
 * @date 2022/05/15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "培训材料学习记录表实体")
@Table(name = "train_materials_learning_record")
public class TrainMaterialsLearningRecord extends BaseEntity<Long> {
    private static final long serialVersionUID = 1L;
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 材料ID
     */
    private Long materialsId;
    /**
     * 材料类型：1课程材料 2培训材料
     */
    private Integer type;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 状态 0.未学 1.已学
     */
    private Integer status;
    /**
     * 材料类别 1.音视频 2.其他
     */
    private Integer materialsType;
    /**
     * 最后学习时长
     */
    private String lastDuration;
    /**
     * 创建人
     */
    private String createUser;
    /**
     * 创建时间
     */
    private Long createTime;
    /**
     * 更新人
     */
    private String updateUser;
    /**
     * 更新时间
     */
    private Long updateTime;
}