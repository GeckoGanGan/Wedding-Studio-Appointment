package com.wedding.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wedding.domain.entity.StudioPhotographers;
import org.apache.ibatis.annotations.Mapper;


/**
 * 影楼摄影师关联表(StudioPhotographers)表数据库访问层
 *
 * @author zhudake
 * @since 2024-03-01 19:52:24
 */
@Mapper
public interface StudioPhotographersMapper extends BaseMapper<StudioPhotographers> {

}

