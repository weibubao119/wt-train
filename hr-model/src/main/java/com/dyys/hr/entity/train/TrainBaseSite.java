package com.dyys.hr.entity.train;

import com.dagongma.kernel.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 
 * 场地实体
 * @author JUCHUAN LI
 * @date 2019/06/26
 */ 
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "场地")
@Table(name = "train_base_site")
public class TrainBaseSite extends BaseEntity<Integer> {
    /**
     * 主键
     */
    @Id
    private Integer id;
    /**
     * 场地名称
     */
    private String siteName;
    /**
     * 所属组织编码
     */
    private String deptId;
    /**
     * 所属公司编码
     */
    private String companyCode;
    /**
     * 负责人
     */
    private String principal;
    /**
     * 联系电话
     */
    private String contactPhone;
    /**
     * 可容纳人数
     */
    private Integer maxCapacity;
    /**
     * 场地地址
     */
    private String address;
    /**
     * 导入标识：1导入，0添加
     */
    private Integer isImport;
    /**
     * 是否删除
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