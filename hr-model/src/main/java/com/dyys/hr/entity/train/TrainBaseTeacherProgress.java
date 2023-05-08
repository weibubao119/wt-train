package com.dyys.hr.entity.train;
import com.dagongma.kernel.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 资源管理-讲师成长历程表
 *
 * @author sie sie
 * @since 1.0.0 2022-09-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "讲师课程数据表")
@Table(name = "train_base_teacher_progress")
public class TrainBaseTeacherProgress extends BaseEntity<Long> {
	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(value = "主键")
	private Long id;
    /**
     * 讲师id
     */
	private Long teacherId;
    /**
     * 
讲师级别常量id
     */
	private Integer level;
    /**
     * 开始时间
     */
	private Date startTime;
    /**
     * 结束时间
     */
	private Date endTime;
    /**
     * 课酬标准（元/学时）
     */
	private Integer fee;
    /**
     * 说明
     */
	private String instructions;
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