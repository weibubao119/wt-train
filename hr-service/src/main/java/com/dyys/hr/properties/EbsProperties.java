package com.dyys.hr.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;

/**
 * ebs相关配置
 *
 * @author JUCHUAN LI
 * @date 2019/7/31 23:50
 **/
@Data
@ConfigurationProperties(prefix = "ebs")
@Primary
public class EbsProperties {

    /**
     * 请求ebs的webservice接口地址
     */
    private String postUrl;

    /**
     * 请求ebs的soapAction地址
     */
    private String soapAction;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;
}
