package com.dyys.hr.entity.staff;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 字典表
 * </p>
 *
 * @author yangye
 * @since 2022-08-23
 */
@Data
@Table(name = "staff_dict")
@ApiModel(value = "StaffDict对象", description = "字典表")
public class StaffDict implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("类型名称")
    private String typeName;

    @ApiModelProperty("类型数值")
    private String typeVal;

    @ApiModelProperty("名称")
    private String val;

    @ApiModelProperty("简称")
    private String shortVal;

    @ApiModelProperty("备注")
    private String memo;

    @ApiModelProperty("更新时间")
    private Date updateTime;


}
