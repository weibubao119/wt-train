package com.dyys.hr.vo.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Desc
 * Create by yangye
 * Date 2023/3/22 11:21
 */
@Data
public class StaffDeptTerseVO {
    @ApiModelProperty(value = "部门编码")
    private String deptId;

    @ApiModelProperty(value = "部门名称")
    private String deptName;
}
