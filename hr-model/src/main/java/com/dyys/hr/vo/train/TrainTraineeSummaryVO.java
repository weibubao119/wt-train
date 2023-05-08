package com.dyys.hr.vo.train;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "培训项目学员总结列表VO")
public class TrainTraineeSummaryVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "工号")
    private String emplId;

    @ApiModelProperty(value = "姓名")
    private String emplName;

    @ApiModelProperty(value = "公司")
    private String companyDescr;

    @ApiModelProperty(value = "部门")
    private String deptDescr;

    @ApiModelProperty(value = "岗位")
    private String postDescr;

    @ApiModelProperty(value = "职级")
    private String gradeDescr;

    @ApiModelProperty(value = "职序")
    private String secDescr;

    @ApiModelProperty(value = "总成绩")
    private String totalScore;

    @ApiModelProperty(value = "是否获证 0.否 1.是")
    private Integer isObtain;

    @ApiModelProperty(value = "是否获证状态名称")
    private String isObtainName;
}
