package com.wedding.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wedding.domain.entity.Menus;
import com.wedding.domain.entity.Users;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 菜单表(Menus)表数据库访问层
 *
 * @author zhudake
 * @since 2024-03-01 19:52:24
 */
@Mapper
public interface MenusMapper extends BaseMapper<Menus> {

    List<String> permissionList(Users users);

}

