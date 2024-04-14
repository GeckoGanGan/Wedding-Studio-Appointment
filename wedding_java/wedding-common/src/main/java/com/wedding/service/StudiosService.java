package com.wedding.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wedding.domain.ResponseResult;
import com.wedding.domain.dto.CollectDTO;
import com.wedding.domain.dto.PageDTO;
import com.wedding.domain.entity.Collect;
import com.wedding.domain.entity.Studios;


/**
 * 影楼表(Studios)表服务接口
 *
 * @author zhudake
 * @since 2024-03-01 19:52:25
 */
public interface StudiosService extends IService<Studios> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Studios queryById(Long id);

    /**
     * 分页查询
     * @param pageDTO
     * @return 分页查询结果
     */
    ResponseResult paginQuery(PageDTO<Studios> pageDTO);
    /**
     * 新增数据
     *
     * @param studios 实例对象
     * @return 实例对象
     */
    Studios insert(Studios studios);
    /**
     * 更新数据
     *
     * @param studios 实例对象
     * @return 实例对象
     */
    Studios update(Studios studios);
    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * 查询影楼详情
     * @param id
     * @return
     */
    ResponseResult getStudioDetails(Long id);

    /**
     * 浏览量刷新
     * @param id
     * @return
     */

    ResponseResult view(String id);

    /**
     * 收藏
     * @param param
     * @return
     */

    ResponseResult collect(CollectDTO param);

    /**
     *  热门影楼
     * @param type
     * @return
     */

    ResponseResult hot(String type);


}

