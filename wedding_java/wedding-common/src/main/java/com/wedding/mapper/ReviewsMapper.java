package com.wedding.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wedding.domain.entity.Reviews;
import org.apache.ibatis.annotations.Mapper;


/**
 * 评价表(Reviews)表数据库访问层
 *
 * @author zhudake
 * @since 2024-03-01 19:52:24
 */
@Mapper
public interface ReviewsMapper extends BaseMapper<Reviews> {

}

