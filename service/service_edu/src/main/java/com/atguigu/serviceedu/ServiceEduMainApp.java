package com.atguigu.serviceedu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("com.atguigu.serviceedu.mapper")
@ComponentScan(basePackages = {"com.atguigu"})
@SpringBootApplication
public class ServiceEduMainApp {
    public static void main(String[] args) {
        SpringApplication.run(ServiceEduMainApp.class);
    }
}
