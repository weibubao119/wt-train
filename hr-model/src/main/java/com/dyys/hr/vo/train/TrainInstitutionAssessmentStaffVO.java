package com.dyys.hr.vo.train;

import com.dyys.hr.dto.train.TrainInstitutionAssessmentDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "机构评估参评人员展示参数")
public class TrainInstitutionAssessmentStaffVO {

    @ApiModelProperty(value = "参评人员记录ID")
    private Long id;

    @ApiModelProperty(value = "评估人编号/ID", required = true)
    @NotBlank(message = "评估人编号/ID不能为空",groups = {TrainInstitutionAssessmentDTO.Insert.class, TrainInstitutionAssessmentDTO.Update.class})
    private String evaluatorEmplId;

    @ApiModelProperty(value = "评估人姓名")
    private String evaluatorEmplName;

    @ApiModelProperty(value = "评估人所属公司ID", required = true)
    @NotBlank(message = "评估人所属公司ID不能为空",groups = {TrainInstitutionAssessmentDTO.Insert.class, TrainInstitutionAssessmentDTO.Update.class})
    private String evaluatorCompanyId;

    @ApiModelProperty(value = "评估人所属公司名称")
    private String evaluatorCompanyName;

    @ApiModelProperty(value = "评估人所属部门ID")
    @NotBlank(message = "评估人所属部门ID不能为空",groups = {TrainInstitutionAssessmentDTO.Insert.class, TrainInstitutionAssessmentDTO.Update.class})
    private String evaluatorDeptId;

    @ApiModelProperty(value = "评估人所属部门名称")
    private String evaluatorDeptName;

    @ApiModelProperty(value = "评估人所在岗位ID")
    @NotBlank(message = "评估人所在岗位ID不能为空",groups = {TrainInstitutionAssessmentDTO.Insert.class, TrainInstitutionAssessmentDTO.Update.class})
    private String evaluatorJobCode;

    @ApiModelProperty(value = "评估人所在岗位名称")
    private String evaluatorJobName;

    @ApiModelProperty(value = "评估人答卷ID")
    private Long answerId;

    @ApiModelProperty(value = "是否完成评估：0未完成(默认)，1已完成")
    private Integer isFinished;
}
