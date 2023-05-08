package com.dyys.hr.vo.train;

import com.dyys.hr.dto.train.FileDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "培训项目资料VO")
public class TrainMaterialsVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "资料Id")
    private String id;

    @ApiModelProperty(value = "材料内容")
    private String content;

    @ApiModelProperty(value = "附件")
    private String fileString;

    @ApiModelProperty(value = "附件")
    private FileDTO file;
}
