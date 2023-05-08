package com.dyys.hr.vo.train;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "培训项目考试详情vo")
public class TrainExamDetailVO implements Serializable{
    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "项目id")
    private Long programsId;

    @ApiModelProperty(value = "考试标题")
    private String title;

    @ApiModelProperty(value = "考试类型")
    private Integer type;

    @ApiModelProperty(value = "考试类型名称")
    private String typeName;

    @ApiModelProperty(value = "考试模板id")
    private Long paperId;

    @ApiModelProperty(value = "考试模板")
    private String paperName;

    @ApiModelProperty(value = "关联课程id")
    private Long courseId;

    @ApiModelProperty(value = "关联课程名称")
    private String courseName;

    @ApiModelProperty(value = "时间是否限制")
    private Integer isLimit;

    @ApiModelProperty(value = "时间是否限制名称")
    private String isLimitName;

    @ApiModelProperty(value = "开始时间")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm")
    private Date endTime;

    @ApiModelProperty(value = "考试总时长")
    private String duration;

    @ApiModelProperty(value = "考试及格分")
    private String passScore;

    @ApiModelProperty(value = "考试总分")
    private String totalScore;

    @ApiModelProperty(value = "考试次数限制")
    private Integer examCount;

    @ApiModelProperty(value = "参考人员数据列表")
    private List<TrainExaminerVO> examinerList;


}
