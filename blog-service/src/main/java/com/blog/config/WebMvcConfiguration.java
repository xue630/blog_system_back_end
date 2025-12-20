package com.blog.config;

import com.blog.interceptor.AdminJwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebMvcConfiguration extends WebMvcConfigurationSupport {
    @Autowired
    AdminJwtInterceptor adminJwtInterceptor;

    @Override
    protected void addCorsMappings(CorsRegistry registry) {
        //上线前要修改一下跨域，生产环境使用nginx不需要配置跨域
        registry.addMapping("/**")
                .allowedOriginPatterns("*")  // 或者指定具体域名
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)  // 关键：允许凭证
                .maxAge(3600);
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminJwtInterceptor)
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/user/login");

    }
}
