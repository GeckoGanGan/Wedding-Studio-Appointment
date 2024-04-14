package com.wedding.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wedding.domain.ResponseResult;
import com.wedding.domain.dto.PageDTO;
import com.wedding.domain.entity.Users;
import com.wedding.domain.vo.UserInfoVo;

import javax.servlet.http.HttpServletRequest;


/**
 * 用户表(Users)表服务接口
 *
 * @author zhudake
 * @since 2024-03-01 19:52:25
 */
public interface UsersService extends IService<Users> {

    /**
     * 注册用户
     * @param user
     * @return
     */
    ResponseResult register(Users user);

    /**
     * 登录接口
     * @param user 登录对象
     * @return
     */
    ResponseResult login(Users user);

    /**
     * 退出登录接口
     * @param request
     * @return
     * @throws Exception
     */
    ResponseResult logout(HttpServletRequest request) throws Exception;

    /**
     * 修改用户信息接口
     * @param userInfoVo
     * @return
     */

    ResponseResult updateUserInfo(UserInfoVo userInfoVo);

    /**
     * 获取用户信息接口
     * @param id
     * @return
     */

    ResponseResult getUserInfo(Long id);

    /**
     * 获取在线用户
     * @param studioId
     * @return
     */

    ResponseResult getOnlineUser(Long studioId);


    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ResponseResult queryById(Long id);

    /**
     * 分页查询
     *
     * @param pageDTO 筛选条件
     * @return
     */
    ResponseResult paginQuery(PageDTO<Users> pageDTO);
    /**
     * 新增数据
     *
     * @param users 实例对象
     * @return 实例对象
     */
    ResponseResult insert(Users users);
    /**
     * 更新数据
     *
     * @param users 实例对象
     * @return 实例对象
     */
    ResponseResult update(Users users);
    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    ResponseResult deleteById(Long id);
}

