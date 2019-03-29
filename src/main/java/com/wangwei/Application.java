package com.wangwei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by wangwei on 2019/3/28.
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.wangwei"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
