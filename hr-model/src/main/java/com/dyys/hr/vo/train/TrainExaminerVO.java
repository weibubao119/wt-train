package com.dyys.hr.vo.train;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "培训项目考试参考人员列表vo")
public class TrainExaminerVO implements Serializable{
    @ApiModelProperty(value = "参考记录id")
    private Long examinerId;

    @ApiModelProperty(value = "学员编号")
    private String userId;

    @ApiModelProperty(value = "学员名称")
    private String userName;

    @ApiModelProperty(value = "公司编码")
    private String companyCode;

    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @ApiModelProperty(value = "部门编码")
    private String departmentCode;

    @ApiModelProperty(value = "部门名称")
    private String departmentName;

    @ApiModelProperty(value = "岗位编码")
    private String jobCode;

    @ApiModelProperty(value = "岗位名称")
    private String jobName;

    @ApiModelProperty(value = "考试次数")
    private Integer examNum;

    @ApiModelProperty(value = "最高分")
    private String highestScore;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "状态名称")
    private String statusName;

    @ApiModelProperty(value = "最后考试时间")
    private Date finalTime;

}
