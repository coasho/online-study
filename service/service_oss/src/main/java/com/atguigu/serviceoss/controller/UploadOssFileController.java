package com.atguigu.serviceoss.controller;

import com.atguigu.commonutils.R;
import com.atguigu.serviceoss.service.UploadOssFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduoss/fileoss")
public class UploadOssFileController {
    @Autowired
    private UploadOssFileService uploadService;

    @PostMapping("/upload")
    public R uploadOssFile(@RequestParam("host") String host, MultipartFile file) {
        String url = uploadService.uploadFile(host,file);
        return R.ok().data("url", url);
    }
}
