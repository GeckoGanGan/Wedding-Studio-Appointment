package com.wedding.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wedding.domain.entity.Samples;
import org.apache.ibatis.annotations.Mapper;


/**
 * 影楼样片关联表(StudioSamples)表数据库访问层
 *
 * @author zhudake
 * @since 2024-03-01 19:52:25
 */
@Mapper
public interface SamplesMapper extends BaseMapper<Samples> {

}

