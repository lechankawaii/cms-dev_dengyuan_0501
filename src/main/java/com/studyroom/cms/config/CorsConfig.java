package com.studyroom.cms.config;


import com.studyroom.cms.intecepter.AdminLoginCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

public class CorsConfig implements WebMvcConfigurer {

    @Autowired
    AdminLoginCheck adminLoginCheck;

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET","HEAD","POST","PUT","DELETE","OPTIONS")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*");
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        List<String> excludeURL_admin = new ArrayList<>();

        //选定不需要拦截的路径

        /*
         admin login check
         */
        excludeURL_admin.add("/api/login");

        registry.addInterceptor(adminLoginCheck).addPathPatterns("/api/**").excludePathPatterns(excludeURL_admin);
        WebMvcConfigurer.super.addInterceptors(registry);


    }
}
