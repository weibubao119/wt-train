package com.dyys.hr.vo.train;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "培训班-培训材料VO")
public class TrainMaterialsVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "材料Id")
    private Long id;

    @ApiModelProperty(value = "材料分类")
    private String category;

    @ApiModelProperty(value = "材料名称")
    private String filename;

    @ApiModelProperty(value = "材料地址")
    private String src;

    @ApiModelProperty(value = "类别 1.音视频 2.其他")
    private Integer type;

    @ApiModelProperty(value = "时长")
    private String duration;

    @ApiModelProperty(value = "状态 1.已发布 0.未发布")
    private Integer status;

    @ApiModelProperty(value = "学习状态：1.已学 0.未学")
    private Integer learnedStatus;
}
