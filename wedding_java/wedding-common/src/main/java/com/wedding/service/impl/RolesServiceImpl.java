package com.wedding.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wedding.domain.ResponseResult;
import com.wedding.domain.dto.PageDTO;
import com.wedding.domain.entity.Roles;
import com.wedding.domain.enums.AppHttpCodeEnum;
import com.wedding.domain.vo.PageVO;
import com.wedding.mapper.RolesMapper;
import com.wedding.service.RolesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色表(Roles)表服务实现类
 *
 * @author zhudake
 * @since 2024-03-01 19:52:24
 */
@Service("rolesService")
@Slf4j
public class RolesServiceImpl extends ServiceImpl<RolesMapper, Roles> implements RolesService {
    @Autowired
    private RolesMapper rolesMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param roleId 主键
     * @return 实例对象
     */
    public ResponseResult queryById(Long roleId) {
        Roles roles = rolesMapper.selectById(roleId);
        return ResponseResult.okResult(roles);
    }

    /**
     * 分页查询
     *
     * @param pageDTO 筛选条件+分页信息
     * @return 分页查询结果
     */
    public ResponseResult paginQuery(PageDTO<Roles> pageDTO) {
        //1. 构建动态查询条件
        LambdaQueryWrapper<Roles> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(pageDTO.getQueryConditions().getRoleName())) {
            queryWrapper.eq(Roles::getRoleName, pageDTO.getQueryConditions().getRoleName());
        }
        if (StringUtils.isNotBlank(pageDTO.getQueryConditions().getRoleKey())) {
            queryWrapper.eq(Roles::getRoleKey, pageDTO.getQueryConditions().getRoleKey());
        }
        if (StringUtils.isNotBlank(pageDTO.getQueryConditions().getStatus())) {
            queryWrapper.eq(Roles::getStatus, pageDTO.getQueryConditions().getStatus());
        }
        if (StringUtils.isNotBlank(pageDTO.getQueryConditions().getRemark())) {
            queryWrapper.eq(Roles::getRemark, pageDTO.getQueryConditions().getRemark());
        }
        //2. 执行分页查询
        Page<Roles> page = new Page<>();
        page.setSize(pageDTO.getPageSize());
        page.setCurrent(pageDTO.getPageNum());
        page(page, queryWrapper);
        List<Roles> records = page.getRecords();
        log.info("分页查询结果：{}", records);
        PageVO pageVO = new PageVO(page.getTotal(), records,true);
        //3. 返回结果
        return ResponseResult.okResult(pageVO);
    }

    /**
     * 新增数据
     *
     * @param roles 实例对象
     * @return 实例对象
     */
    public ResponseResult insert(Roles roles) {
        rolesMapper.insert(roles);
        return ResponseResult.okResult("新增成功！");

    }

    /**
     * 更新数据
     *
     * @param roles 实例对象
     * @return 实例对象
     */
    public ResponseResult update(Roles roles) {
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<Roles> chainWrapper = new LambdaUpdateChainWrapper<>(rolesMapper);
        if (StringUtils.isNotBlank(roles.getRoleName())) {
            chainWrapper.eq(Roles::getRoleName, roles.getRoleName());
        }
        if (StringUtils.isNotBlank(roles.getRoleKey())) {
            chainWrapper.eq(Roles::getRoleKey, roles.getRoleKey());
        }
        if (StringUtils.isNotBlank(roles.getStatus())) {
            chainWrapper.eq(Roles::getStatus, roles.getStatus());
        }
        if (StringUtils.isNotBlank(roles.getRemark())) {
            chainWrapper.eq(Roles::getRemark, roles.getRemark());
        }
        //2. 设置主键，并更新
        chainWrapper.set(Roles::getRoleId, roles.getRoleId());
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
     * @param roleId 主键
     * @return 是否成功
     */
    public ResponseResult deleteById(Long roleId) {
        int total = rolesMapper.deleteById(roleId);
        if (total > 0){
            return ResponseResult.okResult("删除成功！");
        }else {
            return ResponseResult.errorResult(AppHttpCodeEnum.DELETE_ERROR);
        }
    }
}

