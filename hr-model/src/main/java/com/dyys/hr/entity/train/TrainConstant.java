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

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "培训基础档案表")
@Table(name = "train_constant")
public class TrainConstant extends BaseEntity<Integer> {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 上级ID(例如职序ID)
     */
    private String pid;

    /**
     * 常量名称
     */
    private String name;
    /**
     * 常量类型
     1.课程类别
     2.培训需求依据
     3.培训考核方法
     4.讲师等级
     5.机构类型
     6.计划类型
     7.职序与学习方向
     8.培训科目
     9.机构等级
     */
    private Integer type;
    /**
     * 排序值：越小越靠前
     */
    private Integer orderBy;
    /**
     * 状态 1.启用 0.停用
     */
    private Integer status;
    /**
     * 删除标识 1.已删除 0.未删除
     */
    private Integer isDeleted;
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