package com.wedding.controller;

import com.wedding.domain.ResponseResult;
import com.wedding.service.impl.UploadServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhuqi
 * @Date: 2024/03/17/13:20
 * @Description:
 */
@RestController
public class FileController {
    @Autowired
    private UploadServiceImpl uploadService;
    @PostMapping("/upload")
    @ApiOperation("文件上传")
    public ResponseResult uploadImg(MultipartFile img){
        return uploadService.uploadImg(img);
    }
}
