package com.wedding.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wedding.constants.SysConstants;
import com.wedding.domain.ResponseResult;
import com.wedding.domain.dto.AppointmentDTO;
import com.wedding.domain.dto.PageDTO;
import com.wedding.domain.entity.*;
import com.wedding.domain.enums.AppHttpCodeEnum;
import com.wedding.domain.vo.AppointPageVO;
import com.wedding.domain.vo.PageVO;
import com.wedding.mapper.AppointmentsMapper;
import com.wedding.service.*;
import com.wedding.utils.SecurityUtils;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.QuerydslRepositoryInvokerAdapter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 预约表(Appointments)表服务实现类
 *
 * @author zhudake
 * @since 2024-03-01 19:52:24
 */
@Service("appointmentsService")
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class AppointmentsServiceImpl extends ServiceImpl<AppointmentsMapper, Appointments> implements AppointmentsService {
    @Autowired
    private AppointmentsMapper appointmentsMapper;
    @Autowired
    private AppointPhotographerService appointPhotographerService;
    @Autowired
    private PhotographersService photographersService;
    @Autowired
    private StudiosService studiosService;
    @Autowired
    private StudiosPackageService studiosPackageService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    public Appointments queryById(Long id) {
        return appointmentsMapper.selectById(id);
    }

    /**
     * 分页查询
     *
     * @param pageDTO 筛选条件+分页查询
     * @return 分页查询结果
     */
    public ResponseResult paginQuery(PageDTO<Appointments> pageDTO) {
        String studioName = pageDTO.getQueryConditions().getStudioName();
        AppointPageVO appointPageVO = new AppointPageVO();
        LambdaQueryWrapper<Appointments> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Appointments::getUserId, pageDTO.getQueryConditions().getUserId());
        // 模糊查询
        wrapper.like(StringUtils.isNotBlank(studioName),Appointments::getStudioName,studioName);
        Page<Appointments> page = new Page<>();
        page.setCurrent(pageDTO.getPageNum());
        page.setSize(pageDTO.getPageSize());
        // 执行分页查询
        page(page,wrapper);
        List<Appointments> appointmentsList = page.getRecords();
        // 设置套餐信息
        setStudioPageInfo(appointmentsList);
        // 设置摄影师列表
        setPhotographersInfo(appointmentsList);
        // 设置影楼信息
        setStudiosInfo(appointmentsList);
        // 计算总金额
        Long totalPrice = getTotalPrice(pageDTO.getQueryConditions().getUserId());
        // 封装对象被并返回
        appointPageVO.setTotal(page.getTotal());
        appointPageVO.setRecords(appointmentsList);
        appointPageVO.setTotalMoney(totalPrice);
        return ResponseResult.okResult(appointPageVO);

    }

    /**
     * 计算总金额
     * @param userId
     * @return
     */
    private Long getTotalPrice(Long userId) {
        if (userId == null) {
            return 0L; // 或者抛出异常，视情况而定
        }
        Long totalPrice = 0L; // 使用 long 类型保存总金额，避免溢出
        LambdaQueryWrapper<Appointments> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Appointments::getUserId, userId);
        List<Long> packageIdList = list(wrapper).stream().map(Appointments::getPackageId).collect(Collectors.toList());
        if (packageIdList.isEmpty()) {
            return 0L; // 如果没有预约记录，则返回 0
        }
        for (Long aLong : packageIdList) {
            LambdaQueryWrapper<StudiosPackage> packageWrapper = new LambdaQueryWrapper<>();
            packageWrapper.eq(StudiosPackage::getId, aLong);
            StudiosPackage aPackage = studiosPackageService.getOne(packageWrapper);
            totalPrice+= aPackage.getPackagePrice();
        }
        return totalPrice;
    }

    /**
     * 设置影楼信息
     * @param appointmentsList
     */
    private void setStudiosInfo(List<Appointments> appointmentsList) {
        for (Appointments appointments : appointmentsList) {
            Studios studios = studiosService.getById(appointments.getStudioId());
            appointments.setStudio(studios);
        }
    }

    /**
     * 查询套餐信息
     * @param appointmentsList
     */
    private void setStudioPageInfo(List<Appointments> appointmentsList) {
        for (Appointments appointments : appointmentsList) {
            StudiosPackage studiosPackage = studiosPackageService.getById(appointments.getPackageId());
            appointments.setStudiosPackage(studiosPackage);
        }

    }

    /**
     * 获取影楼列表
     * @param appointmentsList
     */
    private void setPhotographersInfo(List<Appointments> appointmentsList){
        for (Appointments appointments : appointmentsList) {
            log.info("--------------------------------setPhotographersInfo----------------------------------");
            log.info("appointments:{}  " + appointments);

            LambdaQueryWrapper<AppointPhotographer> wrapper = new LambdaQueryWrapper<>();
            LambdaQueryWrapper<Photographers> photographersWrapper = new LambdaQueryWrapper<>();
            wrapper.eq(AppointPhotographer::getAppointId,appointments.getId());
            // 收集摄影师id去查询摄影师信息
            List<Long> photograperIdList = appointPhotographerService.list(wrapper).stream().map(AppointPhotographer::getPhotographerId).collect(Collectors.toList());
            // 查询摄影师信息
            photographersWrapper.in(Photographers::getId,photograperIdList);
            List<Photographers> photographersList = photographersService.list(photographersWrapper);
            appointments.setPhotographers(photographersList);
        }
    }

    /**
     * 新增数据
     *
     * @param selectedPackage 实例对象
     * @return 实例对象
     */
    public ResponseResult insert(AppointmentDTO selectedPackage) {

        // 获取套餐信息
        StudiosPackage studiosPackage = selectedPackage.getSelectedPackageInfo();
        // 获取摄影师列表
        List<Photographers> photographers = selectedPackage.getSelectedPhotographersInfo();
        // 获取影楼信息
        Studios studios = studiosService.getById(selectedPackage.getStudioId());
        String studioName = studios.getStudioName();
        // 影楼可预约量减一
        Integer maxReservation = studios.getMaxReservation() -1;

        studios.setMaxReservation(maxReservation);
        // 更新
        studiosService.updateById(studios);
        // 执行插入操作
        Appointments appointments = new Appointments();
        appointments.setAppointTime(selectedPackage.getAppointmentTime());
        appointments.setStatus(SysConstants.APPOINT_STATUS_NORMAL);
        appointments.setStudioId(selectedPackage.getStudioId());
        appointments.setRequirements(selectedPackage.getAppointmentRemark());
        appointments.setAppointTime(selectedPackage.getAppointmentTime());
        appointments.setUserId(SecurityUtils.getUserId());
        appointments.setPackageId(studiosPackage.getId());
        appointments.setStudioName(studioName);
        getBaseMapper().insert(appointments);

        // 获取生成的ID
        Long appointId = appointments.getId();
        List<AppointPhotographer> AppointPhotographers = photographers.stream()
                .map(photographers1 -> {
                    AppointPhotographer appointPhotographer = new AppointPhotographer();
                    appointPhotographer.setAppointId(appointId);
                    appointPhotographer.setPhotographerId(photographers1.getId());
                    return appointPhotographer;
                }).collect(Collectors.toList());
        appointPhotographerService.saveBatch(AppointPhotographers);

        return ResponseResult.okResult("预约成功！后续消息将会通过邮件发送方到您的邮箱，请查收~");
    }

    /**
     * 更新数据
     *
     * @param appointments 实例对象
     * @return 实例对象
     */
    public ResponseResult update(Appointments appointments) {
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<Appointments> chainWrapper = new LambdaUpdateChainWrapper<Appointments>(appointmentsMapper);
        if (StringUtils.isNotBlank(appointments.getRequirements())) {
            chainWrapper.eq(Appointments::getRequirements, appointments.getRequirements());
        }
        if (StringUtils.isNotBlank(appointments.getStatus())) {
            chainWrapper.eq(Appointments::getStatus, appointments.getStatus());
        }
        if (StringUtils.isNotBlank(appointments.getRemark())) {
            chainWrapper.eq(Appointments::getRemark, appointments.getRemark());
        }
        //2. 设置主键，并更新
        chainWrapper.set(Appointments::getId, appointments.getId());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if (ret) {
            return ResponseResult.okResult("更新成功！");
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_ERROR);
        }
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    public ResponseResult deleteById(Long id) {
        Long studioId = getById(id).getStudioId();
        Studios studios = studiosService.getById(studioId);
        Integer maxReservation = studios.getMaxReservation()+1;
        studios.setMaxReservation(maxReservation);
        studiosService.updateById(studios);
        appointmentsMapper.deleteById(id);
        appointPhotographerService.getBaseMapper().deleteById(id);
        return ResponseResult.okResult("取消成功！");
    }



}

