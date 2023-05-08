package com.dyys.hr.vo.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;

/**
 * @Author: Daan li
 * @Date: 2022/6/7 10:24
 */
@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
public class UserTokenVO implements Serializable {
    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty("用户ID(Emplid)")
    private String userId;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("账号ID")
    private String accountId;

    @ApiModelProperty("token")
    private String token;

    @ApiModelProperty("权限部门ID列表")
    private List<String> deptList;

    @ApiModelProperty("登录员工角色集")
    private List<String> roleList;

    @ApiModelProperty("组织id")
    private String organizationId;

    @ApiModelProperty("组织名称")
    private String organizationName;
}
