package com.wedding.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wedding.domain.entity.Users;
import org.apache.ibatis.annotations.Mapper;


/**
 * 用户表(Users)表数据库访问层
 *
 * @author zhudake
 * @since 2024-03-01 19:52:25
 */
@Mapper
public interface UsersMapper extends BaseMapper<Users> {

}

