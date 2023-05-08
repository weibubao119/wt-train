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
@ApiModel(value = "培训项目-学员总结表")
@Table(name = "train_trainee_summary")
public class TrainTraineeSummary extends BaseEntity<Long> {
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
     * 用户id
     */
    private String userId;
    /**
     * 总成绩
     */
    private String totalScore;
    /**
     * 是否获证 0.否 1.是
     */
    private Integer isObtain;
    /**
     * 来源标识：10项目导入，20培训历史数据同步，30ELN数据同步
     */
    private Integer sourceType;
    /**
     * 培训成果
     */
    private String trainingResults;
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