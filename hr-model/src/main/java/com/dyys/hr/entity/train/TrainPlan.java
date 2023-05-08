package com.dyys.hr.entity.train;

import com.dagongma.kernel.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Year;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "计划信息表")
@Table(name = "train_plan")
public class TrainPlan extends BaseEntity<Long> {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 标题
     */
    private String title;
    /**
     * 公司code
     */
    private String companyCode;
    /**
     * 部门编码
     */
    private String deptId;
    /**
     * 发起人ID
     */
    private String initiator;
    /**
     * 计划年度
     */
    private String planYear;
    /**
     * 附件列表
     */
    private String fileList;
    /**
     * 状态
     0.待审批
     1.已通过
     2.未通过
     */
    private Integer status;
    /**
     * 是否删除（1，是，0，否）
     */
    private Integer isDelete;
    /**
     * 审批流ID
     */
    private Integer approveFlowId;
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