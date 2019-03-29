package com.wangwei.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wangwei on 2019/3/28.
 */
@RestController
@RequestMapping("test")
public class TestController {

    @GetMapping("index")
    public Object index(){
        return "success";
    }
}
