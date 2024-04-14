package com.wedding.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wedding.constants.SysConstants;
import com.wedding.domain.entity.*;
import com.wedding.mapper.UsersMapper;
import com.wedding.service.MenusService;
import com.wedding.service.RoleMenusService;
import com.wedding.service.RolesService;
import com.wedding.service.UserRolesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhuqi
 * @Date: 2024/03/09/16:53
 * @Description:
 */
@Component
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private UserRolesService userRolesService;
    @Autowired
    private RoleMenusService roleMenusService;
    @Autowired
    private MenusService menusService;
    @Autowired
    private RolesService rolesService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        log.info("登录用户：{}", userName);
        // 根据用户名或手机号查询用户信息
        Users user = getUserByUserNameOrPhoneNumber(userName);
        log.info("查询到用户：{}", user.toString());
        // 判断用户是否存在
        if (Objects.isNull(user)) {
            throw new RuntimeException("用户不存在！");
        }
        List<String> permissions = new ArrayList<>();
        // 判断是不是管理员
        if (user.getId() == 1L) {
            // 说明是超级管理员，返回全部权限
            menusService.list().forEach(menus -> {
                permissions.add(menus.getPerms());
            });
            user.setUserRole("admin");
            return new LoginUser(user, permissions,"admin");
        }
        // 不是超级管理员，查询用户角色
        LambdaQueryWrapper<UserRoles> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.eq(UserRoles::getUserId, user.getId());
        UserRoles one = userRolesService.getOne(wrapper1);
        Roles role = rolesService.getById(one.getRoleId());
        log.info("----------UserRoles角色信息：{}-----------",one);
        LambdaQueryWrapper<RoleMenus> wrapper2 = new LambdaQueryWrapper<>();
        wrapper2.eq(RoleMenus::getRoleId, one.getRoleId());
        Long menuId = roleMenusService.getOne(wrapper2).getMenuId();
        log.info("------------menuId：{}--------",menuId);
        List<Menus> menus = menusService.list(new LambdaQueryWrapper<Menus>().eq(Menus::getMenuId, menuId));
        log.info("------------menus：{}--------",menus);
        menus.forEach(menu -> {
            permissions.add(menu.getPerms());
        });
        user.setUserRole(role.getRoleKey());
        return new LoginUser(user, permissions,role.getRoleKey());
    }
    private Users getUserByUserNameOrPhoneNumber(String username) {
        LambdaQueryWrapper<Users> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Users::getUsername, username);
        wrapper.eq(Users::getStatus, SysConstants.USER_STATUS_NORMAL);
        return usersMapper.selectOne(wrapper);
    }
}
