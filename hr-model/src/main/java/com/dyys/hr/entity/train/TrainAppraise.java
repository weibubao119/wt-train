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
@ApiModel(value = "培训评估表")
@Table(name = "train_appraise")
public class TrainAppraise extends BaseEntity<Long> {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 项目id
     */
    private Long programsId;
    /**
     * 评估标题
     */
    private String title;
    /**
     * 问卷模板id
     */
    private Long questionnaireId;
    /**
     * 关联课程id
     */
    private Long courseId;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 状态
     0.未开始
     1.正在进行
     2.结束
     */
    private Integer status;
    /**
     * 课程评分结果
     */
    private BigDecimal courseScore;
    /**
     * 讲师评分结果
     */
    private BigDecimal teacherScore;
    /**
     * 组织方(培训项目组织单位)评分结果
     */
    private BigDecimal companyScore;
    /**
     * 创建人
     */
    private String createUser;
    /**
     * 创建时间
     */
    private Long createTime;
    /**
     * 修改人
     */
    private String updateUser;
    /**
     * 修改时间
     */
    private Long updateTime;
}