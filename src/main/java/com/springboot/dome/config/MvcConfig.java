package com.springboot.dome.config;

import com.springboot.dome.interceptor.PermissionCheckInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
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
        InterceptorRegistration interceptor = registry.addInterceptor(permissionCheckInterceptor);
        interceptor
                .addPathPatterns("/**")
                .excludePathPatterns("/login", "/getImage");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 允许跨域访问的路径
        registry.addMapping("/**")
                // 允许头部设置
                .allowedHeaders("*")
                // 允许请求方法
                .allowedMethods("*")
                // 预检间隔时间
                .maxAge(1800)
                // 允许跨域访问的源
                .allowedOrigins("http://localhost:8989","http://localhost:9090")
                // 是否发送cookie
                .allowCredentials(true);
    }
}
