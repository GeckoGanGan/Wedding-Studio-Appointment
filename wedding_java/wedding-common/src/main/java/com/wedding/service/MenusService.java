package com.wedding.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wedding.domain.ResponseResult;
import com.wedding.domain.dto.PageDTO;
import com.wedding.domain.entity.Menus;


/**
 * 菜单表(Menus)表服务接口
 *
 * @author zhudake
 * @since 2024-03-01 19:52:24
 */
public interface MenusService extends IService<Menus> {

    /**
     * 通过ID查询单条数据
     *
     * @param menuId 主键
     * @return 实例对象
     */
    ResponseResult queryById(Long menuId);

    /**
     * 分页查询
     * @param pageDTO 筛选条件+分页信息
     * @return 分页查询结果
     */
    ResponseResult paginQuery(PageDTO<Menus> pageDTO);
    /**
     * 新增数据
     *
     * @param menus 实例对象
     * @return 实例对象
     */
    ResponseResult insert(Menus menus);
    /**
     * 更新数据
     *
     * @param menus 实例对象
     * @return 实例对象
     */
    ResponseResult update(Menus menus);
    /**
     * 通过主键删除数据
     *
     * @param menuId 主键
     * @return 是否成功
     */
    ResponseResult deleteById(Long menuId);
}

