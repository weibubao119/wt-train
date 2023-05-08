package com.dyys.hr.oss.aliyun;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description
 * Create by yangye
 * Date 2021/12/22 10:05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AliyunOssResult {
    /**
     * 上传是否成功
     */
    private boolean success;

    /**
     * 上传的文件名（如果使用自定义文件路径，会返回完整的路径+文件名，例：cf/abc.png）
     */
    private String fileName;

    /**
     * 上传成功的返回url（带过期时间）
     */
    private String url;

    /**
     * 提示信息
     */
    private String msg;
}
