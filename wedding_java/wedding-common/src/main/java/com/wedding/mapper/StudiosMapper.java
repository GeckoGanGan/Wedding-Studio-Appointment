package com.wedding.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.wedding.domain.entity.Studios;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * 影楼表(Studios)表数据库访问层
 *
 * @author zhudake
 * @since 2024-03-01 19:52:25
 */
@Mapper
public interface StudiosMapper extends BaseMapper<Studios> {
    /**
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<Studios> selectByPage(IPage<Studios> page , @Param(Constants.WRAPPER) Wrapper<Studios> wrapper);

}

