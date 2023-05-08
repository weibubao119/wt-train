package com.dyys.hr.dto.train;

import com.dyys.hr.vo.train.TrainInstitutionAssessmentStaffVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "机构评估传输参数")
public class TrainInstitutionAssessmentDTO {

    @ApiModelProperty(value = "评估记录ID")
    @NotNull(message = "缺少评估记录ID",groups = {Update.class})
    @Min(value = 1L, message = "缺少评估记录ID", groups = {Update.class})
    private Long id;

    @ApiModelProperty(value = "机构ID", required = true)
    @NotNull(message = "缺少机构ID",groups = {Insert.class, Update.class})
    @Min(value = 1L, message = "缺少机构ID", groups = {Insert.class, Update.class})
    private Long institutionId;

    @ApiModelProperty(value = "评估标题", required = true)
    @NotBlank(message = "评估标题不能为空",groups = {Insert.class, Update.class})
    private String title;

    @ApiModelProperty(value = "问卷模板ID", required = true)
    @NotNull(message = "请选择问卷模板",groups = {Insert.class, Update.class})
    @Min(value = 1L, message = "请选择问卷模板", groups = {Insert.class, Update.class})
    private Long questionnaireId;

    @ApiModelProperty(value = "问卷模板名称")
    private String questionnaireName;

    @ApiModelProperty(value = "年度", required = true)
    @NotBlank(message = "年度不能为空",groups = {Insert.class, Update.class})
    @Length(min = 4, max = 4, message = "评估年度必须为4位字符",groups = {Insert.class, Update.class})
    private String yearly;

    @ApiModelProperty(value = "评估开始时间", required = true)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern = "yyyy-MM-dd HH:mm")
    @NotNull(message = "评估开始时间不能为空",groups = {Insert.class, Update.class})
    private Date startTime;

    @ApiModelProperty(value = "评估结束时间", required = true)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern = "yyyy-MM-dd HH:mm")
    @NotNull(message = "评估结束时间不能为空",groups = {Insert.class, Update.class})
    @Future(message = "评估结束时间必须大于当前时间", groups = {Insert.class, Update.class})
    private Date endTime;

    @ApiModelProperty(value = "评估得分")
    private BigDecimal score;

    @ApiModelProperty(value = "状态：1未开始，2进行中，3已完成，4已取消")
    private Integer status;

    @ApiModelProperty(value = "参评人员列表", required = true)
    @NotEmpty(message = "请选择参评人员", groups = {Insert.class,Update.class})
    private List<TrainInstitutionAssessmentStaffVO> staffList;

    @ApiIgnore
    public interface Insert{}
    @ApiIgnore
    public interface Update{}
}
