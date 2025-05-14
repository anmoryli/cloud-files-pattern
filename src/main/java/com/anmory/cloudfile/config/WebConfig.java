package com.anmory.cloudfile.config;

/**
 * @author Anmory
 * @description TODO
 * @date 2025-05-14 下午7:29
 */

import com.anmory.cloudfile.service.RememberMeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@Configuration
public class WebConfig {

    @Autowired
    private RememberMeFilter rememberMeFilter;

    @Bean
    public FilterRegistrationBean<RememberMeFilter> rememberMeFilterRegistration() {
        FilterRegistrationBean<RememberMeFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(rememberMeFilter);
        registration.addUrlPatterns("/*"); // 应用到所有请求
        registration.setName("RememberMeFilter");
        registration.setOrder(1); // 设置执行顺序
        return registration;
    }

    @Bean
    public FilterRegistrationBean<CharacterEncodingFilter> appCharacterEncodingFilter() {
        FilterRegistrationBean<CharacterEncodingFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new CharacterEncodingFilter("UTF-8", true));
        bean.addUrlPatterns("/*");
        return bean;
    }

}

