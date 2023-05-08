package com.dyys.hr.vo.exam;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Desc
 * Create by yangye
 * Date 2023/3/18 9:19
 */
@Data
public class ExamPaperPageVO {
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("试卷名称")
    private String name;

    @ApiModelProperty("试卷描述")
    private String content;

    @ApiModelProperty("试卷总分")
    private Float totle;

    @ApiModelProperty("公司编码")
    private String companyCode;

    @ApiModelProperty("组织(部门)编码")
    private String deptId;

    @ApiModelProperty("组织(部门)名称")
    private String deptPathName;

    @ApiModelProperty("状态(0:暂存,1:发布)")
    private Integer status;

    @ApiModelProperty("是否删除(0:否, 1:是)")
    private Integer isDeleted;

    @ApiModelProperty("创建人")
    private String creator;

    @ApiModelProperty("创建时间")
    private Date createDate;

    @ApiModelProperty("更新人")
    private String updater;

    @ApiModelProperty("更新时间")
    private Date updateDate;
}
