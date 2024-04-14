package com.wedding.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wedding.constants.SysConstants;
import com.wedding.domain.ResponseResult;
import com.wedding.domain.dto.PageDTO;
import com.wedding.domain.entity.LoginUser;
import com.wedding.domain.entity.UserRoles;
import com.wedding.domain.entity.Users;
import com.wedding.domain.enums.AppHttpCodeEnum;
import com.wedding.domain.vo.PageVO;
import com.wedding.domain.vo.StudioOnlineUserVo;
import com.wedding.domain.vo.UserInfoVo;
import com.wedding.domain.vo.WeddingLoginUser;
import com.wedding.mapper.UsersMapper;
import com.wedding.service.UserRolesService;
import com.wedding.service.UsersService;
import com.wedding.utils.BeanCopyUtils;
import com.wedding.utils.JwtUtil;
import com.wedding.utils.RedisCache;
import com.wedding.utils.SecurityUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 用户表(Users)表服务实现类
 *
 * @author zhudake
 * @since 2024-03-01 19:52:25
 */
@Service("usersService")
@Slf4j
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private UserRolesService userRolesService;


    @Override
    public ResponseResult register(Users user) {
        // 先查询用户是否已存在
        LambdaQueryWrapper<Users> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Users::getUsername, user.getUsername());
        if (count(wrapper) > 0){
            return ResponseResult.errorResult(AppHttpCodeEnum.USERNAME_EXIST);
        }
        // 设置默认头像
        user.setAvatar(SysConstants.DEFAULT_AVATAR);
        user.setNickName(SysConstants.DEFAULT_NICKNAME);
        // 密码加密
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode(user.getPassword());
        user.setPassword(encode);
        // 存入数据库
        ResponseResult responseResult = insert(user);
        Users regester = (Users) responseResult.getData();
        // 设置角色为普通角色
        UserRoles userRoles = new UserRoles();
        userRoles.setUserId(regester.getId());
        userRoles.setRoleId(SysConstants.USER_ROLE_NORMAL);
        userRolesService.save(userRoles);
        log.info("---------注册用户：{}",regester);
        return ResponseResult.okResult("注册成功！");
    }
    // 登录接口
    @Override
    public ResponseResult login(Users user) {
        log.info("----------用户名登录：{}",user);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        // 判断是否认证通过
        log.info("-----authenticate是否通过----:{}",authenticate.isAuthenticated());
        if (Objects.isNull(authenticate)||!authenticate.isAuthenticated()){
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR);
        }
        // 认证通过
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        // 获取userId生成JWT
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        log.info("-----loginUser----:{}",loginUser);
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);

        // 将用户信息存入redis并设置过期时间为一天
        redisCache.setCacheObject(SysConstants.WEDDING_LOGIN+userId, loginUser,SysConstants.EXPIRED_USER,TimeUnit.HOURS);
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        WeddingLoginUser weddingLoginUser = new WeddingLoginUser(jwt, userInfoVo);
        // 尝试打印信息
        log.info("-----weddingLoginUser----:{}",SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return ResponseResult.okResult(weddingLoginUser);

    }

    // 退出登录
    @Override
    public ResponseResult logout(HttpServletRequest request) throws Exception {
        log.info("------------进入logout方法------------");
        log.info("------header----:{}",request.getHeader("token"));

        Claims claims = JwtUtil.parseJWT(request.getHeader("token"));
        if (Objects.isNull(claims)){
            log.error("-------登录用户为空！");
        }
        String id =  claims.getSubject();
        log.info("------id----:{}",id);
        redisCache.deleteObject(SysConstants.WEDDING_LOGIN+id);
        return ResponseResult.okResult("退出成功！");

    }


    @Override
    public ResponseResult updateUserInfo(UserInfoVo userInfoVo) {
        Users users = BeanCopyUtils.copyBean(userInfoVo, Users.class);
        int update = getBaseMapper().update(users, new LambdaQueryWrapper<Users>().eq(Users::getId, userInfoVo.getId()));
        log.info("------update----:{}",update);
        return ResponseResult.okResult("修改成功！");
    }

    @Override
    public ResponseResult getUserInfo(Long id) {
        LambdaQueryWrapper<Users> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Users::getId,id);
        Users user = getOne(wrapper);
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(userInfoVo);
    }

    @Override
    public ResponseResult getOnlineUser(Long studioId) {
        Object cacheObject = redisCache.getCacheObject(SysConstants.STUDIO_ONLINE_USER_KEY + studioId);
        log.info("-------getOnlineUser：{}-------",cacheObject);
        StudioOnlineUserVo studioOnlineUserVo = BeanCopyUtils.copyBean(cacheObject, StudioOnlineUserVo.class);
        // 排除当前登录用户
        Users currentUser = SecurityUtils.getLoginUser().getUser();
        log.info("-------currentUser：{}-------",currentUser);
        List<Users> onlineUsers = studioOnlineUserVo.getOnlineUsers();
        log.info("-------onlineUsers：{}-------",onlineUsers);

        // 根据用户去除重复的用户
        List<Users> collect = onlineUsers.stream().filter(users -> users.getId() != currentUser.getId()).collect(Collectors.toList());
        return ResponseResult.okResult(collect);
    }

    /**
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    public ResponseResult queryById(Long id){
         usersMapper.selectById(id);
         return ResponseResult.okResult();
    }

    /**
     * 分页查询
     *
     * @param pageDTO 筛选条件
     * @return
     */
    public ResponseResult paginQuery(PageDTO<Users> pageDTO){
        //1. 构建动态查询条件
        LambdaQueryWrapper<Users> queryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(pageDTO.getQueryConditions().getUsername())){
            queryWrapper.eq(Users::getUsername, pageDTO.getQueryConditions().getUsername());
        }
        if(StringUtils.isNotBlank(pageDTO.getQueryConditions().getPassword())){
            queryWrapper.eq(Users::getPassword, pageDTO.getQueryConditions().getPassword());
        }
        if(StringUtils.isNotBlank(pageDTO.getQueryConditions().getUserEmail())){
            queryWrapper.eq(Users::getUserEmail, pageDTO.getQueryConditions().getUserEmail());
        }
        if(StringUtils.isNotBlank(pageDTO.getQueryConditions().getPhoneNumber())){
            queryWrapper.eq(Users::getPhoneNumber, pageDTO.getQueryConditions().getPhoneNumber());
        }
        if(StringUtils.isNotBlank(pageDTO.getQueryConditions().getSex())){
            queryWrapper.eq(Users::getSex, pageDTO.getQueryConditions().getSex());
        }
        if(StringUtils.isNotBlank(pageDTO.getQueryConditions().getAvatar())){
            queryWrapper.eq(Users::getAvatar, pageDTO.getQueryConditions().getAvatar());
        }
        if(StringUtils.isNotBlank(pageDTO.getQueryConditions().getStatus())){
            queryWrapper.eq(Users::getStatus, pageDTO.getQueryConditions().getStatus());
        }
        if(StringUtils.isNotBlank(pageDTO.getQueryConditions().getRemark())){
            queryWrapper.eq(Users::getRemark, pageDTO.getQueryConditions().getRemark());
        }
        if(StringUtils.isNotBlank(pageDTO.getQueryConditions().getNickName())){
            queryWrapper.eq(Users::getNickName, pageDTO.getQueryConditions().getNickName());
        }
        //2. 执行分页查询
        Page<Users> page  = new Page<>(pageDTO.getPageNum() , pageDTO.getPageSize() , true);
        page(page, queryWrapper);
        PageVO pageVO = new PageVO(page.getTotal(), page.getRecords(),true);

        //3. 返回结果
        return ResponseResult.okResult(pageVO);
    }

    /**
     * 新增数据
     *
     * @param users 实例对象
     * @return 实例对象
     */
    public ResponseResult insert(Users users){
        int insert = usersMapper.insert(users);
        return ResponseResult.okResult();
    }

    /**
     * 更新数据
     *
     * @param users 实例对象
     * @return 实例对象
     */
    public ResponseResult update(Users users){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<Users> chainWrapper = new LambdaUpdateChainWrapper<Users>(usersMapper);
        if(StringUtils.isNotBlank(users.getUsername())){
            chainWrapper.eq(Users::getUsername, users.getUsername());
        }
        if(StringUtils.isNotBlank(users.getPassword())){
            chainWrapper.eq(Users::getPassword, users.getPassword());
        }
        if(StringUtils.isNotBlank(users.getUserEmail())){
            chainWrapper.eq(Users::getUserEmail, users.getUserEmail());
        }
        if(StringUtils.isNotBlank(users.getPhoneNumber())){
            chainWrapper.eq(Users::getPhoneNumber, users.getPhoneNumber());
        }
        if(StringUtils.isNotBlank(users.getSex())){
            chainWrapper.eq(Users::getSex, users.getSex());
        }
        if(StringUtils.isNotBlank(users.getAvatar())){
            chainWrapper.eq(Users::getAvatar, users.getAvatar());
        }
        if(StringUtils.isNotBlank(users.getStatus())){
            chainWrapper.eq(Users::getStatus, users.getStatus());
        }
        if(StringUtils.isNotBlank(users.getRemark())){
            chainWrapper.eq(Users::getRemark, users.getRemark());
        }
        if(StringUtils.isNotBlank(users.getNickName())){
            chainWrapper.eq(Users::getNickName, users.getNickName());
        }
        //2. 设置主键，并更新
        chainWrapper.set(Users::getId, users.getId());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return ResponseResult.okResult("更新成功");
        }else{
            return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_ERROR);
        }
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    public ResponseResult deleteById(Long id){
        int total = usersMapper.deleteById(id);
        if(total > 0){
            return ResponseResult.okResult("删除成功");
        }else{
            return ResponseResult.errorResult(AppHttpCodeEnum.DELETE_ERROR);
        }
    }
}

