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
@ApiModel(value = "培训考试人员参考记录表")
@Table(name = "train_examiner_detail")
public class TrainExaminerDetail extends BaseEntity<Long> {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 考试人员记录ID
     */
    private Long examinerId;
    /**
     * 考试时间
     */
    private Date examTime;
    /**
     * 考试用时
     */
    private String useTime;
    /**
     * 考试得分
     */
    private String score;
    /**
     * 是否合格 0.不合格 1.合格
     */
    private Integer isPass;
    /**
     * 状态 0.未完成 1.已完成
     */
    private Integer status;
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