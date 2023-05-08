/**
 * Copyright (c) 2018 CoolChange All rights reserved.
 * <p>
 * https://www.coolchange.tech
 * <p>
 * 版权所有，侵权必究！
 */

package com.dyys.hr.oss;

import com.dyys.hr.oss.group.FastDFSGroup;
import com.dyys.hr.oss.group.LocalGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 云存储配置信息
 *
 * @author
 */
@Data
@ApiModel(value = "云存储配置信息")
@Component
@ConfigurationProperties(prefix = "storage-config")
public class CloudStorageConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "类型 1：七牛  2：阿里云  3：腾讯云   4：FastDFS   5：本地上传")
    @Range(min = 1, max = 5, message = "{oss.type.range}")
    private Integer type;

    @ApiModelProperty(value = "FastDFS绑定的域名")
    @NotBlank(message = "{fastdfs.domain.require}", groups = FastDFSGroup.class)
    @URL(message = "{fastdfs.domain.url}", groups = FastDFSGroup.class)
    private String fastdfsDomain;

    @ApiModelProperty(value = "本地上传绑定的域名")
    @NotBlank(message = "{local.domain.require}", groups = LocalGroup.class)
    @URL(message = "{local.domain.url}", groups = LocalGroup.class)
    private String localDomain;

    @ApiModelProperty(value = "本地上传路径前缀")
    private String localPrefix;

    @ApiModelProperty(value = "本地上传存储目录")
    @NotBlank(message = "{local.path.url}", groups = LocalGroup.class)
    private String localPath;

}
