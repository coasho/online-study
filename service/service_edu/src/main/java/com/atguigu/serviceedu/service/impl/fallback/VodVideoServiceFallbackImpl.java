package com.atguigu.serviceedu.service.impl.fallback;

import com.atguigu.commonutils.R;
import com.atguigu.serviceedu.service.VodVideoService;
import org.springframework.stereotype.Component;

@Component
public class VodVideoServiceFallbackImpl implements VodVideoService {
    public R removeVideo(String videoId) {
        System.out.println("###############################################");
        return R.ok().message("Vod服务维护中");
    }
}
