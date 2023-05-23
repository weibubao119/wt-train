package com.dyys.hr.vo.train;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "培训班-培训资料学习页VO")
public class TrainMaterialsLearnVO implements Serializable {
    @ApiModelProperty(value = "培训班ID")
    private Long programsId;

    @ApiModelProperty(value = "材料学习标题")
    private String title;

    @ApiModelProperty(value = "已学人数")
    private Integer learnedNum;

    @ApiModelProperty(value = "学习材料分类页数据")
    private List<TrainMaterialsLearnCategoryVO> categoryList;
}
