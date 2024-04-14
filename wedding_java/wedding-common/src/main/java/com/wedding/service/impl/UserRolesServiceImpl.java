package com.wedding.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wedding.domain.entity.UserRoles;
import com.wedding.mapper.UserRolesMapper;
import org.springframework.stereotype.Service;
import  com.wedding.service.UserRolesService;
/**
 * 用户角色关联表(UserRoles)表服务实现类
 *
 * @author zhudake
 * @since 2024-03-01 19:52:25
 */
@Service("userRolesService")
public class UserRolesServiceImpl extends ServiceImpl<UserRolesMapper, UserRoles> implements UserRolesService {

}

