package com.second.service.Filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @WebFilter注解为声明此类为filter 第一个参数为该filter起一个名字，第二个参数为说明要拦截的请求地址。 该类实现了Filter接口，里面有三个方法，分别为过滤器创建、过滤方法和过滤器销毁。我们在过滤方法doFilter执行过滤逻辑。
 * 同样，在使用注解的方式（即@WebFilter）声明过滤器时，需要再main函数类上添加@ServletComponentScan（basePackages = "此处写明类地址，格式为包名+类名（如com.yxc.*）"）
 */
@WebFilter(filterName = "test", urlPatterns = "/second/*")
@Component
public class SecondFilterChain implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("---->Beginning to start the filter");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String requestURI = httpServletRequest.getRequestURI();
        System.out.println("---->过滤器请求地址：" + requestURI);
        if (requestURI.contains("/second/test")) {
            request.getRequestDispatcher("/second/failed").forward(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        System.out.println("----> The filter has been destroyed");
    }
}
