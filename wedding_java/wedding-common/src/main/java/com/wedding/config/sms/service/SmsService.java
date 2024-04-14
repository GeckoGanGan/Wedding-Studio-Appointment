package com.wedding.config.sms.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wedding.config.sms.constant.SmsConstant;
import com.wedding.constants.SysConstants;
import com.wedding.domain.ResponseResult;
import com.wedding.domain.entity.LoginUser;
import com.wedding.domain.entity.Users;
import com.wedding.domain.enums.AppHttpCodeEnum;
import com.wedding.domain.vo.UserInfoVo;
import com.wedding.domain.vo.WeddingLoginUser;
import com.wedding.service.UsersService;
import com.wedding.utils.BeanCopyUtils;
import com.wedding.utils.JwtUtil;
import com.wedding.utils.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhuqi
 * @Date: 2024/03/25/20:36
 * @Description:
 */
@Service
@Slf4j
public class SmsService {
    @Autowired
    private UsersService usersService;
    @Autowired
    private RedisCache redisCache;

    public ResponseResult verifySmsCode(String phone, String code) {
        LambdaQueryWrapper<Users> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Users::getPhoneNumber, phone);
        Users user = usersService.getOne(wrapper);
        if (Objects.nonNull(user)) {
            //如果认证通过，判断验证码是否正确
            String smsCode = redisCache.getCacheObject(SmsConstant.SMS_CODE + phone);
            // 获取过期时间
            long expire = redisCache.getExpire(SmsConstant.SMS_CODE + phone);
            if (expire < 0) {
                return ResponseResult.errorResult(98,"验证码过期！");
            }
            if (smsCode.equals(code)) {
               return ResponseResult.okResult(99,phone);
            } else {
                 return ResponseResult.errorResult(100,"验证码错误！");
            }
        }else {
             return ResponseResult.errorResult(98,"没有查询到该用户！");
        }
    }
}
