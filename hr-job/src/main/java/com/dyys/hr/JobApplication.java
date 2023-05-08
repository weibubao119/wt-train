package com.dyys.hr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author ZHIQIANG LI
 * @date 2019/6/1 1:25
 **/
@SpringBootApplication
@MapperScan(basePackages = {"com.dyys.hr.dao.*"})
@EnableTransactionManagement
@EnableScheduling
public class JobApplication {

    public static void main(String[] args) {
        /**
         * 解决spring boot 升级至2.0 后  es报错：IllegalStateException: availableProcessors is already set to [8], rejecting
         */
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication.run(JobApplication.class, args);
    }
}
