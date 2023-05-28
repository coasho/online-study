package com.atguigu.servicecms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * Hello world!
 */
@EnableDiscoveryClient
@MapperScan("com.atguigu.servicecms.mapper")
@ComponentScan({"com.atguigu"})
@SpringBootApplication
public class ServiceCMSMainApp {
    public static void main(String[] args) {
        SpringApplication.run(ServiceCMSMainApp.class);
    }
}
