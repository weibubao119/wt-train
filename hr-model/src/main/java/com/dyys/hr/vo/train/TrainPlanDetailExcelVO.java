package com.dyys.hr.vo.train;

import com.dyys.hr.entity.train.excel.PlanDetailExcel;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * Desc
 * Create by yangye
 * Date 2022/9/20 9:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "培训计划详情导入结果VO")
public class TrainPlanDetailExcelVO {
    @ApiModelProperty(value = "校验不通过数据")
    private List<PlanDetailExcel> errorList;

    @ApiModelProperty(value = "校验通过数据")
    private List<TrainPlanDetailVO> dataList;
}
