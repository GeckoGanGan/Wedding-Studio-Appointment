package com.wedding.controller;

import com.wedding.domain.ResponseResult;
import com.wedding.domain.entity.Users;
import com.wedding.domain.vo.UserInfoVo;
import com.wedding.service.UsersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户表;(users)表控制层
 *
 * @author : zhudake
 * @date : 2024-3-9
 */
@Api(tags = "用户表对象功能接口")
@RestController
@RequestMapping("/users")
@Slf4j
public class UsersController {
    @Autowired
    private UsersService usersService;

    /**
     * 登录接口
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @ApiOperation("登录接口")
    @PostMapping("/login")
    public ResponseResult login(@RequestBody Users user) {
        log.info("-----user:{}",user.toString());
        return usersService.login(user);
    }

    /**
     * 注册接口
     * @return 实例对象
     */
    @ApiOperation("注册接口")
    @PostMapping("/register")
    public ResponseResult register(@RequestBody Users user) {
        log.info("--------------------组测用户--------：{}",user);
        return usersService.register(user);
    }

    /**
     * 退出登录接口
     * @return 实例对象
     */
    @ApiOperation("登录接口")
    @PostMapping("/logout")
    public ResponseResult logout(HttpServletRequest request) throws Exception {
        log.info("--------------------退出登录--------");
        return usersService.logout(request);
    }

    /**
     * 获取用户信息接口
     * @return 实例对象
     */
    @ApiOperation("获取用户信息接口")
    @GetMapping("/userInfo/{id}")
    public ResponseResult getUserInfo(@PathVariable("id") Long id) {
        log.info("--------------------修改用户信息--------");
        return usersService.getUserInfo(id);
    }



    /**
     * 修改用户信息接口
     * @return 实例对象
     */
    @ApiOperation("修改用户信息接口")
    @PostMapping("/update")
    public ResponseResult updateUserInfo(@RequestBody UserInfoVo userInfoObj) {
        log.info("--------------------修改用户信息-----：{}---",userInfoObj.toString());
        return usersService.updateUserInfo(userInfoObj);
    }



}