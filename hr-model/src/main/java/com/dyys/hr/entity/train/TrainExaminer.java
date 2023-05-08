package com.dyys.hr.entity.train;

import com.dagongma.kernel.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@ApiModel(value = "培训考试人员表")
@Table(name = "train_examiner")
public class TrainExaminer extends BaseEntity<Long> {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 考试ID
     */
    private Long examId;
    /**
     * 考试人ID
     */
    private String userId;
    /**
     * 状态 0.未通过 1.已通过 2.未答卷
     */
    private Integer status;
    /**
     * 考试最高分
     */
    private String highestScore;
    /**
     * 考试次数
     */
    private Integer examNum;
    /**
     * 最后考试时间
     */
    private Date finalTime;
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