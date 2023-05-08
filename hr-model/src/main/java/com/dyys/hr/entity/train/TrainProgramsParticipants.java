package com.dyys.hr.entity.train;

import com.dagongma.kernel.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@ApiModel(value = "培训项目-参训人员表")
@Table(name = "train_programs_participants")
public class TrainProgramsParticipants extends BaseEntity<Long> {
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
     * 参训人员ID
     */
    private String userId;
    /**
     * 公司编码
     */
    private String companyCode;
    /**
     * 部门编码
     */
    private String departmentCode;
    /**
     * 岗位编码
     */
    private String jobCode;
    /**
     * 状态
     0.待确认
     1.已报名
     2.待通知
     */
    private Integer status;
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