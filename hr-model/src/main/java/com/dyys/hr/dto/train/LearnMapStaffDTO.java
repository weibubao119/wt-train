package com.dyys.hr.dto.train;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Desc
 * Create by yangye
 * Date 2022/9/8 8:57
 */
@Data
@ApiModel(value = "学员地图传输参数")
public class LearnMapStaffDTO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "员工工号", required = true)
    @NotBlank(message = "缺少员工工号",groups = {Insert.class, Update.class})
    private String emplId;

    @ApiModelProperty(value = "学习地图ID", required = true)
    @NotNull(message = "请提交选择的学习地图ID",groups = {Insert.class, Update.class})
    @Min(value = 1L, message = "请提交选择的学习地图ID", groups = {Insert.class, Update.class})
    private Long mapId;

    @ApiModelProperty(value = "学习地图编码", required = true)
    @NotBlank(message = "请提交选择的学习地图编码",groups = {Insert.class, Update.class})
    private String mapCode;

    @ApiModelProperty(value = "学习地图职序编码", required = true)
    @NotBlank(message = "请提交选择的学习地图职序编码",groups = {Insert.class, Update.class})
    private String posnSecCode;

    @ApiModelProperty(value = "学习职级", required = true)
    @NotBlank(message = "请选择学习职级",groups = {Insert.class, Update.class})
    private String posnGradeCode;

    @ApiModelProperty(value = "学习地图学习方向", required = true)
    @NotEmpty(message = "请选择学习方向", groups = {Insert.class,Update.class})
    private List<LearnMapStaffSblDTO> staffSblList;

    @ApiIgnore
    public interface Insert{}
    @ApiIgnore
    public interface Update{}
}
