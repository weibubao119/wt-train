package com.dyys.hr.vo.train;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "员工培训课表vo")
public class EmployeeTrainingScheduleVO implements Serializable {
    @ApiModelProperty(value = "培训名称")
    private String trainName ;

    @ApiModelProperty(value = "开始天")
    private String startDay ;

    @ApiModelProperty(value = "结束天")
    private String endDay ;
}
