package com.dyys.hr.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * bpm相关配置
 *
 * @author ZHIQIANG LI
 * @date 2019/7/31 23:50
 **/
@Data
@ConfigurationProperties(prefix = "bpm")
@Primary
public class BpmProperties {

    /**
     *  host
     */
    private String wsdlHost;
    /**
     * 前端嵌套url
     */
    private String webUrl;
    /**
     *  系统注册名
     */
    private String sysId;
    /**
     *  系统注册密码
     */
    private String sysPw;
    /**
     *  流程配置信息
     */
    private Map<String, ProcessProperties> process = new LinkedHashMap();
    /**
     * BPM接口方法
     */
    private MethodProperties method;

    @Data
    public static class MethodProperties{
        /**
         * 运行流程服务
         */
        private String runProcess;
        /**
         * 打开流程服务
         */
        private String openProcess;
        /**
         * 公文流转规则
         */
        private String runRule;
    }

    @Data
    public static class ProcessProperties {

        /**
         * 下一节点ID
         */
        private String defaultNodeId;
        /**
         * 流程ID
         */
        private String processId;
        /**
         * 是否要更新业务状态
         */
        private Boolean isUpdateStatus;
        /**
         * 流程申请表单页面
         */
        private String formUrl;
        /**
         * 表单宽度
         */
        private Integer formWidth;
        /**
         * 表单高度
         */
        private Integer formHeight;
        /**
         * 部门节点
         */
        private Map<String,String> nodeIds = new LinkedHashMap<>();
    }
}
