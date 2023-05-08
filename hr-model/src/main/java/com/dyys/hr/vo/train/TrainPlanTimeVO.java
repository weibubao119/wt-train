package com.dyys.hr.vo.train;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 功能描述：
 *
 * @author syyang
 * @date 2019-09-23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "首页培训计划统计前端接收VO")
public class TrainPlanTimeVO implements Serializable {
    /**
     * 机构id
     */
    @ApiModelProperty(value = "机构id")
    private Long companyId;

    /**
     * 当前年月
     */
    @ApiModelProperty(value = "当前年月")
    private String month;
}
