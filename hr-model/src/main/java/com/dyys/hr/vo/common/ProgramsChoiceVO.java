package com.dyys.hr.vo.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Desc
 * Create by yangye
 * Date 2022/12/21 8:56
 */
@Data
public class ProgramsChoiceVO {
    @ApiModelProperty(value = "培训项目ID")
    private Long programsId;

    @ApiModelProperty(value = "培训项目编号")
    private String programsNumber;

    @ApiModelProperty(value = "培训项目名称")
    private String trainName;
}
