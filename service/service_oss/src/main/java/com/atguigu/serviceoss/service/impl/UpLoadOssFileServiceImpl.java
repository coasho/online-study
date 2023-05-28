package com.atguigu.serviceoss.service.impl;

import cn.hutool.core.util.IdUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.serviceoss.service.UploadOssFileService;
import com.atguigu.serviceoss.utils.ConstantPropertiesUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class UpLoadOssFileServiceImpl implements UploadOssFileService {
    public String uploadFile(String host,MultipartFile file) {
        String endpoint = ConstantPropertiesUtils.END_POINT;
        String accessKeyId = ConstantPropertiesUtils.KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        String filename = file.getOriginalFilename();
        String fileDir = host+"/" + IdUtil.fastSimpleUUID() +"."+ filename.substring(filename.lastIndexOf(".") + 1);
        InputStream inputStream;
        try {
            inputStream = file.getInputStream();
            ossClient.putObject(bucketName, fileDir, inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ossClient.shutdown();
        return "https://" + bucketName + "." + endpoint + "/" + fileDir;
    }
}
