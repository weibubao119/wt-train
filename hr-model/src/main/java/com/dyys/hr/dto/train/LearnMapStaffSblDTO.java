package com.dyys.hr.dto.train;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Desc
 * Create by yangye
 * Date 2022/9/8 9:10
 */
@Data
@ApiModel(value = "学员地图学习方向传输参数")
public class LearnMapStaffSblDTO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "学习方向ID", required = true)
    @NotNull(message = "缺少学习方向ID",groups = {Insert.class, Update.class})
    @Min(value = 1, message = "缺少学习方向ID", groups = {Insert.class, Update.class})
    private Integer sblId;

    @ApiIgnore
    public interface Insert{}
    @ApiIgnore
    public interface Update{}
}
