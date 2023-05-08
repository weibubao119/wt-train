package com.dyys.hr.config;

import cn.hutool.core.util.StrUtil;
import com.dagongma.redis.common.util.RedisUtil;
import com.dyys.hr.interceptor.LoginInterceptor;
import com.dyys.hr.properties.PermissionProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * MVC 配置信息
 *
 * @author ZHIQIANG LI
 * @date 2019/7/28 12:41
 **/
@Configuration
@Slf4j
public class HrWebConfig implements WebMvcConfigurer {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private PermissionProperties permission;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        String authc = permission.getAuthc();
        String anon = permission.getAnon();

        InterceptorRegistration registration = null;
        if (StrUtil.isNotBlank(authc)) {
            registration = registry.addInterceptor(loginInterceptor());
            registration.addPathPatterns(permission.getAuthc().split(","));
        }
        if (StrUtil.isNotBlank(anon) && registration != null) {
            registration.excludePathPatterns(permission.getAnon().split(","));
        }
    }

    @Bean
    public LoginInterceptor loginInterceptor() {
        return new LoginInterceptor(redisUtil);
    }
}
