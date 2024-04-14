package com.wedding.config.sms.config;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wedding.config.sms.service.SmsService;
import com.wedding.config.sms.vo.MobilecodeAuthenticationToken;
import com.wedding.constants.SysConstants;
import com.wedding.domain.ResponseResult;
import com.wedding.domain.entity.*;
import com.wedding.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhuqi
 * @Date: 2024/03/25/20:36
 * @Description:
 */
@Component
@Slf4j
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private SmsService smsService;
    @Autowired
    private UsersService usersService;
    @Autowired
    private MenusService menusService;
    @Autowired
    private UserRolesService userRolesService;
    @Autowired
    private RoleMenusService roleMenusService;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private RolesService rolesService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        MobilecodeAuthenticationToken mobilecodeAuthenticationToken = (MobilecodeAuthenticationToken) authentication;
        String phone = mobilecodeAuthenticationToken.getPhone();
        String code = mobilecodeAuthenticationToken.getMobileCode();
        log.info("登陆手机号：" + phone);
        log.info("手机验证码：" + code);
        // 根据手机号和验证码进行认证
        ResponseResult isValid = smsService.verifySmsCode(phone, code);
        log.info("验证结果：" + isValid.getMsg()+isValid.getCode());
        if (isValid.getCode() != 99) {
            throw new BadCredentialsException("手机号或验证码错误");
        }
        // 查询用户信息
        LoginUser loginUser = storageUserInfos(phone);

        // 创建一个新的Authentication对象，传递自定义用户信息
        LoginUser loadedUser = (LoginUser) userDetailsService.loadUserByUsername(loginUser.getUsername());

        if (Objects.isNull(loadedUser)) {
            throw new BadCredentialsException("用户不存在！");
        }else {
           MobilecodeAuthenticationToken res = new MobilecodeAuthenticationToken(loginUser, null, loadedUser.getAuthorities());
            return res;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return MobilecodeAuthenticationToken.class.isAssignableFrom(authentication);
    }
    private LoginUser storageUserInfos(String phone) throws UsernameNotFoundException {

        Users user = getUserByrPhoneNumber(phone);
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
        String roleKey = rolesService.getById(one.getRoleId()).getRoleKey();
        user.setUserRole(roleKey);
        LambdaQueryWrapper<RoleMenus> wrapper2 = new LambdaQueryWrapper<>();
        wrapper2.eq(RoleMenus::getRoleId, one.getRoleId());
        Long menuId = roleMenusService.getOne(wrapper2).getMenuId();
        List<Menus> menus = menusService.list(new LambdaQueryWrapper<Menus>().eq(Menus::getMenuId, menuId));
        menus.forEach(menu -> {
            permissions.add(menu.getPerms());
        });
        return new LoginUser(user, permissions,roleKey);
    }


    private Users getUserByrPhoneNumber(String phone) {
        LambdaQueryWrapper<Users> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Users::getPhoneNumber, phone);
        wrapper.eq(Users::getStatus, SysConstants.USER_STATUS_NORMAL);
        return usersService.getOne(wrapper);
    }
}