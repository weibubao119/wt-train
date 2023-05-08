package com.dyys.hr.vo.train;

import com.dagongma.kernel.annotation.EnumValue;
import com.dagongma.kernel.core.entity.BaseEntity;
import com.dyys.hr.enums.YesOrNoEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

/**
 * 
 * 场地租赁信息实体类
 * @author JUCHUAN LI
 * @date 2019/06/26
 */ 
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "场地租赁信息实体类")
public class TrainSiteLeaseInfoVO {

    /**
     * 场地Id
     */
    @ApiModelProperty(value = "场地Id")
    private Long siteId;

    /**
     * 时间
     */
    @ApiModelProperty(value = "时间")
    private String trainDate;

    /**
     * 日期
     */
    @ApiModelProperty(value = "日期")
    private String trainTime;

    /**
     * 课程名称
     */
    @ApiModelProperty(value = "课程名称")
    private String courseName;

    /**
     * 组织方Id
     */
    @ApiModelProperty(value = "组织方Id")
    private Long organisers;

    /**
     * 组织方名称
     */
    @ApiModelProperty(value = "组织方名称")
    private String organisersName;


    /**
     * 负责人
     */
    @ApiModelProperty(value = "负责人")
    private String leadingIds;

    /**
     * 负责人名称
     */
    @ApiModelProperty(value = "负责人名称")
    private String leadingName;
}