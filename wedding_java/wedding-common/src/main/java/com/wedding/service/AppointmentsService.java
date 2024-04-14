package com.wedding.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wedding.domain.ResponseResult;
import com.wedding.domain.dto.AppointmentDTO;
import com.wedding.domain.dto.PageDTO;
import com.wedding.domain.entity.Appointments;
import com.wedding.domain.entity.Studios;

/**
 * 预约表(Appointments)表服务接口
 *
 * @author zhudake
 * @since 2024-03-01 19:52:24
 */
public interface AppointmentsService extends IService<Appointments> {
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Appointments queryById(Long id);

    /**
     * 分页查询
     *
     * @param pageDTO 筛选条件+分页查询
     * @return 分页查询结果
     */
    ResponseResult paginQuery(PageDTO<Appointments> pageDTO);
    /**
     * 新增数据
     *
     * @param selectedPackage 实例对象
     * @return 实例对象
     */
    ResponseResult insert(AppointmentDTO selectedPackage);
    /**
     * 更新数据
     *
     * @param appointments 实例对象
     * @return 实例对象
     */
    ResponseResult update(Appointments appointments);
    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    ResponseResult deleteById(Long id);


}

