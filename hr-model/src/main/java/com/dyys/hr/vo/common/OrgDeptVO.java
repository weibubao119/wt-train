package com.dyys.hr.vo.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Desc
 * Create by yangye
 * Date 2023/3/16 13:27
 */
@Data
public class OrgDeptVO {
    @ApiModelProperty(value = "组织(部门)编码")
    private String deptId;

    @ApiModelProperty(value = "组织(部门)路径名称")
    private String deptPathName;
}
