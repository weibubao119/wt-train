package com.dyys.hr.entity.sys;

import com.dagongma.kernel.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 系统用户Token
 * </p>
 *
 * @author lidaan
 * @since 2022-04-18
 */
@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@Table(name = "sys_user_token")
@ApiModel(value = "SysUserToken对象", description = "系统用户Token")
public class SysUserToken extends BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty("用户ID")
    @Column(name = "user_id")
    private String userId;

    @ApiModelProperty("用户名")
    @Column(name = "user_name")
    private String userName;

    @ApiModelProperty("账号ID")
    @Column(name = "account_id")
    private String accountId;

    @ApiModelProperty("token")
    @Column(name = "token")
    private String token;

    @ApiModelProperty("权限部门ID列表")
    private List<String> deptList;

    @ApiModelProperty("系统角色集")
    private List<String> roleList;

    @ApiModelProperty("过期时间")
    @Column(name = "expire_date")
    private Date expireDate;

    @ApiModelProperty("更新时间")
    @Column(name = "update_date")
    private Date updateDate;
}
