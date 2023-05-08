package com.dyys.hr.vo.train;

import com.dyys.hr.dto.train.CompanyDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author wangyunzhen
 * @date 2019/6/19 22:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "培训计划详情筛选VO")
public class TrainPlanDetailSelectVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "计划详情ID")
    private Long id;

    @ApiModelProperty(value = "计划培训名称")
    private String name;

    @ApiModelProperty(value = "单位编码")
    private String companyCode;

    @ApiModelProperty(value = "单位名称")
    private String companyName;

    @ApiModelProperty(value = "协办单位串")
    private String coOrganizer;

    @ApiModelProperty(value = "协办单位列表")
    private List<CompanyDTO> coOrganizerList;
}
