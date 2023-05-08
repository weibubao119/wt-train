package com.dyys.hr.vo.train;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@ApiModel(value = "培训项目考勤管理学员签到情况列表vo")
public class TrainAttendanceStudentSignInDataVO implements Serializable{
    @ApiModelProperty(value = "学员编号")
    private String userId;

    @ApiModelProperty(value = "学员名称")
    private String userName;

    @ApiModelProperty(value = "公司编码")
    private String companyCode;

    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @ApiModelProperty(value = "部门编码")
    private String departmentCode;

    @ApiModelProperty(value = "部门名称")
    private String departmentName;

    @ApiModelProperty(value = "考勤次数")
    private Integer attendanceNum;

    @ApiModelProperty(value = "迟到次数")
    private Integer lateNum;

    @ApiModelProperty(value = "旷课次数")
    private Integer absentNum;

    @ApiModelProperty("签到时间")
    private String signInTime;

    @ApiModelProperty("签到状态")
    private Integer status;

//    @ApiModelProperty(value = "每次签到情况")
//    private List<TrainAttendanceRecordVO> trainAttendanceRecordVOList;

}
