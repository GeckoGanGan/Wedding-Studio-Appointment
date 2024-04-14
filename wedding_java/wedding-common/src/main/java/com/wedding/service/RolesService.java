package com.wedding.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wedding.domain.ResponseResult;
import com.wedding.domain.dto.PageDTO;
import com.wedding.domain.entity.Roles;


/**
 * 角色表(Roles)表服务接口
 *
 * @author zhudake
 * @since 2024-03-01 19:52:24
 */
public interface RolesService extends IService<Roles> {
    /**
     * 通过ID查询单条数据
     *
     * @param roleId 主键
     * @return 实例对象
     */
    ResponseResult queryById(Long roleId);

    /**
     * 分页查询
     *
     * @param pageDTO 筛选条件
     * @return 分页查询结果
     */
    ResponseResult paginQuery(PageDTO<Roles> pageDTO);
    /**
     * 新增数据
     *
     * @param roles 实例对象
     * @return 实例对象
     */
    ResponseResult insert(Roles roles);
    /**
     * 更新数据
     *
     * @param roles 实例对象
     * @return 实例对象
     */
    ResponseResult update(Roles roles);
    /**
     * 通过主键删除数据
     *
     * @param roleId 主键
     * @return 是否成功
     */
    ResponseResult deleteById(Long roleId);
}

