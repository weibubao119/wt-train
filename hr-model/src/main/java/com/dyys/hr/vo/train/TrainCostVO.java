package com.dyys.hr.vo.train;

import com.dyys.hr.dto.train.FileDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "培训项目费用VO")
public class TrainCostVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "费用Id")
    private String id;

    @ApiModelProperty(value = "培训科目常量id")
    private Integer subjectsId;

    @ApiModelProperty(value = "培训科目常量名称")
    private String subjectsName;

    @ApiModelProperty(value = "金额")
    private Float amount;

    @ApiModelProperty(value = "附件列表")
    private String fileListString;

    @ApiModelProperty(value = "附件列表")
    private List<FileDTO> fileList;
}
