package com.dyys.hr.entity.train;

import com.dagongma.kernel.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "课程信息表")
@Table(name = "train_base_course")
public class TrainBaseCourse extends BaseEntity<Long> {
    private static final long serialVersionUID = 1L;

    /**
     * 课程名称
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 课程名称
     */
    private String name;
    /**
     * 课程编号
     */
    private String number;
    /**
     * 课程类别常量id
     */
    private Integer category;
    /**
     * 课时数
     */
    private BigDecimal classHours;
    /**
     * 课程简介
     */
    private String instructions;
    /**
     * 课程学分
     */
    private BigDecimal credit;
    /**
     * 附件列表
     */
    private String fileList;
    /**
     * 课程来源 1.外部 2.自有
     */
    private Integer courseSource;
    /**
     * 状态 1.生效 0.失效
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
    /**
     * 是否删除
     */
    private Integer isDelete;
    /**
     * 是否是导入数据
     */
    private Integer isImport;
}