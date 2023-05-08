package com.dyys.hr.vo.train;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Desc
 * Create by yangye
 * Date 2022/9/8 16:44
 */
@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "学员推荐课程信息")
public class LearnMapStuCourseVO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("地图课程记录ID")
    private Long id;

    @ApiModelProperty("地图ID")
    private Long mapId;

    @ApiModelProperty("职级编码")
    private String posnGradeCode;

    @ApiModelProperty("学习方向")
    private Integer sblId;

    @ApiModelProperty("学习方向名称")
    private String sblName;

    @ApiModelProperty("课程模块名称")
    private String moduleName;

    @ApiModelProperty("课程编号")
    private String courseNumber;

    @ApiModelProperty("课程名称")
    private String courseName;

    @ApiModelProperty("课程类别名称")
    private String courseCateName;

    @ApiModelProperty("课程学分")
    private BigDecimal credit;

    @ApiModelProperty("课程学时")
    private BigDecimal classHours;

    @ApiModelProperty("必修标识：1必修；0选修（默认）")
    private Integer requiredFlag;

    @ApiModelProperty("必修标识名称")
    private String requiredName;

    @ApiModelProperty("完成状态名称")
    private String finishName;

    @ApiModelProperty("同等课程列表")
    private List<LearnMapCourseTantaVO> courseTantaList;
}
