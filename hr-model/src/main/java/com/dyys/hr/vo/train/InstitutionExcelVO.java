package com.dyys.hr.vo.train;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Desc
 * Create by yangye
 * Date 2022/9/25 15:17
 */
@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "培训机构批量导入校验不通过数据")
public class InstitutionExcelVO {
    @ApiModelProperty(value = "提示语")
    private String errMsg;

    @ApiModelProperty(value = "机构名称")
    private String name;

    @ApiModelProperty(value = "机构类型")
    private String cateIdName;

    @ApiModelProperty(value = "机构联系人姓名")
    private String principalName;

    @ApiModelProperty(value = "机构联系人电话")
    private String principalPhone;

    @ApiModelProperty(value = "机构地址")
    private String address;
}
