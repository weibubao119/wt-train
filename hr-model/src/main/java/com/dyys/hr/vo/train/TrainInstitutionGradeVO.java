package com.dyys.hr.vo.train;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "机构等级分页列表前端展示参数")
public class TrainInstitutionGradeVO {
    @ApiModelProperty(value = "等级记录ID")
    private Long id;

    @ApiModelProperty(value = "机构ID", required = true)
    private Long institutionId;

    @ApiModelProperty(value = "评估年度")
    private String yearly;

    @ApiModelProperty(value = "机构等级ID", required = true)
    private Integer gradeId;

    @ApiModelProperty(value = "机构等级名称")
    private String gradeName;

    @ApiModelProperty(value = "说明")
    private String memo;

    @ApiIgnore
    public interface Insert{}
    @ApiIgnore
    public interface Update{}
}
