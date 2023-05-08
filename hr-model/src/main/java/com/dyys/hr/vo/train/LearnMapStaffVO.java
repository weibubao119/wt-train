package com.dyys.hr.vo.train;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Desc
 * Create by yangye
 * Date 2022/9/7 15:38
 */
@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "学员地图")
public class LearnMapStaffVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("员工工号")
    private String emplId;

    @ApiModelProperty("员工姓名")
    private String emplName;

    @ApiModelProperty("公司名称")
    private String companyName;

    @ApiModelProperty("部门名称")
    private String deptName;

    @ApiModelProperty("职序名称")
    private String secName;

    @ApiModelProperty("职级编码名称")
    private String gradeCodeName;

    @ApiModelProperty("学习地图名称")
    private String mapName;

    @ApiModelProperty("学习职级编码名称")
    private String mapGradeCodeName;

    @ApiModelProperty("方向")
    private String sblName;
}
