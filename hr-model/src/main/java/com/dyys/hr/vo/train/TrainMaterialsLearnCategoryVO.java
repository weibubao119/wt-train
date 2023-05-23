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
@ApiModel(value = "培训班-培训资料学习分类页VO")
public class TrainMaterialsLearnCategoryVO implements Serializable {
    @ApiModelProperty(value = "材料分类")
    private String category;

    @ApiModelProperty(value = "学习材料列表")
    private List<TrainMaterialsVO> materialsList;
}
