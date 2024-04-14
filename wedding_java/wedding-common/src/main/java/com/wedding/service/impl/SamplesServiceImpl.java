package com.wedding.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wedding.domain.entity.Samples;
import com.wedding.mapper.SamplesMapper;
import com.wedding.service.SamplesService;
import org.springframework.stereotype.Service;

/**
 * 影楼样片关联表(StudioSamples)表服务实现类
 *
 * @author zhudake
 * @since 2024-03-01 19:52:25
 */
@Service("studioSamplesService")
public class SamplesServiceImpl extends ServiceImpl<SamplesMapper, Samples> implements SamplesService {

}

