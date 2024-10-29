package com.kb.inno.admin.config;

import com.kb.inno.admin.interceptor.Interceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
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
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/css/**", "/images/**", "/js/**, /enov/component/**");
    }
}
