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
 * Date 2022/9/6 14:45
 */
@Data
@ApiModel(value = "学习地图传输参数")
public class LearnMapDTO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "地图ID")
    @NotNull(message = "缺少地图ID",groups = {Update.class})
    @Min(value = 1L, message = "缺少地图ID", groups = {Update.class})
    private Long id;

    @ApiModelProperty(value = "地图编号", required = true)
    @NotBlank(message = "地图编号不能为空",groups = {Insert.class, Update.class})
    private String mapCode;

    @ApiModelProperty(value = "地图名称", required = true)
    @NotBlank(message = "地图名称不能为空",groups = {Insert.class, Update.class})
    private String mapName;

    @ApiModelProperty(value = "职序编码", required = true)
    @NotBlank(message = "请选择职序",groups = {Insert.class, Update.class})
    private String posnSecCode;

    @ApiModelProperty(value = "职级列表", required = true)
    @NotEmpty(message = "请添加职级", groups = {Insert.class,Update.class})
    private List<LearnMapPosnGradeDTO> posnGradeList;

    @ApiIgnore
    public interface Insert{}
    @ApiIgnore
    public interface Update{}
}
