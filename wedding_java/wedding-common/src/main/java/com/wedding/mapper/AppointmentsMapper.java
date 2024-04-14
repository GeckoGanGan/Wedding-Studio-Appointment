package com.wedding.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wedding.domain.entity.Appointments;
import org.apache.ibatis.annotations.Mapper;


/**
 * 预约表(Appointments)表数据库访问层
 *
 * @author zhudake
 * @since 2024-03-01 19:52:23
 */
@Mapper
public interface AppointmentsMapper extends BaseMapper<Appointments> {
}

