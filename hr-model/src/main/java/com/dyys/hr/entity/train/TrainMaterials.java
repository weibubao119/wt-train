package com.dyys.hr.entity.train;

import com.dagongma.kernel.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@ApiModel(value = "培训项目-项目资料表")
@Table(name = "train_programs_materials")
public class TrainMaterials extends BaseEntity<Long> {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 培训项目ID
     */
    private Long programsId;
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
    /**
     * 状态 1.已发布 0.未发布
     */
    private Integer status;
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