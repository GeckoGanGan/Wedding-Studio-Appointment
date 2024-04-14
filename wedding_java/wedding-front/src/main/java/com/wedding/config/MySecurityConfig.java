package com.wedding.config;

import com.wedding.config.sms.config.SmsCodeAuthenticationProvider;
import com.wedding.filter.MyJwtTokenFilter;
import com.wedding.handler.security.AccessDeniedHandlerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhuqi
 * @Date: 2024/03/09/18:31
 * @Description: SpringSecurity 认证配置类
 */
@Component
public class MySecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyJwtTokenFilter jwtTokenFilter;
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private AccessDeniedHandlerImpl accessDeniedHandler;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private SmsCodeAuthenticationProvider smsCodeAuthenticationProvider;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(smsCodeAuthenticationProvider);
        auth.authenticationProvider(daoAuthenticationProvider());

        // Add other authentication providers if needed
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {

        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //关闭csrf
                .csrf().disable()
                //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 对于登录接口 允许匿名访问
                .antMatchers("/users/login").anonymous()
                // 注册不需要认证
                .antMatchers("/users/register").anonymous()
                .antMatchers("/studios/page").anonymous()
                .antMatchers("/studios/").anonymous()
                .antMatchers("/comment/commentList").anonymous()
                // 短信验证码发送不需要认证
                .antMatchers("/sms/sendMsg").anonymous()
                .antMatchers("/sms/login").anonymous()
                // 上传图片接口就不需要认证了
                .antMatchers(("/upload")).anonymous()
                // 查询影楼套餐不需要认证
                .antMatchers("/package/packgeList").anonymous()
                // 生成图形验证码接口不需要验证
                .antMatchers("/image/imageCode").anonymous()
                // 查询摄影师不需要认证
                .antMatchers("/photographers/list").anonymous()
                // 查询点赞量不需要认证
                .antMatchers("/photographers/getLike").anonymous()
                // 擦寻摄影师个人信息不需要认证
                .antMatchers("/photographers/info").anonymous()
                // 查询热榜排行不需要认证
                .antMatchers("/studios/hot").anonymous()
                // 收藏需要认证
                .antMatchers(".studios/collect").authenticated()
                // 查看收藏需要认证
                .antMatchers(".studios/collect/").authenticated()
                // 点赞需要认证
                .antMatchers("/photographers/like").authenticated()
                //退出登录需要认证
                .antMatchers("/users/logout").authenticated()
                //查询用户信息必须要先认证
                .antMatchers("/users/userInfo").authenticated()
                // 发表评论需要认证
                .antMatchers("/comment/add").authenticated()
                // 预约需要认证
                .antMatchers("/appointments/appoint").authenticated()
                // 查询预约信息需要认证
                .antMatchers("/appointments/page").authenticated()
                // .antMatchers("/upload").authenticated()
                //jwt过滤器测试用，如果测试没有问题吧这里删除了
                //.antMatchers("/link/getAllLink").authenticated()
                // 除上面外的所有请求全部不需要认证即可访问
                .anyRequest().permitAll();
        //配置异常处理器
        http.exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);
        http.logout().disable();
        // 把jwtAuthenticationTokenFilter添加到SpringSecurity的过滤器链中
        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        //允许跨域
        http.cors();
    }
}
