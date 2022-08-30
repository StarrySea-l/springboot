package com.springboot.dome.config;

import com.springboot.dome.interceptor.PermissionCheckInterceptor;
import com.springboot.dome.shiro.ShiroConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author :王磊
 * @version :
 * @date :Created in 2021/12/15 15:16
 * @description: mvc相关配置
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Autowired
    PermissionCheckInterceptor permissionCheckInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {

    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
    }



}
