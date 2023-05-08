package com.dyys.hr.dto.train;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Description
 * Create by yangye
 * Date 2022/5/11 15:42
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "培训机构传输参数")
public class TrainInstitutionDTO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "机构ID")
    @NotNull(message = "缺少机构ID",groups = {Update.class})
    @Min(value = 1L, message = "缺少机构ID", groups = {Update.class})
    private Long id;

    @ApiModelProperty(value = "机构名称", required = true)
    @NotBlank(message = "机构名称不能为空",groups = {Insert.class, Update.class})
    private String name;

    @ApiModelProperty(value = "机构类型ID", required = true)
    @NotNull(message = "请选择机构类型",groups = {Insert.class, Update.class})
    @Min(value = 0, message = "请选择机构类型", groups = {Insert.class, Update.class})
    private Integer cateId;

    @ApiModelProperty(value = "负责人姓名", required = true)
    @NotBlank(message = "负责人姓名不能为空",groups = {Insert.class, Update.class})
    private String principalName;

    @ApiModelProperty(value = "负责人电话", required = true)
    @NotBlank(message = "负责人电话不能为空",groups = {Insert.class, Update.class})
    private String principalPhone;

    @ApiModelProperty(value = "机构地址", required = true)
    // @NotBlank(message = "机构地址不能为空",groups = {Insert.class, Update.class})
    private String address;

    @ApiIgnore
    public interface Insert{}
    @ApiIgnore
    public interface Update{}
}
