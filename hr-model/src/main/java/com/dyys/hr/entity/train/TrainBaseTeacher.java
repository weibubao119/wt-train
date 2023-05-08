package com.dyys.hr.entity.train;

import com.dagongma.kernel.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "讲师信息表")
@Table(name = "train_base_teacher")
public class TrainBaseTeacher extends BaseEntity<Long> {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键")
    private Long id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 工号/编码
     */
    private String number;
    /**
     * 类别
     1.内部讲师
     2.外部讲师
     */
    private Integer type;
    /**
     * 性别
     F.女
     M.男
     */
    private String sex;
    /**
     * 讲师年龄
     */
    private Integer age;
    /**
     * 联系电话
     */
    private String contactPhone;
    /**
     * 邮箱地址
     */
    private String email;
    /**
     * 讲师等级常量id

     */
    private Integer level;
    /**
     * 文化程度
     */
    private String educationLevel;
    /**
     * 状态
     0.失效
     1.正常
     */
    private Integer status;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 机构名称
     */
    private String organizationName;
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