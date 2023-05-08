package com.dyys.hr.entity.train;

import com.dagongma.kernel.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel(value = "培训项目表")
@Table(name = "train_programs")
public class TrainPrograms extends BaseEntity<Long> {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 培训名称
     */
    private String trainName;
    /**
     * 培训项目编号
     */
    private String number;
    /**
     * 大计划id
     */
    private Long planId;
    /**
     * 小计划id
     */
    private Long planDetailId;
    /**
     * 主办单位编码
     */
    private String companyCode;
    /**
     * 部门编码
     */
    private String deptId;
    /**
     * 协办单位列表
     */
    private String coOrganizer;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 受训对象
     */
    private String trainSubject;
    /**
     * 参训人数
     */
    private Integer participantsNum;
    /**
     * 培训形式
     1.内部
     2.外部
     */
    private Integer trainShape;
    /**
     * 经费预算
     */
    private BigDecimal outlay;
    /**
     * 负责人列表
     */
    private String principalList;
    /**
     * 状态
     1.进行中
     2.已完成
     */
    private Integer status;
    /**
     * 是否删除（1，是，0，否）
     */
    private Integer isDelete;
    /**
     * 是否是导入数据
     */
    private Integer isImport;
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