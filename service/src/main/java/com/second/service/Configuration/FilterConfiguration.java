package com.second.service.Configuration;

import com.second.service.Filter.SecondFilterChain;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class FilterConfiguration {
    @Bean
    public FilterRegistrationBean filterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new SecondFilterChain());
        List<String> urlList = new ArrayList<>();
        urlList.add("/second/*");
        registration.setUrlPatterns(urlList);
        registration.setName("UrlFilter");
        registration.setOrder(1);
        return registration;
    }
}
