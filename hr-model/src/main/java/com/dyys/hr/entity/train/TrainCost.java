package com.dyys.hr.entity.train;

import com.dagongma.kernel.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@ApiModel(value = "培训项目-项目费用表")
@Table(name = "train_cost")
public class TrainCost extends BaseEntity<Long> {
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
     * 培训科目常量id
     */
    private Integer subjectsId;
    /**
     * 费用
     */
    private Float amount;
    /**
     * 附件列表
     */
    private String fileList;
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