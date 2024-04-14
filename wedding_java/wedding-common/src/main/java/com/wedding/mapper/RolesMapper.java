package com.wedding.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wedding.domain.entity.Roles;
import org.apache.ibatis.annotations.Mapper;


/**
 * 角色表(Roles)表数据库访问层
 *
 * @author zhudake
 * @since 2024-03-01 19:52:24
 */
@Mapper
public interface RolesMapper extends BaseMapper<Roles> {

}

