package com.atguigu.serviceucenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Hello world!
 */
@MapperScan("com.atguigu.serviceucenter.mapper")
@ComponentScan(basePackages = {"com.atguigu"})
@SpringBootApplication
public class ServiceUcenterMainApp {
    public static void main(String[] args) {
        SpringApplication.run(ServiceUcenterMainApp.class);
    }
}
