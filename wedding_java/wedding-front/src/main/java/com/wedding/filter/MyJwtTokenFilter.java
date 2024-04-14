package com.wedding.filter;

import com.alibaba.fastjson.JSON;
import com.wedding.constants.SysConstants;
import com.wedding.domain.ResponseResult;
import com.wedding.domain.entity.LoginUser;
import com.wedding.domain.enums.AppHttpCodeEnum;
import com.wedding.utils.JwtUtil;
import com.wedding.utils.RedisCache;
import com.wedding.utils.WebUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhuqi
 * @Date: 2024/03/05/20:52
 * @Description: request filter
 */
@Component
@Slf4j
public class MyJwtTokenFilter extends OncePerRequestFilter {
    @Autowired
    private RedisCache redisCache;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取请求头中的token
        String token = request.getHeader("token");
        log.info("token--------->:{}",token);
        if (!StringUtils.hasText(token)){
            //说明该接口不需要直接登录，直接放行
            filterChain.doFilter(request,response);
            return;
        }
        //获取userId
        Claims claims = null;
        try {
            claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            e.printStackTrace();
            // token超时 token非法
            // 响应给前端需要重新登录
            ResponseResult responseResult = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(responseResult));
            return;
        }
        //获取当前用户的userId
        String userId = claims.getSubject();
        log.info("userId--------->:{}",userId);
        //从redis中获取用户的信息
        LoginUser loginUser = redisCache.getCacheObject(SysConstants.WEDDING_LOGIN + userId);
        log.info("----------loginUser:{}",loginUser);

        //获取不到用户说明token过期了，需要重新登录
        if (Objects.isNull(loginUser)){
            ResponseResult responseResult = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(responseResult));
            return;
        }
        //存入SecurityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken  = new UsernamePasswordAuthenticationToken(loginUser,null,null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request,response);
    }
}
