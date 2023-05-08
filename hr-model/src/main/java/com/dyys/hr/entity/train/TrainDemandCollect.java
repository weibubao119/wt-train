package com.dyys.hr.entity.train;

import com.dagongma.kernel.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "需求信息表")
@Table(name = "train_demand_collect")
public class TrainDemandCollect extends BaseEntity<Long> {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 标题
     */
    private String demandName;
    /**
     * 公司code
     */
    private String companyCode;
    /**
     * 部门code
     */
    private String departmentCode;
    /**
     * 发起人ID
     */
    private String initiator;
    /**
     * 发起时间
     */
    private Date initiationTime;
    /**
     * 需求说明
     */
    private String demandDescription;
    /**
     * 附件列表
     */
    private String fileList;
    /**
     * 反馈开始时间
     */
    private Date feedbackStartTime;
    /**
     * 反馈结束时间
     */
    private Date feedbackEndTime;
    /**
     * 状态
     0.进行中
     1.已完成
     */
    private Integer status;
    /**
     * 是否删除（1，是，0，否）
     */
    private Integer isDelete;
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