package com.dyys.hr.dto.train;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotBlank;

/**
 * Desc
 * Create by yangye
 * Date 2022/9/6 14:57
 */
@Data
@ApiModel(value = "学习地图职级列表传输参数")
public class LearnMapPosnGradeDTO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "职级编码", required = true)
    @NotBlank(message = "请选择职级",groups = {Insert.class, Update.class})
    private String posnGradeCode;

    @ApiModelProperty(value = "高一级职级编码", required = true)
    @NotBlank(message = "请选择高一级职级",groups = {Insert.class, Update.class})
    private String pPosnGradeCode;

    @ApiIgnore
    public interface Insert{}
    @ApiIgnore
    public interface Update{}
}
