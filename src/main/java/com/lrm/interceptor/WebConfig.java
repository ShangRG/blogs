package com.lrm.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 配置类
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    /**
     * addInterceptors;  重写这个方法，，
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/admin/**")  //对这个路径进行拦截
                .excludePathPatterns("/admin")   //放行这个路径  因为这个路径是登陆
                .excludePathPatterns("/admin/login");   //放行
    }


}
