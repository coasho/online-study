package com.atguigu.serviceedu.service;

import com.atguigu.commonutils.R;
import com.atguigu.serviceedu.service.impl.fallback.VodVideoServiceFallbackImpl;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
@Component
@FeignClient(value = "service-vod",fallback = VodVideoServiceFallbackImpl.class )
public interface VodVideoService {
    @DeleteMapping("/eduvod/video/delete/{videoId}")
    R removeVideo(@ApiParam(name = "videoId", value = "云端视频id", required = true)
                         @PathVariable("videoId") String videoId);
}
