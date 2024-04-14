package com.wedding.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wedding.domain.ResponseResult;
import com.wedding.domain.dto.PageDTO;
import com.wedding.domain.vo.PageVO;
import com.wedding.mapper.RoleMenusMapper;
import com.wedding.service.RoleMenusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import  com.wedding.domain.entity.RoleMenus;

import java.util.List;

/**
 * 角色菜单关联表(RoleMenus)表服务实现类
 *
 * @author zhudake
 * @since 2024-03-01 19:52:24
 */
@Service("roleMenusService")
@Slf4j
public class RoleMenusServiceImpl extends ServiceImpl<RoleMenusMapper, RoleMenus> implements RoleMenusService {
    @Autowired
    private RoleMenusMapper roleMenusMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    public RoleMenus queryById(Long id){
        return roleMenusMapper.selectById(id);
    }

    /**
     * 分页查询
     *
     * @param pageDTO 筛选条件+分页信息
     * @return 分页查询结果
     */
    public ResponseResult paginQuery(PageDTO<RoleMenus> pageDTO){
        //1. 构建动态查询条件
        LambdaQueryWrapper<RoleMenus> queryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(pageDTO.getQueryConditions().getRemark())){
            queryWrapper.eq(RoleMenus::getRemark, pageDTO.getQueryConditions().getRemark());
        }
        //2. 执行分页查询
        Page<RoleMenus> page = new Page<>();
        page.setSize(pageDTO.getPageSize());
        page.setCurrent(pageDTO.getPageNum());
        page(page, queryWrapper);
        List<RoleMenus> records = page.getRecords();
        log.info("分页查询结果：{}", records);
        PageVO pageVO = new PageVO(page.getTotal(), records,true);
        //3. 返回结果
        return ResponseResult.okResult(pageVO);
    }

    /**
     * 新增数据
     *
     * @param roleMenus 实例对象
     * @return 实例对象
     */
    public RoleMenus insert(RoleMenus roleMenus){
        roleMenusMapper.insert(roleMenus);
        return roleMenus;
    }

    /**
     * 更新数据
     *
     * @param roleMenus 实例对象
     * @return 实例对象
     */
    public RoleMenus update(RoleMenus roleMenus){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<RoleMenus> chainWrapper = new LambdaUpdateChainWrapper<RoleMenus>(roleMenusMapper);
        if(StringUtils.isNotBlank(roleMenus.getRemark())){
            chainWrapper.eq(RoleMenus::getRemark, roleMenus.getRemark());
        }
        //2. 设置主键，并更新
        chainWrapper.set(RoleMenus::getId, roleMenus.getId());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(roleMenus.getId());
        }else{
            return roleMenus;
        }
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    public boolean deleteById(Long id){
        int total = roleMenusMapper.deleteById(id);
        return total > 0;
    }
}

