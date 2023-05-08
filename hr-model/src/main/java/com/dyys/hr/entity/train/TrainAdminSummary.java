package com.dyys.hr.entity.train;

import com.dagongma.kernel.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@ApiModel(value = "培训项目-管理员总结表")
@Table(name = "train_admin_summary")
public class TrainAdminSummary extends BaseEntity<Long> {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 培训项目id
     */
    private Long programsId;
    /**
     * 目标参培率
     */
    private String targetParticipationRate;
    /**
     * 实际参培率
     */
    private String actualParticipationRate;
    /**
     * 目标成绩
     */
    private String targetResults;
    /**
     * 实际成绩
     */
    private String actualResults;
    /**
     * 培训总结
     */
    private String summary;
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