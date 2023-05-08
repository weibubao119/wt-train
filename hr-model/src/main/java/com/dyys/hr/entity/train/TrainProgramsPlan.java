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
@ApiModel(value = "培训项目-计划表")
@Table(name = "train_programs_plan")
public class TrainProgramsPlan extends BaseEntity<Long> {
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
     * 课程id
     */
    private Long courseId;
    /**
     * 课程开始时间
     */
    private Date startTime;
    /**
     * 课程结束时间
     */
    private Date endTime;
    /**
     * 上课时间
     */
    private String schooltime;
    /**
     * 场地
     */
    private String site;
    /**
     * 机构id
     */
    private Long institutionId;
    /**
     * 学时
     */
    private BigDecimal classHour;
    /**
     * 学习形式
     */
    private Integer learningForm;
    /**
     * 考核形式
     */
    private Integer examineForm;
    /**
     * 费用
     */
    private BigDecimal outlay;
    /**
     * 费用说明
     */
    private String outlayText;
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