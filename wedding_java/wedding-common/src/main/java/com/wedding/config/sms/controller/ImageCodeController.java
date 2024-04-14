package com.wedding.config.sms.controller;


import com.wedding.config.sms.service.ImageCodeService;
import com.wedding.domain.ResponseResult;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhuqi
 * @Date: 2023/12/05/10:16
 * @Description: 图形验证码接口
 */
@RestController
@RequestMapping("/image")
@Slf4j
public class ImageCodeController {
    @Resource
    private ImageCodeService imageCodeService;

    // 生成验证码
    @GetMapping("/imageCode")
    @ApiOperation("生成图片验证码")
    public ResponseResult getImageCode(HttpServletRequest request, HttpServletResponse response, @RequestParam("key") String key){
        return imageCodeService.getImageCode(request, response,key);
    }
    // 图形验证码校验
    @GetMapping("/imageCode/check/{imageCode}")
    @ApiOperation("校验图片验证码")
        public ResponseResult imageCodeCheck(HttpServletRequest request, @PathVariable String imageCode){
        log.info("imageCode---->{}",imageCode);
        return imageCodeService.imageCodeCheck(request, imageCode);
    }

}
