package com.dyys.hr.entity.exam;

import com.dagongma.kernel.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * <p>
 * 试卷信息
 * </p>
 *
 * @author lidaan
 * @since 2022-04-21
 */
@Data
@Table(name = "exam_paper")
@ApiModel(value = "ExamPaper对象", description = "试卷信息")
public class ExamPaper extends BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("试卷名称")
    @Column(name = "name")
    private String name;

    @ApiModelProperty("试卷描述")
    @Column(name = "content")
    private String content;

    @ApiModelProperty("试卷总分")
    @Column(name = "totle")
    private Float totle;

    @ApiModelProperty("公司编码")
    @Column(name = "company_code")
    private String companyCode;

    @ApiModelProperty("部门编码")
    @Column(name = "dept_id")
    private String deptId;

    @ApiModelProperty("状态(0:暂存,1:发布)")
    @Column(name = "status")
    private Integer status;

    @ApiModelProperty("是否删除(0:否, 1:是)")
    @Column(name = "is_deleted")
    private Integer isDeleted;

    @ApiModelProperty("创建人")
    @Column(name = "creator")
    private String creator;

    @ApiModelProperty("创建时间")
    @Column(name = "create_date")
    private Date createDate;

    @ApiModelProperty("更新人")
    @Column(name = "updater")
    private String updater;

    @ApiModelProperty("更新时间")
    @Column(name = "update_date")
    private Date updateDate;
}
