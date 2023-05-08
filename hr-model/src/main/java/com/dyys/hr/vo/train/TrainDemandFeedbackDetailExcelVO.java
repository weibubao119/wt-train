package com.dyys.hr.vo.train;

import com.dyys.hr.entity.train.excel.DemandFeedbackImportExcel;
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
 * Date 2022/11/22 14:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "导入需求单人反馈数据校验结果")
public class TrainDemandFeedbackDetailExcelVO {
    @ApiModelProperty(value = "校验不通过数据")
    private List<DemandFeedbackImportExcel> errorList;

    @ApiModelProperty(value = "校验通过数据")
    private List<TrainDemandFeedbackDetailVO> dataList;
}
