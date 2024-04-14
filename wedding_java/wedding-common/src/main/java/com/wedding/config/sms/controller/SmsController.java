package com.wedding.config.sms.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wedding.config.sms.config.AliyunSmsConfig;
import com.wedding.config.sms.config.AliyunSmsUtils;
import com.wedding.config.sms.constant.SmsConstant;
import com.wedding.config.sms.vo.MobilecodeAuthenticationToken;
import com.wedding.config.sms.vo.SmsLoginVo;
import com.wedding.config.sms.vo.SmsResponse;
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
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhuqi
 * @Date: 2024/02/03/18:04
 * @Description:
 */
@RequestMapping("/sms")
@RestController
@Slf4j
public class SmsController {
    @Resource
    private AliyunSmsConfig aliyunSmsConfig;
    @Resource
    private AliyunSmsUtils aliyunSmsUtils;
    @Autowired
    private UsersService usersService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;

    @PostMapping("/sendMsg")
    @ApiOperation("发送短信验证码")
    public ResponseResult sendMsgs(HttpServletRequest request, @RequestBody SmsLoginVo phoneLoginData) throws Exception {
        // 查看用户是否是系统内部用户
        LambdaQueryWrapper<Users> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Users::getPhoneNumber, phoneLoginData.getPhone());
        if (usersService.count(queryWrapper) <= 0) {
            return ResponseResult.errorResult(AppHttpCodeEnum.USER_NOT_EXISTS);
        }
        // 先判断图形验证码是否过期和是否正确
        String key = phoneLoginData.getKey();
        String imageCode = phoneLoginData.getImageCode();
        String cacheCode = redisCache.getCacheObject(SysConstants.WEDDING_IMAGE_CODE + key);
        long expire = redisCache.getExpire(SysConstants.WEDDING_IMAGE_CODE + key);
        if (expire < 0) {
            return ResponseResult.errorResult(AppHttpCodeEnum.IMAGE_CODE_IS_EXPIRE);
        }
        // 判断图形验证码是否正确
        if (!cacheCode.equals(imageCode)) {
            redisCache.deleteObject(SysConstants.WEDDING_IMAGE_CODE + key);
            return ResponseResult.errorResult(AppHttpCodeEnum.IMAGE_CODE_ERROR);
        }
        redisCache.deleteObject(SysConstants.WEDDING_IMAGE_CODE + key);
        // 发送短信验证码
        aliyunSmsUtils.sendMsg(request, phoneLoginData);
        return ResponseResult.okResult(new SmsResponse(200, "验证码发送成功~"));
    }

    @PostMapping("/login")
    @ApiOperation("验证码登录")
    public ResponseResult smsLogin(@RequestBody SmsLoginVo phoneLoginData) {
        //如果认证通过，判断验证码是否正确
        String code = redisCache.getCacheObject(SmsConstant.SMS_CODE + phoneLoginData.getPhone());
        log.info("----------------------smsCode:{}", code);

        LambdaQueryWrapper<Users> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Users::getPhoneNumber, phoneLoginData.getPhone());
        Users user = usersService.getOne(wrapper);
        if (Objects.nonNull(user)) {
            // 说明系统有该用户
            MobilecodeAuthenticationToken authenticationToken = new MobilecodeAuthenticationToken(phoneLoginData.getPhone(), phoneLoginData.getCode());
            Authentication authenticate = authenticationManager.authenticate(authenticationToken);
            if (Objects.isNull(authenticate) || !authenticate.isAuthenticated()) {
                log.info("-----smsLogin认证失败！--：{}---", authenticate.isAuthenticated());
                return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR);
            }
            if (authenticate.isAuthenticated()) {
                // 认证通过
                SecurityContextHolder.getContext().setAuthentication(authenticate);
                //如果认证通过，判断验证码是否正确
                String smsCode = redisCache.getCacheObject(SmsConstant.SMS_CODE + phoneLoginData.getPhone());
                // 获取过期时间
                long expire = redisCache.getExpire(SmsConstant.SMS_CODE + phoneLoginData.getPhone());
                if (expire < 0) {
                    return ResponseResult.okResult("验证码已过期！请重新发送！");
                }
                if (smsCode.equals(phoneLoginData.getCode())) {
                    // 验证码正确
                    // 生成token
                    // SmsLoginUser loginUser = (SmsLoginUser) authenticate.getPrincipal();
                    LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
                    log.info("-----smsLogin认证通过！--：{}---", loginUser);

                    String userId = loginUser.getUser().getId().toString();
                    String jwt = JwtUtil.createJWT(userId);
                    // 将用户信息存入redis并设置过期时间为一天
                    redisCache.setCacheObject(SysConstants.WEDDING_LOGIN + userId, loginUser, SysConstants.EXPIRED_USER, TimeUnit.HOURS);
                    UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
                    WeddingLoginUser weddingLoginUser = new WeddingLoginUser(jwt, userInfoVo);
                    // 尝试打印信息
                    log.info("-----weddingLoginUser----:{}", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
                    return ResponseResult.okResult(weddingLoginUser);
                } else {
                    return ResponseResult.okResult("验证码错误！");
                }
            }

        }
        return ResponseResult.okResult();
    }

}
