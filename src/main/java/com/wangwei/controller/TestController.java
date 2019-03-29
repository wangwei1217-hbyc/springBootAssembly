package com.wangwei.controller;

import com.xiaoma.util.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by wangwei on 2019/3/28.
 */
@RestController
@RequestMapping("test")
public class TestController {
    @Resource
    private RedisTemplate redisTemplate;

    @GetMapping("index")
    public Object index(){
        System.out.println(redisTemplate);
        return "success";
    }
}
