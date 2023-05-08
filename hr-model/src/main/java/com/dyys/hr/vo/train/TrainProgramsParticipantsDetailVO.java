package com.dyys.hr.vo.train;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author wangyunzhen
 * @date 2019/6/19 22:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "过程管理-项目管理参训人员详情VO")
public class TrainProgramsParticipantsDetailVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "序号")
    private String id;

    @ApiModelProperty(value = "参训人员编号")
    private String userId;

    @ApiModelProperty(value = "参训人员名称")
    private String userName;

    @ApiModelProperty(value = "所属公司编码")
    private String companyCode;

    @ApiModelProperty(value = "所属公司名称")
    private String companyName;

    @ApiModelProperty(value = "所属部门编码")
    private String departmentCode;

    @ApiModelProperty(value = "所属部门编码")
    private String departmentName;

    @ApiModelProperty(value = "岗位code")
    private String jobCode;

    @ApiModelProperty(value = "岗位名称")
    private String jobName;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "状态名称")
    private String statusName;
}
