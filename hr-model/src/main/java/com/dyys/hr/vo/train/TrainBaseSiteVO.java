package com.dyys.hr.vo.train;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

/**
 *  场地vo
 *  wsj
 */
@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value="TrainBaseSiteVO",description="场地视图对象")
public class TrainBaseSiteVO implements Serializable {

    @ApiModelProperty(value = "场地ID")
    private Integer id;

    @ApiModelProperty(value = "场地名称")
    private String siteName;

    @ApiModelProperty(value = "所属组织编码")
    private String deptId;

    @ApiModelProperty(value = "所属组织名称")
    private String deptPathName;

    @ApiModelProperty(value = "负责人")
    private String principal;

    @ApiModelProperty(value = "联系方式")
    private String contactPhone;

    @ApiModelProperty(value = "最大容量")
    private Integer maxCapacity;

    @ApiModelProperty(value = "场地地址")
    private String address;
}
