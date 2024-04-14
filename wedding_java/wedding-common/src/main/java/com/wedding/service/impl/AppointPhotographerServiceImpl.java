package com.wedding.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wedding.domain.ResponseResult;
import com.wedding.domain.dto.AppointmentDTO;
import com.wedding.domain.entity.AppointPhotographer;
import org.springframework.stereotype.Service;
import com.wedding.service.AppointPhotographerService;
import com.wedding.mapper.AppointPhotographerMapper;

/**
 * 预约摄影师关联表(AppointPhotographer)表服务实现类
 *
 * @author zhudake
 * @since 2024-03-24 15:57:55
 */
@Service("appointPhotographerService")
public class AppointPhotographerServiceImpl extends ServiceImpl<AppointPhotographerMapper, AppointPhotographer> implements AppointPhotographerService {


}

