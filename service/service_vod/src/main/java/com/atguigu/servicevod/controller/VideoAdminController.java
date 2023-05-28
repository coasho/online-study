package com.atguigu.servicevod.controller;

import com.atguigu.commonutils.R;
import com.atguigu.servicevod.service.VideoService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduvod/video")
public class VideoAdminController {
    @Autowired
    private VideoService videoService;

    @PostMapping("/upload")
    public R uploadVideo(
            @ApiParam(name = "file", value = "文件", required = true)
            @RequestParam("file") MultipartFile file) throws Exception {
        String[] videoInfo = videoService.uploadVideo(file);
        return R.ok().message("视频上传成功").data("videoId", videoInfo[0]).data("title", videoInfo[1]);
    }

    @DeleteMapping("/delete/{videoId}")
    public R removeVideo(@ApiParam(name = "videoId", value = "云端视频id", required = true)
                         @PathVariable("videoId") String videoId) {
        videoService.removeVideo(videoId);
        return R.ok().message("视频删除成功");
    }

}