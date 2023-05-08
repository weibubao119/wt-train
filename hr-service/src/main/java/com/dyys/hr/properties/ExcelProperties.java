package com.dyys.hr.properties;

import com.dagongma.kernel.config.YamlConfigFactory;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Excel导出配置
 *
 * @author ZHIQIANG LI
 * @date 2019/9/8 0:53
 **/

@Data
@Component
@PropertySource(value = "excel.yml", factory = YamlConfigFactory.class)
@ConfigurationProperties(prefix = "excel")
@Primary
public class ExcelProperties {

    private Map<String, ExcelFileProperties> type = new LinkedHashMap();

    @Data
    public static class ExcelFileProperties {
        /**
         * 导出的字段名称
         */
        private String text;
        /**
         * 导出的字段
         */
        private String value;
        /**
         * 导出的文件名称
         */
        private String title;

        /**
         * excel sheet name
         */
        private String sheetName;
    }
}
