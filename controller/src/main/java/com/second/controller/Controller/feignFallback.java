package com.second.controller.Controller;

public class feignFallback implements feignController {
    @Override
    public String findAll() {
        return "request error!!";
    }
}
