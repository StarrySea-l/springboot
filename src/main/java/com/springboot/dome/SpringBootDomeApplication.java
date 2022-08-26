package com.springboot.dome;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author :王磊
 * @version :
 * @date :Created in 2021/12/15 10:41
 * @description: springBoot 启动类
 */
@EnableCaching
@SpringBootApplication
@MapperScan("com.springboot.dome.mapper")
public class SpringBootDomeApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootDomeApplication.class,args);
    }
}
