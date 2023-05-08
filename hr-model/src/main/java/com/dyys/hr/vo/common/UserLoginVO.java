package com.dyys.hr.vo.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Desc
 * Create by yangye
 * Date 2023/3/15 9:47
 */
@Data
public class UserLoginVO {
    @ApiModelProperty("登录员工角色集")
    private List<String> roleList;

    @ApiModelProperty("权限部门ID集")
    private List<String> deptList;
}
