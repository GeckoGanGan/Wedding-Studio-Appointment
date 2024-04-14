package com.wedding.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wedding.domain.entity.StudioPhotographers;
import com.wedding.mapper.StudioPhotographersMapper;
import com.wedding.service.StudioPhotographersService;
import org.springframework.stereotype.Service;

/**
 * 影楼摄影师关联表(StudioPhotographers)表服务实现类
 *
 * @author zhudake
 * @since 2024-03-01 19:52:24
 */
@Service("studioPhotographersService")
public class StudioPhotographersServiceImpl extends ServiceImpl<StudioPhotographersMapper, StudioPhotographers> implements StudioPhotographersService {

}

