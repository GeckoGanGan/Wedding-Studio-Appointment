package com.wedding.controller;

import com.wedding.domain.ResponseResult;
import com.wedding.service.UsersService;
import com.wedding.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhuqi
 * @Date: 2024/03/31/16:16
 * @Description: websocket控制类
 */
@RestController
@RequestMapping("/myServer")
@Slf4j
public class MyServerController {
    @Autowired
    private UsersService usersService;


    // 获取在线客服列表
    @GetMapping("/online/{studioId}")
    public ResponseResult getOnlineUsers(@PathVariable("studioId") Long studioId){
        log.info("-------------------------------------------当前登录用户：{}",SecurityUtils.getLoginUser().getUser());
        return usersService.getOnlineUser(studioId);
    }
}
