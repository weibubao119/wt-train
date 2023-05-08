package com.dyys.hr.vo.statis;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Desc
 * Create by yangye
 * Date 2023/3/22 11:37
 */
@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "组织列表培训项目总数分布趋势VO")
public class MonthlyDeptVO {
    @ApiModelProperty(value = "组织编码")
    private String deptId;

    @ApiModelProperty(value = "组织名称")
    private String deptName;

    @ApiModelProperty(value = "月度数据")
    private List<MonthlyVO> deptMonthlyList;
}
