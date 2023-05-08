package com.dyys.hr.entity.train;

import com.dagongma.kernel.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 过程管理-培训项目考勤规则表
 *
 * @author sie sie
 * @since 1.0.0 2022-06-06
 */
@Data
@ApiModel(value = "培训项目考勤规则表")
@Table(name = "train_attendance_rules")
public class TrainAttendanceRules extends BaseEntity<Long> {
	private static final long serialVersionUID = 1L;
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
     * 项目课表id
     */
	private Long programsCourseId;
	/**
	 * 课程id
	 */
	private Long courseId;
    /**
     * 日期
     */
	private Date date;
    /**
     * 开始时间
     */
	private String startTime;
    /**
     * 结束时间
     */
	private String endTime;
    /**
     * 出勤人数
     */
	private Integer attendanceNum;
    /**
     * 缺勤人数
     */
	private Integer absentNum;
    /**
     * 迟到人数
     */
	private Integer lateNum;
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