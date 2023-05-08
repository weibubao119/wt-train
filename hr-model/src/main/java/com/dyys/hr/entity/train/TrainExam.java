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
@ApiModel(value = "培训考试表")
@Table(name = "train_exam")
public class TrainExam extends BaseEntity<Long> {
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
     * 考试标题
     */
    private String title;
    /**
     * 试卷模板id
     */
    private Integer paperId;
    /**
     * 考试类型(1:线上考试，2:线下考试)
     */
    private Integer type;
    /**
     * 关联课程id
     */
    private Long courseId;
    /**
     * 考试总时长
     */
    private String duration;
    /**
     * 考试及格分
     */
    private String passScore;
    /**
     * 考试总分
     */
    private String totalScore;
    /**
     * 考试人数
     */
    private Integer userCount;
    /**
     * 是否限时(0: 不限时，1: 限时)
     */
    private Integer isLimit;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 考试次数
     */
    private Integer examCount;
    /**
     * 状态
     0.未开始
     1.正在进行
     2.结束
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
     * 修改人
     */
    private String updateUser;
    /**
     * 修改时间
     */
    private Long updateTime;
}