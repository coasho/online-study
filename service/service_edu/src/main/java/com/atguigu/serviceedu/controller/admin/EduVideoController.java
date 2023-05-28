package com.atguigu.serviceedu.controller.admin;


import com.atguigu.commonutils.R;
import com.atguigu.serviceedu.entity.EduVideo;
import com.atguigu.serviceedu.service.EduVideoService;
import com.atguigu.serviceedu.service.VodVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2023-04-18
 */
@RestController
@RequestMapping("/serviceedu/edu-video")
public class EduVideoController {
    @Autowired
    private EduVideoService eduVideoService;
    @Autowired
    private VodVideoService vodVideoService;

    @PostMapping("/addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo) {
        boolean saved = eduVideoService.save(eduVideo);
        if (saved) return R.ok();
        else return R.error();
    }

    @PostMapping("/updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo) {
        boolean b = eduVideoService.updateById(eduVideo);
        if (b) return R.ok();
        else return R.error();
    }

    @DeleteMapping("/deleteVideo/{videoId}")
    public R deleteVideoById(@PathVariable("videoId") String videoId) {
        R r = vodVideoService.removeVideo(eduVideoService.getById(videoId).getVideoSourceId());
        boolean b = eduVideoService.removeById(videoId);
        System.out.println("***************"+r.getSuccess());
        if (b && r.getSuccess()) return R.ok().message(r.getMessage());
        else return R.error();
    }
}

