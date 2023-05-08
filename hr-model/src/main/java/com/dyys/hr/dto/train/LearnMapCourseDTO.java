package com.dyys.hr.dto.train;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.*;
import java.util.List;

/**
 * Desc
 * Create by yangye
 * Date 2022/9/7 10:22
 */
@Data
@ApiModel(value = "学习地图课程传输参数")
public class LearnMapCourseDTO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "地图课程记录ID")
    @NotNull(message = "缺少地图课程记录ID",groups = {Update.class})
    @Min(value = 1L, message = "缺少地图课程记录ID", groups = {Update.class})
    private Long id;

    @ApiModelProperty(value = "地图ID", required = true)
    @NotNull(message = "缺少地图ID",groups = {Insert.class, Update.class})
    @Min(value = 1L, message = "缺少地图ID", groups = {Insert.class, Update.class})
    private Long mapId;

    @ApiModelProperty(value = "职级编码", required = true)
    @NotBlank(message = "缺少职级编码",groups = {Insert.class, Update.class})
    private String posnGradeCode;

    @ApiModelProperty(value = "学习方向", required = true)
    @NotNull(message = "请选择学习方向",groups = {Insert.class, Update.class})
    @Min(value = 1L, message = "请选择学习方向", groups = {Insert.class, Update.class})
    private Integer sblId;

    @ApiModelProperty(value = "课程模块名称", required = true)
    @NotBlank(message = "课程模块名称不能为空",groups = {Insert.class, Update.class})
    private String moduleName;

    @ApiModelProperty(value = "课程编号", required = true)
    @NotBlank(message = "请选择课程",groups = {Insert.class, Update.class})
    private String courseNumber;

    @ApiModelProperty(value = "必修标识：1必修；0选修（默认）", required = true)
    @NotNull(message = "请选择是否必修",groups = {Insert.class, Update.class})
    @Min(value = 0, message = "请选择是否必修", groups = {Insert.class, Update.class})
    @Max(value = 1, message = "请选择是否必修", groups = {Insert.class, Update.class})
    private Integer requiredFlag;

    @ApiModelProperty(value = "同等课程列表")
//    @NotEmpty(message = "请添加同等课程", groups = {Insert.class,Update.class})
    private List<LearnMapCourseTantaDTO> courseTantaList;

    @ApiIgnore
    public interface Insert{}
    @ApiIgnore
    public interface Update{}
}
