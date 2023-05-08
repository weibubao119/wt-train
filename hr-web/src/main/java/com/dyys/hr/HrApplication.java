package com.dyys.hr;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author ZHIQIANG LI
 * @date 2019/6/1 1:25
 **/
@SpringBootApplication
@MapperScan(basePackages = {"com.dyys.hr.dao.*"})
@EnableTransactionManagement
@EnableSwagger2Doc
@EnableScheduling
public class HrApplication {

        public static void main(String[] args) {



        /**
         * 解决spring boot 升级至2.0 后  es报错：IllegalStateException: availableProcessors is already set to [8], rejecting
         */
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication.run(HrApplication.class, args);
    }
}
