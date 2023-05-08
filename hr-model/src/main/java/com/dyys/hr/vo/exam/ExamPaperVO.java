package com.dyys.hr.vo.exam;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.List;

/**
 * @Author: Daan li
 * @Date: 2022/4/20 10:12
 */
@Data
public class ExamPaperVO implements Serializable {
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("试卷名称")
    private String name;

    @ApiModelProperty("试卷描述")
    private String content;

    @ApiModelProperty("试卷总分")
    private String totle;

    @ApiModelProperty("公司编码")
    @Column(name = "companyCode")
    private String companyCode;

    @ApiModelProperty("部门编码")
    @Column(name = "deptId")
    private String deptId;

    @ApiModelProperty("试题id列表")
    private List<Long> questionList;
}
