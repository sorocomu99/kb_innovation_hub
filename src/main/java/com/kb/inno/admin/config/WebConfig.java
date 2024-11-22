/**
 * 파일명     : WebConfig.java
 * 화면명     : 없음
 * 설명       : 관리자 화면 접속시 URL 패턴 추가
 * 최초개발일 : 2024.10.23
 * 최초개발자 : 이훈희
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 *
 */
package com.kb.inno.admin.config;

import com.kb.inno.admin.interceptor.Interceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
/*
    @Bean
    public Interceptor authInterceptor(){
        return new Interceptor();
    }
*/

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        Interceptor interceptor = new Interceptor();

        registry.addInterceptor(interceptor)
                .addPathPatterns("/popup/**", "/member/**", "/visual/**")
                .excludePathPatterns("/css/**", "/images/**", "/js/**", "/enov/component/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/summernoteimages/**" , "/upload/**")
                .addResourceLocations("file:///D:/upload/");
    }
}
