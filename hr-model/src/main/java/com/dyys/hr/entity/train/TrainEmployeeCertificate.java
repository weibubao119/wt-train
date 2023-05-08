package com.dyys.hr.entity.train;

import com.dagongma.kernel.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 过程管理-培训项目员工证书管理表
 *
 * @author sie sie
 * @since 1.0.0 2022-06-16
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Table(name = "train_employee_certificate")
public class TrainEmployeeCertificate extends BaseEntity<Long> {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 员工id
     */
    private String emplId;
    /**
     * 员工姓名
     */
    private String emplName;
    /**
     * 证书编号
     */
    private String certificateNo;
    /**
     * 证书名称
     */
    private String certificateName;
    /**
     * 证书等级
     */
    private String certificateLevel;
    /**
     * 发证机构名称
     */
    private String issuingAgencyName;
    /**
     * 证书开始时期
     */
    private Date startDate;
    /**
     * 证书截止时期
     */
    private Date endDate;
    /**
     * 培训名称
     */
    private String trainName;
    /**
     * 项目唯一编号
     */
    private String trainNumber;
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