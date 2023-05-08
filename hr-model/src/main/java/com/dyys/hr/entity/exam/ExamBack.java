package com.dyys.hr.entity.exam;

import com.dagongma.kernel.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * <p>
 * 回退重考
 * </p>
 *
 * @author lidaan
 * @since 2022-04-22
 */
@Data
@Table(name = "exam_back")
@ApiModel(value = "ExamBack对象", description = "回退重考")
public class ExamBack extends BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("用户ID")
    @Column(name = "user_id")
    private String userId;

    @ApiModelProperty("考试ID")
    @Column(name = "exam_id")
    private Long examId;

    @ApiModelProperty("回退原因")
    @Column(name ="reason")
    private String reason;

    @ApiModelProperty("创建人")
    @Column(name = "creator")
    private String creator;

    @ApiModelProperty("创建时间")
    @Column(name = "create_time")
    private Date createTime;
}
