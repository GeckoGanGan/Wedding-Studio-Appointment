package com.wedding.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wedding.domain.entity.RoleMenus;
import org.apache.ibatis.annotations.Mapper;


/**
 * 角色菜单关联表(RoleMenus)表数据库访问层
 *
 * @author zhudake
 * @since 2024-03-01 19:52:24
 */
@Mapper
public interface RoleMenusMapper extends BaseMapper<RoleMenus> {

}

