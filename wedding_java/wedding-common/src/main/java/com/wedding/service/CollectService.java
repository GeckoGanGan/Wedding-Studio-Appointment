package com.wedding.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wedding.domain.ResponseResult;
import com.wedding.domain.dto.PageDTO;
import com.wedding.domain.entity.Collect;


/**
 * 收藏表(Collect)表服务接口
 *
 * @author zhudake
 * @since 2024-04-06 21:33:47
 */
public interface CollectService extends IService<Collect> {
    // 查询登录用户的收藏列表
    ResponseResult getCollectByUserId(PageDTO<Collect> collectPageDTO);

    // 取消收藏
    ResponseResult collectCancel(Long collectId);
}

