package com.wedding.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wedding.domain.ResponseResult;
import com.wedding.domain.dto.PageDTO;
import com.wedding.domain.entity.RoleMenus;


/**
 * 角色菜单关联表(RoleMenus)表服务接口
 *
 * @author zhudake
 * @since 2024-03-01 19:52:24
 */
public interface RoleMenusService extends IService<RoleMenus> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    RoleMenus queryById(Long id);

    /**
     * 分页查询
     *
     * @param pageDTO 筛选条件+分页信息
     * @return 分页查询结果
     */
    ResponseResult paginQuery(PageDTO<RoleMenus> pageDTO);

    /**
     * 新增数据
     *
     * @param roleMenus 实例对象
     * @return 实例对象
     */
    RoleMenus insert(RoleMenus roleMenus);

    /**
     * 更新数据
     *
     * @param roleMenus 实例对象
     * @return 实例对象
     */
    RoleMenus update(RoleMenus roleMenus);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);
}

