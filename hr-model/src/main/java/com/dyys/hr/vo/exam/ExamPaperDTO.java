package com.dyys.hr.vo.exam;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
public class ExamPaperDTO {

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
    private String totle;

    @ApiModelProperty("公司编码")
    @Column(name = "companyCode")
    private String companyCode;

    @ApiModelProperty("部门编码")
    @Column(name = "deptId")
    private String deptId;

    @ApiModelProperty("状态(0:未发布, 1:已发布)")
    @Column(name = "status")
    private Integer status;

    @ApiModelProperty("创建人")
    @Column(name = "creator")
    private String creator;

    @ApiModelProperty("创建时间")
    @Column(name = "create_date")
    private Date createDate;
}
