package com.wangwei.controller;

import com.xiaoma.framework.cache.manager.RedisCacheManager;
import com.xiaoma.util.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by wangwei on 2019/3/28.
 */
@RestController
@RequestMapping("test")
public class TestController {
//    @Resource
//    private RedisTemplate redisTemplate;
//    @Resource
//    private RedisCacheManager redisCacheManager;

//    @GetMapping("index")
//    public Object index(){
//        System.out.println(redisTemplate);
//        redisTemplate.setnx("token", UUID.randomUUID().toString().replaceAll("-",""));
//        System.out.println(redisTemplate.get("token"));
//        return "success:"+redisTemplate.get("token");
//    }

//    @GetMapping("home")
//    public Object home(){
//        System.out.println(redisCacheManager);
//        //过期时间 秒为单位
//        redisCacheManager.set("token",60, UUID.randomUUID().toString().replaceAll("-",""));
////        redisCacheManager.setnx("token", UUID.randomUUID().toString().replaceAll("-",""));
//        System.out.println(redisCacheManager.get("token"));
//        return "success:"+redisCacheManager.get("token");
//    }

    @GetMapping("print")
    public Object print(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return "当前时间："+ sdf.format(new Date());
    }
}
