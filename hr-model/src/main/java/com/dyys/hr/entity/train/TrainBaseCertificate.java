package com.dyys.hr.entity.train;

import com.dagongma.kernel.annotation.EnumValue;
import com.dagongma.kernel.core.entity.BaseEntity;
import com.dyys.hr.enums.YesOrNoEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Date;

/**
 * 
 * 证书表实体
 * @author JUCHUAN LI
 * @date 2019/06/26
 */ 
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "证书表")
@Table(name = "train_base_certificate")
public class TrainBaseCertificate extends BaseEntity<Integer> {
    /**
     * 主键
     */
    @Id
    private Integer id;

    /**
     * 证书名称
     */
    private String certificateName;
    /**
     * 证书编号唯一
     */
    private String certificateNo;
    /**
     * 颁发机构
     */
    private String issuedInstitutions;
    /**
     * 证书期限
     */
    private Date certificatePeriod;
    /**
     * 证书状态 1正常 0失效
     */
    private Integer certificateStatus;
    /**
     * 文件列表
     */
    private String fileList;
    /**
     * 更新人
     */
    private String updateUser;
    /**
     * 更新时间
     */
    private Long updateTime;
    /**
     * 创建人
     */
    private String createUser;
    /**
     * 创建时间
     */
    private Long createTime;
    /**
     * 是否删除
     */
    private Integer isDelete;
}