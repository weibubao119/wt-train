package com.dyys.hr.vo.train;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Desc
 * Create by yangye
 * Date 2022/12/20 11:00
 */
@Data
public class TrainInstitutionStaffVO {
    @ApiModelProperty(value = "培训项目")
    private String trainName;

    @ApiModelProperty(value = "员工工号")
    private String id;

    @ApiModelProperty(value = "员工姓名")
    private String name;

    @ApiModelProperty(value = "员工工号")
    private String emplId;

    @ApiModelProperty(value = "员工姓名")
    private String emplName;

    @ApiModelProperty(value = "公司编码")
    private String companyCode;

    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @ApiModelProperty(value = "部门编码")
    private String departmentCode;

    @ApiModelProperty(value = "部门名称")
    private String departmentName;

    @ApiModelProperty(value = "职序编码")
    private String secCode;

    @ApiModelProperty(value = "职序名称")
    private String secName;

    @ApiModelProperty(value = "职级编码")
    private String gradeCode;

    @ApiModelProperty(value = "职级名称")
    private String gradeName;

    @ApiModelProperty(value = "职位/岗位编码")
    private String postCode;

    @ApiModelProperty(value = "职位/岗位名称")
    private String postName;

    @ApiModelProperty(value = "职位/岗位编码")
    private String jobCode;

    @ApiModelProperty(value = "职位/岗位名称")
    private String jobName;
}
