package com.wedding.contrloller;

import com.wedding.domain.ResponseResult;
import com.wedding.domain.dto.PageDTO;
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
        return usersService.login(user);
    }
    /**
     * 退出登录接口
     * @return 实例对象
     */
    @ApiOperation("登录接口")
    @PostMapping("/logout")
    public ResponseResult logout(HttpServletRequest request) throws Exception {
        return usersService.logout(request);
    }

    /**
     * 获取用户信息接口
     * @return 实例对象
     */
    @ApiOperation("获取用户信息接口")
    @GetMapping("/userInfo/{id}")
    public ResponseResult getUserInfo(@PathVariable("id") Long id) {
        return usersService.getUserInfo(id);
    }



    /**
     * 修改用户信息接口
     * @return 实例对象
     */
    @ApiOperation("修改用户信息接口")
    @PostMapping("/update")
    public ResponseResult updateUserInfo(@RequestBody UserInfoVo userInfoObj) {
        return usersService.updateUserInfo(userInfoObj);
    }

    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    @ApiOperation("通过ID查询单条数据")
    @GetMapping("{roleid}")
    public ResponseResult queryById(Long userId){
        return usersService.queryById(userId);
    }

    /**
     * 分页查询
     *
     * @param queryParam 筛选条件
     * @return 查询结果
     */
    @ApiOperation("分页查询")
    @PostMapping("/page")
    public ResponseResult paginQuery(@RequestBody PageDTO<Users> queryParam){
        log.info("-----------进入studiosController.paginQuery方法-----------:{}",queryParam);
        return usersService.paginQuery(queryParam);
    }

    /**
     * 新增数据
     *
     * @param users 实例对象
     * @return 实例对象
     */
    @ApiOperation("新增数据")
    @PostMapping
    public ResponseResult add(Users users){
        return usersService.insert(users);
    }

    /**
     * 更新数据
     *
     * @param users 实例对象
     * @return 实例对象
     */
    @ApiOperation("更新数据")
    @PutMapping
    public ResponseResult edit(Users users){
        return usersService.update(users);
    }

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 是否成功
     */
    @ApiOperation("通过主键删除数据")
    @DeleteMapping
    public ResponseResult deleteById(Long userId){
        return usersService.deleteById(userId);
    }
}