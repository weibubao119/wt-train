package com.dyys.hr.vo.train;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "管理员工作台相关数据vo")
public class AdminRelateDataVO implements Serializable {
    @ApiModelProperty(value = "进行中培训数量")
    private Integer trainingInProgressNum;

    @ApiModelProperty(value = "已完成培训数量")
    private Integer trainingCompletedNum;

    @ApiModelProperty(value = "已完成培训总人次")
    private Integer trainingTotalParticipantsNum;

    @ApiModelProperty(value = "培训完成率")
    private String trainingFinishedRate;

    @ApiModelProperty(value = "已培训总时长")
    private String trainingTotalHoursNum;

    @ApiModelProperty(value = "已完成培训人均学时")
    private String trainAvghours;

    @ApiModelProperty(value = "已完成培训费用(元)")
    private String trainTotalFee ;

}
