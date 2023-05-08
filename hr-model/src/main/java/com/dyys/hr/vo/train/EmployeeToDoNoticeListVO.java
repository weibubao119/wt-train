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
@ApiModel(value = "员工代办通知列表vo")
public class EmployeeToDoNoticeListVO implements Serializable {
    @ApiModelProperty(value = "数据id")
    private Integer id;

    @ApiModelProperty(value = "通知类型 1.人员参训 2.考试通知 3.参训评估 4.机构评估 10.临时需求审批 11.培训计划审批")
    private Integer type;

    @ApiModelProperty(value = "类别名称")
    private String typeName;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "开始时间")
    private String startTime;

    @ApiModelProperty(value = "结束时间")
    private String endTime;
}
