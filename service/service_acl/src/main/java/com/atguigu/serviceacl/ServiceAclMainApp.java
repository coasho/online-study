package com.atguigu.serviceacl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.atguigu")
@MapperScan("com.atguigu.serviceacl.mapper")
public class ServiceAclMainApp {

    public static void main(String[] args) {
        SpringApplication.run(ServiceAclMainApp.class, args);
    }

}
