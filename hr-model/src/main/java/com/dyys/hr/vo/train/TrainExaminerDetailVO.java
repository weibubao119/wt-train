package com.dyys.hr.vo.train;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "培训项目人员参考记录vo")
public class TrainExaminerDetailVO implements Serializable{
    @ApiModelProperty(value = "明细ID")
    private Long id;

    @ApiModelProperty(value = "人员参考记录ID")
    private Long examinerId;

    @ApiModelProperty(value = "考试时间")
    private Date examTime;

    @ApiModelProperty(value = "考试用时")
    private String useTime;

    @ApiModelProperty(value = "考试得分")
    private String score;

    @ApiModelProperty(value = "是否合格 0.不合格 1.合格")
    private Integer isPass;

    @ApiModelProperty(value = "是否合格名称")
    private String isPassName;

    @ApiModelProperty(value = "状态 0.未完成 1.已完成")
    private Integer status;

    @ApiModelProperty(value = "状态名称")
    private String statusName;
}
