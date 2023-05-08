package com.dyys.hr.dto.train;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 *  场地vo
 *  wsj
 */
@Data
@ApiModel(value="TrainBaseSiteDTO",description="场地视图对象")
public class TrainBaseSiteDTO implements Serializable {

    @ApiModelProperty(value = "场地ID")
    @NotNull(message = "场地ID不能为空", groups = { Update.class})
    private Integer id;

    @ApiModelProperty(value = "场地名称")
    @NotBlank(message = "场地名称不能为空", groups = {Update.class,Insert.class})
    private String siteName;

    @ApiModelProperty(value = "所属组织编码")
    @NotBlank(message = "所属组织编码不能为空", groups = {Update.class,Insert.class})
    private String deptId;

    @ApiModelProperty(value = "所属公司编码")
    @NotBlank(message = "所属公司编码不能为空", groups = {Update.class,Insert.class})
    private String companyCode;

    @ApiModelProperty(value = "负责人")
    @NotBlank(message = "负责人不能为空", groups = {Update.class,Insert.class})
    private String principal;

    @ApiModelProperty(value = "联系电话")
    @NotBlank(message = "联系电话不能为空", groups = {Update.class,Insert.class})
    private String contactPhone;

    @ApiModelProperty(value = "可容纳人数")
    @NotNull(message = "可容纳人数不能为空", groups = {Update.class,Insert.class})
    private Integer maxCapacity;

    @ApiModelProperty(value = "场地地址")
    @NotBlank(message = "场地地址不能为空", groups = {Update.class,Insert.class})
    private String address;

    @ApiIgnore
    public interface Insert{}
    @ApiIgnore
    public interface Update{}
}
