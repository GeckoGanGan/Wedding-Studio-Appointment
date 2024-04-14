package com.wedding.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.wedding.config.FileConfig;
import com.wedding.domain.ResponseResult;
import com.wedding.domain.enums.AppHttpCodeEnum;
import com.wedding.exception.SystemException;
import com.wedding.utils.PathUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Service
@Data
@Slf4j
public class UploadServiceImpl{
    @Autowired
    private FileConfig fileConfig;
    private String accessKey;
    private String secretKey;
    private String bucket;



    public ResponseResult uploadImg(MultipartFile img) {
       log.info("------------------进入上传图片接口---------");
       log.info("----fileConfig----"+fileConfig);

        // 获取原始文件名称
        String originalFilename = img.getOriginalFilename();
        // 对原始文件类型判断
        if (StringUtils.isNotBlank(originalFilename)){
            if (!originalFilename.endsWith(".png") && !originalFilename.endsWith(".jpg")) {
                // 这里应该响应给前端具体的错误提示
                throw new SystemException(AppHttpCodeEnum.FILE_TYPE_ERROR);
            }
        }
        // 如果判断成功就上传到oss
        // oss中的文件名
        String filePath = PathUtils.generateFilePath(originalFilename);
        String url = uploadOss(img, filePath, originalFilename);
        return ResponseResult.okResult(url);
    }
    private String uploadOss(MultipartFile image,String filePath,String originFileName){
        // 配置属性
        String endPoint = fileConfig.getEndpoint();
        String accessKeyId = fileConfig.getKeyid();
        String secret = fileConfig.getKeysecret();
        String bucketName = fileConfig.getBucketname();

        // 创建ossClient客户端
        OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, secret);

        // 调用oss方法实现长传
        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(getContentType(originFileName.substring(originFileName.lastIndexOf("."))));
            ossClient.putObject(bucketName, filePath,new ByteArrayInputStream(image.getBytes()),objectMetadata);
            // 关闭客户端连接
            ossClient.shutdown();
            // 设置图片有效时间
            Instant now = Instant.now();
            Instant expireTime = now.plus(Duration.ofDays(365*20)); // 20年后过期
            String imageUrl = ossClient.generatePresignedUrl(bucketName, filePath, Date.from(expireTime)).toString();

            String previewUrl = null;
            if (StringUtils.isNotBlank(imageUrl)){
                previewUrl = fileConfig.getPreviewurl()+filePath;
            }
            return previewUrl;
        } catch (IOException e) {
            e.printStackTrace();
            return "上传出错了";
        }
    }

    // 实现图片的预览功能
    public static String getContentType(String FilenameExtension) {
        if (FilenameExtension.equalsIgnoreCase(".bmp")) {
            return "image/bmp";
        }
        if (FilenameExtension.equalsIgnoreCase(".gif")) {
            return "image/gif";
        }
        if (FilenameExtension.equalsIgnoreCase(".jpeg") ||
                FilenameExtension.equalsIgnoreCase(".jpg") ||
                FilenameExtension.equalsIgnoreCase(".png")) {
            return "image/jpg";
        }
        if (FilenameExtension.equalsIgnoreCase(".html")) {
            return "text/html";
        }
        if (FilenameExtension.equalsIgnoreCase(".txt")) {
            return "text/plain";
        }
        if (FilenameExtension.equalsIgnoreCase(".vsd")) {
            return "application/vnd.visio";
        }
        if (FilenameExtension.equalsIgnoreCase(".pptx") ||
                FilenameExtension.equalsIgnoreCase(".ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (FilenameExtension.equalsIgnoreCase(".docx") ||
                FilenameExtension.equalsIgnoreCase(".doc")) {
            return "application/msword";
        }
        if (FilenameExtension.equalsIgnoreCase(".xml")) {
            return "text/xml";
        }
        return "image/jpg";
    }
}
