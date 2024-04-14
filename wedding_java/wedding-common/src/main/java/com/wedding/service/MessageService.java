package com.wedding.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wedding.domain.ResponseResult;
import com.wedding.domain.dto.PageDTO;
import com.wedding.domain.entity.Message;


/**
 * 留言表(Message)表服务接口
 *
 * @author zhudake
 * @since 2024-03-24 18:58:59
 */
public interface MessageService extends IService<Message> {
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ResponseResult queryById(Long id);

    /**
     * 分页查询
     *
     * @param pageDTO 筛选条件
     * @return
     */
   ResponseResult paginQuery(PageDTO<Message> pageDTO);
    /**
     * 新增数据
     *
     * @param message 实例对象
     * @return 实例对象
     */
    ResponseResult insert(Message message);
    /**
     * 更新数据
     *
     * @param message 实例对象
     * @return 实例对象
     */
    ResponseResult update(Message message);
    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    ResponseResult deleteById(Long id);

}

