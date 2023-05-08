package com.dyys.hr.vo.train;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "学员档案数据分页VO")
public class StudentFilesPageVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "工号")
    private String emplId;

    @ApiModelProperty(value = "员工姓名")
    private String emplName;

    @ApiModelProperty(value = "公司id")
    private String company;

    @ApiModelProperty(value = "公司名称")
    private String companyDescr;

    @ApiModelProperty(value = "部门id")
    private String deptId;

    @ApiModelProperty(value = "部门名称")
    private String deptDescr;

    @ApiModelProperty(value = "入职时间")
    private Date effDate;

    @ApiModelProperty(value = "职位层级编码")
    private String gradeCode;

    @ApiModelProperty(value = "职位层级名称")
    private String gradeDescr;

    @ApiModelProperty(value = "职位等级编码")
    private String levelCode;

    @ApiModelProperty(value = "职位等级名称")
    private String levelDescr;

    @ApiModelProperty(value = "职类编码")
    private String posnType;

    @ApiModelProperty(value = "职类名称")
    private String posnTypeDescr;

    @ApiModelProperty(value = "职族编码")
    private String profession;

    @ApiModelProperty(value = "职族名称")
    private String professionDescr;

    @ApiModelProperty(value = "职序编码")
    private String secCode;

    @ApiModelProperty(value = "职序名称")
    private String secDescr;
}

