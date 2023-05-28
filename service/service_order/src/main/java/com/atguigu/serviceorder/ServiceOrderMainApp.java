package com.atguigu.serviceorder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * Hello world!
 */

@EnableFeignClients
@EnableDiscoveryClient
@MapperScan("com.atguigu.serviceorder.mapper")
@ComponentScan(basePackages = {"com.atguigu"})
@SpringBootApplication
public class ServiceOrderMainApp {
    public static void main(String[] args) {
        SpringApplication.run(ServiceOrderMainApp.class);
    }
}
