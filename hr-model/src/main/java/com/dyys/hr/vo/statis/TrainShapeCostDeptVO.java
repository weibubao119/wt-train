package com.dyys.hr.vo.statis;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Desc
 * Create by yangye
 * Date 2023/3/29 11:18
 */
@Data
public class TrainShapeCostDeptVO {
    @ApiModelProperty(value = "组织编码")
    private String deptId;

    @ApiModelProperty(value = "组织名称")
    private String deptName;

    @ApiModelProperty(value = "培训形式费用统计数据")
    private List<TrainShapeCostVO> trainShapeCostList;
}
