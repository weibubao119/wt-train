package com.dyys.hr.properties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author ZHIQIANG LI
 * @date 2019/7/31 23:50
 **/
@Configuration
@EnableConfigurationProperties(
        {
                BusProperties.class,
                PermissionProperties.class,
                BpmProperties.class,
                ExcelProperties.class,
                EbsProperties.class
        }
)
public class PropertiesConfig {
}
