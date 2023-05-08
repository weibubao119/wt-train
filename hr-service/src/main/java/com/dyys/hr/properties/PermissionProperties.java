package com.dyys.hr.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;

/**
 * @author ZHIQIANG LI
 * @date 2019/7/28 14:22
 **/
@Data
@ConfigurationProperties(prefix = "permission")
@Primary
public class PermissionProperties {
    /**
     * 需认证的地址，多个以逗号分隔
     */
    private String authc;
    /**
     * 无需认证的地址，多个以逗号分隔
     */
    private String anon;
}
