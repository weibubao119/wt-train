package com.dyys.hr.vo.train;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Description
 * Create by yangye
 * Date 2022/5/11 15:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "培训机构分页列表展示参数")
public class TrainInstitutionVO {
    @ApiModelProperty(value = "机构ID")
    private Long id;

    @ApiModelProperty(value = "机构名称")
    private String name;

    @ApiModelProperty(value = "机构类型ID")
    private Integer cateId;

    @ApiModelProperty(value = "机构类型名称")
    private String cateName;

    @ApiModelProperty(value = "负责人姓名")
    private String principalName;

    @ApiModelProperty(value = "负责人电话")
    private String principalPhone;

    @ApiModelProperty(value = "机构地址")
    private String address;

    @ApiModelProperty(value = "年度")
    private String ratingYear;

    @ApiModelProperty(value = "等级")
    private Integer gradeId;

    @ApiModelProperty(value = "等级名称")
    private String gradeName;
}
