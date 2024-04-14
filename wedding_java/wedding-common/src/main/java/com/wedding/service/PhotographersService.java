package com.wedding.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wedding.domain.ResponseResult;
import com.wedding.domain.dto.PageDTO;
import com.wedding.domain.entity.Photographers;


/**
 * 摄影师表(Photographers)表服务接口
 *
 * @author zhudake
 * @since 2024-03-01 19:52:22
 */
public interface PhotographersService extends IService<Photographers> {
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Photographers queryById(Long id);

    /**
     * 分页查询
     *
     * @param pageDTO 筛选条件+分页信息
     * @return 分页查询结果
     */
    ResponseResult paginQuery(PageDTO<Photographers> pageDTO);

    /**
     * 新增数据
     *
     * @param photographers 实例对象
     * @return 实例对象
     */
    Photographers insert(Photographers photographers);

    /**
     * 更新数据
     *
     * @param photographers 实例对象
     * @return 实例对象
     */
    Photographers update(Photographers photographers);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * 查询摄影师列表
     * @return
     */
    ResponseResult listPhotographers();

    /**
     * 点赞操作
     * @param id
     * @return
     */
    ResponseResult like(String id);
}

