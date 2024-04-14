package com.wedding.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wedding.constants.SysConstants;
import com.wedding.domain.entity.Photographers;
import com.wedding.domain.entity.StudioPhotographers;
import com.wedding.domain.entity.StudiosPackage;
import com.wedding.domain.vo.StudioPackageVo;
import com.wedding.mapper.StudiosPackageMapper;
import com.wedding.service.PhotographersService;
import com.wedding.service.StudioPhotographersService;
import com.wedding.service.StudiosPackageService;
import com.wedding.service.StudiosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 影楼套餐表(StudiosPackage)表服务实现类
 *
 * @author zhudake
 * @since 2024-03-23 14:21:15
 */
@Service("studiosPackageService")
public class StudiosPackageServiceImpl extends ServiceImpl<StudiosPackageMapper, StudiosPackage> implements StudiosPackageService {

    @Autowired
    private StudioPhotographersService studioPhotographersService;
    @Autowired
    private PhotographersService photographersService;
    @Autowired
    private StudiosService studiosService;
    @Override
    public StudioPackageVo getStudioPackages(Long studioId) {

        // 设置摄影师列表
        StudioPackageVo studioPackageVo = new StudioPackageVo();
        // 先获取摄影师信息
        LambdaQueryWrapper<StudioPhotographers> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StudioPhotographers::getStudioId,studioId);
        List<Long> photographerIdList = studioPhotographersService.list(queryWrapper)
                .stream()
                .map(studioPhotographers -> studioPhotographers.getPhotographerId())
                .collect(Collectors.toList());
        LambdaQueryWrapper<Photographers> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.in(Photographers::getId,photographerIdList);
        studioPackageVo.setPhotographers(photographersService.list(queryWrapper1));

        // 查询影楼信息
        studioPackageVo.setStudio(studiosService.getById(studioId));
        LambdaQueryWrapper<StudiosPackage> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StudiosPackage::getStudioId,studioId);
        // 套餐的状态必须是正常的
        wrapper.eq(StudiosPackage::getStatus,SysConstants.PACKAGE_NORMAL);
        wrapper.orderByDesc(StudiosPackage::getPackagePrice);
        studioPackageVo.setStudiosPackageList(list(wrapper));

        return studioPackageVo;
    }
}

