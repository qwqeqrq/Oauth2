package com.second.controller.Controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "FERTILIZATIONMATHSERVICE",fallback = feignFallback.class)
public interface feignController {

    @GetMapping( value = "/test")
    public String findAll();

}
