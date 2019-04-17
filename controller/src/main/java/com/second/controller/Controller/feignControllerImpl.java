package com.second.controller.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
public class feignControllerImpl {
    @Autowired
    feignController feignController;

    @GetMapping
    public List<Object> findAll(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        List<Object> list = new ArrayList<>();
//        list.add(String.valueOf(httpServletResponse));
//        list.add(httpServletRequest);
        list.add(feignController.findAll());
        return list;
    }
}
