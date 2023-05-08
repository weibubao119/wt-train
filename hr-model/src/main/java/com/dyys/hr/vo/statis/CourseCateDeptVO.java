package com.dyys.hr.vo.statis;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Desc
 * Create by yangye
 * Date 2023/3/24 15:27
 */
@Data
public class CourseCateDeptVO {
    @ApiModelProperty(value = "组织编码")
    private String deptId;

    @ApiModelProperty(value = "组织名称")
    private String deptName;

    @ApiModelProperty(value = "课程类别数据")
    private List<CourseCateVO> courseCateList;
}
