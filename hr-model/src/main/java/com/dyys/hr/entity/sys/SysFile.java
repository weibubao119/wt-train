package com.dyys.hr.entity.sys;

import com.dagongma.kernel.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * <p>
 * 上传文件
 * </p>
 *
 * @author lidaan
 * @since 2022-05-07
 */
@Data
@Table(name = "sys_file")
@ApiModel(value = "SysFile对象", description = "上传文件")
public class SysFile extends BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty("文件名称")
    @Column(name = "file_name")
    private String fileName;

    @ApiModelProperty("上传后文件名")
    @Column(name = "file_name_encode")
    private String fileNameEncode;

    @ApiModelProperty("文件大小")
    @Column(name = "file_size")
    private Long fileSize;

    @ApiModelProperty("文件后缀")
    @Column(name = "file_suffix")
    private String fileSuffix;

    @ApiModelProperty("服务器存放地址")
    @Column(name = "server_path")
    private String serverPath;

    @ApiModelProperty("访问地址")
    @Column(name = "web_path")
    private String webPath;

    @ApiModelProperty("状态：1可用")
    @Column(name = "status")
    private Integer status;

    @ApiModelProperty("类型：1.音视频 2.其他")
    @Column(name = "type")
    private Integer type;

    @ApiModelProperty("时长")
    @Column(name = "duration")
    private String duration;

    @ApiModelProperty("创建人")
    @Column(name = "creator")
    private String creator;

    @ApiModelProperty("创建时间")
    @Column(name = "create_date")
    private Date createDate;
}
