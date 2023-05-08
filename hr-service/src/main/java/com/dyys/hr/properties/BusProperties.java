package com.dyys.hr.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;

/**
 * 大冶有色系统相关配置信息
 *
 * @author ZHIQIANG LI
 * @date 2019/7/20 14:38
 **/
@Data
@ConfigurationProperties(prefix = "dyys-hr")
@Primary
public class BusProperties {

    /**
     * 组织根id
     */
    private String organizationRootId;

    /**
     * 内网web发布地址
     */
    private String nginxWebUrl;
    /**
     * 外网链接
     */
    private String networkUrl;
    /**
     * 外网职位链接
     */
    private String positionPage;
    /**
     * 外网更新简历链接
     */
    private String updateResumePage;
    /**
     * 附件地址
     */
    private String filePath;
    /**
     * nginx 附件地址
     */
    private String nginxFileUrl;

    /**
     * 开放调查-二维码URL
     */
    private String questionnaireUrl;

    /**
     * 签到二维码跳转页面
     */
    private String signupUrl;

    /**
     * 用户图片服务器地址
     */
    private String pictureUrl;

    /**
     * EBS菜单接口参数
     */
    private MenuProperties menu;

    /**
     * EBS角色
     */
    private RoleProperties role;

    @Data
    public static class MenuProperties{
        /**
         * token
         */
        private  String usernameToken;
        /**
         * username
         */
        private String username;
        /**
         * password
         */
        private String password;
        /**
         * clientCode
         */
        private String clientCode;

    }
    @Data
    public static class RoleProperties{
        /**
         * 培训超管
         */
        private String trainSuperAdmin;
        /**
         * 培训专员
         */
        private String trainer;
        /**
         * 招聘超管
         */
        private String recruitSuperAdmin;
        /**
         * 招聘专员
         */
        private String recruiter;
    }

}
