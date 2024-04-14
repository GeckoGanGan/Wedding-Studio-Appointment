package com.wedding.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wedding.domain.entity.StudiosPackage;
import com.wedding.domain.vo.StudioPackageVo;

import java.util.List;


/**
 * 影楼套餐表(StudiosPackage)表服务接口
 *
 * @author zhudake
 * @since 2024-03-23 14:21:15
 */
public interface StudiosPackageService extends IService<StudiosPackage> {
    /**
     * 查询对应影楼的套餐列表
     * @param studioId
     * @return
     */
    StudioPackageVo getStudioPackages(Long studioId);

}

