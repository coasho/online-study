package com.atguigu.serviceorder.service;

import com.atguigu.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-ucenter")
public interface UcenterClient {
    //根据课程id查询课程信息
    @GetMapping("/eduucenter/getMemberInfo/{memberId}")
    R getInfo(@PathVariable("memberId") String memberId);
}