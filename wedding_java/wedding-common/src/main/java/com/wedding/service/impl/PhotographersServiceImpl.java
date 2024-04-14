package com.wedding.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wedding.constants.SysConstants;
import com.wedding.domain.ResponseResult;
import com.wedding.domain.dto.PageDTO;
import com.wedding.domain.entity.Photographers;
import com.wedding.domain.entity.Samples;
import com.wedding.domain.vo.PageVO;
import com.wedding.mapper.PhotographersMapper;
import com.wedding.service.PhotographersService;
import com.wedding.utils.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 摄影师表(Photographers)表服务实现类
 *
 * @author zhudake
 * @since 2024-03-01 19:52:23
 */
@Service("photographersService")
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class PhotographersServiceImpl extends ServiceImpl<PhotographersMapper, Photographers> implements PhotographersService {
    @Autowired
    private PhotographersMapper photographersMapper;
    @Autowired
    private RedisCache redisCache;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    public Photographers queryById(Long id){
        return photographersMapper.selectById(id);
    }

    /**
     * 分页查询
     *
     * @param pageDTO 筛选条件+分页信息
     * @return 分页查询结果
     */
    public ResponseResult paginQuery(PageDTO<Photographers> pageDTO){
        //1. 构建动态查询条件
        LambdaQueryWrapper<Photographers> queryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(pageDTO.getQueryConditions().getPhotographerName())){
            queryWrapper.eq(Photographers::getPhotographerName, pageDTO.getQueryConditions().getPhotographerName());
        }
        if(StringUtils.isNotBlank(pageDTO.getQueryConditions().getStyle())){
            queryWrapper.eq(Photographers::getStyle, pageDTO.getQueryConditions().getStyle());
        }
        if(StringUtils.isNotBlank(pageDTO.getQueryConditions().getPhotographerEmail())){
            queryWrapper.eq(Photographers::getPhotographerEmail, pageDTO.getQueryConditions().getPhotographerEmail());
        }
        if(StringUtils.isNotBlank(pageDTO.getQueryConditions().getStatus())){
            queryWrapper.eq(Photographers::getStatus, pageDTO.getQueryConditions().getStatus());
        }
        if(StringUtils.isNotBlank(pageDTO.getQueryConditions().getRemark())){
            queryWrapper.eq(Photographers::getRemark, pageDTO.getQueryConditions().getRemark());
        }
        //2. 执行分页查询
        Page<Photographers> page = new Page<>();
        page.setSize(pageDTO.getPageSize());
        page.setCurrent(pageDTO.getPageNum());
        page(page, queryWrapper);
        List<Photographers> records = page.getRecords();
        log.info("分页查询结果：{}", records);
        PageVO pageVO = new PageVO(page.getTotal(), records,true);
        //3. 返回结果
        return ResponseResult.okResult(pageVO);
    }

    /**
     * 新增数据
     *
     * @param photographers 实例对象
     * @return 实例对象
     */
    public Photographers insert(Photographers photographers){
        photographersMapper.insert(photographers);
        return photographers;
    }

    /**
     * 更新数据
     *
     * @param photographers 实例对象
     * @return 实例对象
     */
    public Photographers update(Photographers photographers){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<Photographers> chainWrapper = new LambdaUpdateChainWrapper<>(photographersMapper);
        if(StringUtils.isNotBlank(photographers.getPhotographerName())){
            chainWrapper.eq(Photographers::getPhotographerName, photographers.getPhotographerName());
        }
        if(StringUtils.isNotBlank(photographers.getStyle())){
            chainWrapper.eq(Photographers::getStyle, photographers.getStyle());
        }
        if(StringUtils.isNotBlank(photographers.getPhotographerEmail())){
            chainWrapper.eq(Photographers::getPhotographerEmail, photographers.getPhotographerEmail());
        }
        if(StringUtils.isNotBlank(photographers.getStatus())){
            chainWrapper.eq(Photographers::getStatus, photographers.getStatus());
        }
        if(StringUtils.isNotBlank(photographers.getRemark())){
            chainWrapper.eq(Photographers::getRemark, photographers.getRemark());
        }
        //2. 设置主键，并更新
        chainWrapper.set(Photographers::getId, photographers.getId());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(photographers.getId());
        }else{
            return photographers;
        }
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    public boolean deleteById(Long id){
        int total = photographersMapper.deleteById(id);
        return total > 0;
    }

    /**
     * 获取摄影师列表和摄影师的作品列表
     * @return
     */
    @Override
    public ResponseResult listPhotographers() {
        List<Photographers> photographers = photographersMapper.getPhotographersList();
        // 查询点赞量
        Map<String, Integer> photograhperIdAndLikeMap = redisCache.getCacheMap(SysConstants.PHOTOGRAPHERS_LIKE);
        photograhperIdAndLikeMap.forEach((k,v) ->{
            log.info("-------------------------------------------------photograhperIdAndLikeMap k:{} v:{}",k,v);
        });
        for (Photographers photographer : photographers) {
            Integer likes = photograhperIdAndLikeMap.get(photographer.getId().toString());
            photographer.setLikes(likes.longValue());
        }
        return  ResponseResult.okResult(photographers);

    }

    /**
     * 点赞操作
     * @param id
     * @return
     */
    @Override
    public ResponseResult like(String id) {
        log.info("--------------------like:{}",id);
        redisCache.incrementCacheMapValue(SysConstants.PHOTOGRAPHERS_LIKE,id,1);
        return ResponseResult.okResult();
    }

}

