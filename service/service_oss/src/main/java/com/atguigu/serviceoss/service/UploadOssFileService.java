package com.atguigu.serviceoss.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadOssFileService {
    String uploadFile(String host,MultipartFile file);
}
