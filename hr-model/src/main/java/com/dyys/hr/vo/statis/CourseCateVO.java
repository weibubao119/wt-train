package com.dyys.hr.vo.statis;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Desc
 * Create by yangye
 * Date 2023/3/24 15:27
 */
@Data
public class CourseCateVO {
    @ApiModelProperty(value = "课程类别ID")
    private String courseCateId;

    @ApiModelProperty(value = "课程类别名称")
    private String courseCateName;

    @ApiModelProperty(value = "统计数量")
    private String statisNum;

    @ApiModelProperty(value = "占比")
    private String statisRatio;
}
