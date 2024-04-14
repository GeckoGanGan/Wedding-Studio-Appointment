package com.wedding.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wedding.domain.entity.Message;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;


/**
 * 留言表(Message)表数据库访问层
 *
 * @author zhudake
 * @since 2024-03-24 18:58:56
 */
@Mapper
public interface MessageMapper extends BaseMapper<Message> {

}

