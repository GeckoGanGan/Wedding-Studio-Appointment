package com.wedding.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wedding.domain.entity.Photographers;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 摄影师表(Photographers)表数据库访问层
 *
 * @author zhudake
 * @since 2024-03-01 19:52:22
 */
@Mapper
public interface PhotographersMapper extends BaseMapper<Photographers> {

    List<Photographers> getPhotographersList();

    List<Photographers> getPhotographersByPhotographerIds(List<Long> photographerIds);

    Photographers getPhotographersById(Long id);

}

