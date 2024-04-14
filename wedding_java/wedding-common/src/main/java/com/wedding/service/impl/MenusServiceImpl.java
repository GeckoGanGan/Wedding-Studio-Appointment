package com.wedding.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wedding.domain.ResponseResult;
import com.wedding.domain.dto.PageDTO;
import com.wedding.domain.entity.ChatMessages;
import com.wedding.domain.entity.Menus;
import com.wedding.domain.enums.AppHttpCodeEnum;
import com.wedding.domain.vo.PageVO;
import com.wedding.mapper.MenusMapper;
import com.wedding.service.MenusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 菜单表(Menus)表服务实现类
 *
 * @author zhudake
 * @since 2024-03-01 19:52:24
 */
@Service("menusService")
@Slf4j
public class MenusServiceImpl extends ServiceImpl<MenusMapper, Menus> implements MenusService {
    @Autowired
    private MenusMapper menusMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param menuId 主键
     * @return 实例对象
     */
    public ResponseResult queryById(Long menuId) {
        return ResponseResult.okResult(menusMapper.selectById(menuId));
    }

    /**
     * 分页查询
     *
     * @param pageDTO 筛选条件+分页信息
     * @return 分页查询结果
     */
    public ResponseResult paginQuery(PageDTO<Menus> pageDTO) {
        //1. 构建动态查询条件
        LambdaQueryWrapper<Menus> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(pageDTO.getQueryConditions().getMenuName())) {
            queryWrapper.eq(Menus::getMenuName, pageDTO.getQueryConditions().getMenuName());
        }
        if (StringUtils.isNotBlank(pageDTO.getQueryConditions().getPath())) {
            queryWrapper.eq(Menus::getPath, pageDTO.getQueryConditions().getPath());
        }
        if (StringUtils.isNotBlank(pageDTO.getQueryConditions().getComponent())) {
            queryWrapper.eq(Menus::getComponent, pageDTO.getQueryConditions().getComponent());
        }
        if (StringUtils.isNotBlank(pageDTO.getQueryConditions().getMenuType())) {
            queryWrapper.eq(Menus::getMenuType, pageDTO.getQueryConditions().getMenuType());
        }
        if (StringUtils.isNotBlank(pageDTO.getQueryConditions().getVisible())) {
            queryWrapper.eq(Menus::getVisible, pageDTO.getQueryConditions().getVisible());
        }
        if (StringUtils.isNotBlank(pageDTO.getQueryConditions().getStatus())) {
            queryWrapper.eq(Menus::getStatus, pageDTO.getQueryConditions().getStatus());
        }
        if (StringUtils.isNotBlank(pageDTO.getQueryConditions().getPerms())) {
            queryWrapper.eq(Menus::getPerms, pageDTO.getQueryConditions().getPerms());
        }
        if (StringUtils.isNotBlank(pageDTO.getQueryConditions().getIcon())) {
            queryWrapper.eq(Menus::getIcon, pageDTO.getQueryConditions().getIcon());
        }
        if (StringUtils.isNotBlank(pageDTO.getQueryConditions().getRemark())) {
            queryWrapper.eq(Menus::getRemark, pageDTO.getQueryConditions().getRemark());
        }
        //2. 执行分页查询
        Page<Menus> page = new Page<>();
        page.setSize(pageDTO.getPageSize());
        page.setCurrent(pageDTO.getPageNum());
        page(page, queryWrapper);
        List<Menus> records = page.getRecords();
        log.info("分页查询结果：{}", records);
        PageVO pageVO = new PageVO(page.getTotal(), records,true);
        //3. 返回结果
        return ResponseResult.okResult(pageVO);
    }

    /**
     * 新增数据
     *
     * @param menus 实例对象
     * @return 实例对象
     */
    public ResponseResult insert(Menus menus) {
        menusMapper.insert(menus);
        return ResponseResult.okResult("新增成功");
    }

    /**
     * 更新数据
     *
     * @param menus 实例对象
     * @return 实例对象
     */
    public ResponseResult update(Menus menus) {
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<Menus> chainWrapper = new LambdaUpdateChainWrapper<Menus>(menusMapper);
        if (StringUtils.isNotBlank(menus.getMenuName())) {
            chainWrapper.eq(Menus::getMenuName, menus.getMenuName());
        }
        if (StringUtils.isNotBlank(menus.getPath())) {
            chainWrapper.eq(Menus::getPath, menus.getPath());
        }
        if (StringUtils.isNotBlank(menus.getComponent())) {
            chainWrapper.eq(Menus::getComponent, menus.getComponent());
        }
        if (StringUtils.isNotBlank(menus.getMenuType())) {
            chainWrapper.eq(Menus::getMenuType, menus.getMenuType());
        }
        if (StringUtils.isNotBlank(menus.getVisible())) {
            chainWrapper.eq(Menus::getVisible, menus.getVisible());
        }
        if (StringUtils.isNotBlank(menus.getStatus())) {
            chainWrapper.eq(Menus::getStatus, menus.getStatus());
        }
        if (StringUtils.isNotBlank(menus.getPerms())) {
            chainWrapper.eq(Menus::getPerms, menus.getPerms());
        }
        if (StringUtils.isNotBlank(menus.getIcon())) {
            chainWrapper.eq(Menus::getIcon, menus.getIcon());
        }
        if (StringUtils.isNotBlank(menus.getRemark())) {
            chainWrapper.eq(Menus::getRemark, menus.getRemark());
        }
        //2. 设置主键，并更新
        chainWrapper.set(Menus::getMenuId, menus.getMenuId());
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
     * @param menuId 主键
     * @return 是否成功
     */
    public ResponseResult deleteById(Long menuId) {
        int total = menusMapper.deleteById(menuId);
        if (total>0){
            return ResponseResult.okResult("删除成功！");
        }else {
            return ResponseResult.errorResult(AppHttpCodeEnum.DELETE_ERROR);
        }
    }

}

