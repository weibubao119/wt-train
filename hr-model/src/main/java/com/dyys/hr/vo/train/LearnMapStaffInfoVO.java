package com.dyys.hr.vo.train;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Desc
 * Create by yangye
 * Date 2022/9/8 11:04
 */
@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "学员地图详情")
public class LearnMapStaffInfoVO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("工号")
    private String emplId;

    @ApiModelProperty("姓名")
    private String emplName;

    @ApiModelProperty("公司名称")
    private String companyName;

    @ApiModelProperty("部门名称")
    private String deptName;

    @ApiModelProperty("当前岗位名称")
    private String postName;

    @ApiModelProperty("当前职序名称")
    private String secName;

    @ApiModelProperty("当前职级名称")
    private String gradeCodeName;

    @ApiModelProperty("当前学习地图名称")
    private String mapName;

    @ApiModelProperty("当前学习职序名称")
    private String mapSecName;

    @ApiModelProperty("当前学习职级名称")
    private String mapGradeCodeName;

    @ApiModelProperty("高一级学习职级名称")
    private String highMapGradeCodeName;

    @ApiModelProperty("低一级学习职级名称")
    private String lowMapGradeCodeName;

    @ApiModelProperty("当前学习职级课程完成率")
    private String finishRate;

    @ApiModelProperty("高一级学习职级课程完成率")
    private String highFinishRate;

    @ApiModelProperty("当前学习职级课程完成比例")
    private String finishRatio;

    @ApiModelProperty("高一级学习职级课程完成比例")
    private String highFinishRatio;

    @ApiModelProperty("低一级学习职级课程完成比例")
    private String lowFinishRatio;

    @ApiModelProperty("当前学习职级课程列表")
    private List<LearnMapStuCourseVO> currentList;

    @ApiModelProperty("低一级学习职级课程列表")
    private List<LearnMapStuCourseVO> lowList;

    @ApiModelProperty("高一级学习职级课程列表")
    private List<LearnMapStuCourseVO> highList;
}
