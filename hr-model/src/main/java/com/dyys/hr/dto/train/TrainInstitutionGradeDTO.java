package com.dyys.hr.dto.train;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Desc
 * Create by yangye
 * Date 2022/9/25 9:37
 */
@Data
@ApiModel(value = "机构等级传输参数")
public class TrainInstitutionGradeDTO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "等级记录ID")
    @NotNull(message = "缺少等级记录ID",groups = {Update.class})
    @Min(value = 1L, message = "缺少等级记录ID", groups = {Update.class})
    private Long id;

    @ApiModelProperty(value = "机构ID", required = true)
    @NotNull(message = "缺少机构ID",groups = {Insert.class, Update.class})
    @Min(value = 1L, message = "缺少机构ID", groups = {Insert.class, Update.class})
    private Long institutionId;

    @ApiModelProperty(value = "评估年度")
    @NotBlank(message = "评估年度不能为空",groups = {Insert.class, Update.class})
    @Length(min = 4, max = 4, message = "评估年度必须为4位字符",groups = {Insert.class, Update.class})
    private String yearly;

    @ApiModelProperty(value = "机构等级ID", required = true)
    @NotNull(message = "请选择机构等级",groups = {Insert.class, Update.class})
    @Min(value = 1L, message = "请选择机构等级", groups = {Insert.class, Update.class})
    private Integer gradeId;

    @ApiModelProperty(value = "说明")
    @Length(max = 255, message = "说明内容不能超过255个字符",groups = {Insert.class, Update.class})
    private String memo;

    @ApiIgnore
    public interface Insert{}
    @ApiIgnore
    public interface Update{}
}
