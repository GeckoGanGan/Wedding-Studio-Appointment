package com.wedding.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wedding.constants.SysConstants;
import com.wedding.domain.ResponseResult;
import com.wedding.domain.dto.CollectDTO;
import com.wedding.domain.dto.PageDTO;
import com.wedding.domain.entity.*;
import com.wedding.domain.enums.AppHttpCodeEnum;
import com.wedding.domain.vo.PageVO;
import com.wedding.domain.vo.PhotographerVO;
import com.wedding.domain.vo.StudiosDetailsVO;
import com.wedding.mapper.SamplesMapper;
import com.wedding.mapper.StudiosMapper;
import com.wedding.service.*;
import com.wedding.utils.BeanCopyUtils;
import com.wedding.utils.RedisCache;
import com.wedding.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 影楼表(Studios)表服务实现类
 *
 * @author zhudake
 * @since 2024-03-01 19:52:25
 */
@Slf4j
@Service("studiosService")
@Transactional(rollbackFor = Exception.class)
public class StudiosServiceImpl extends ServiceImpl<StudiosMapper, Studios> implements StudiosService {

    @Autowired
    private StudiosMapper studiosMapper;
    @Autowired
    private StudioPhotographersService studioPhotographersService;
    @Autowired
    private PhotographersService photographersService;
    @Autowired
    private SamplesMapper samplesMapper;
    @Autowired
    private CommentService commentService;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private CollectService collectService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    public Studios queryById(Long id){
        return studiosMapper.selectById(id);
    }

    /**
     * 分页查询
     * @param pageDTO  分页大小 + 查询条件
     * @return 分页查询结果
     */

    public ResponseResult paginQuery(PageDTO<Studios> pageDTO){

        //1. 构建动态查询条件
        LambdaQueryWrapper<Studios> queryWrapper = new LambdaQueryWrapper<>();
        // 通过分数降序排序
        queryWrapper.orderByDesc(Studios::getRate);
        if(StringUtils.isNotBlank(pageDTO.getQueryConditions().getStudioName())){
            queryWrapper.like(Studios::getStudioName, pageDTO.getQueryConditions().getStudioName());
        }
        if(StringUtils.isNotBlank(pageDTO.getQueryConditions().getStudioLocation())){
            queryWrapper.like(Studios::getStudioLocation, pageDTO.getQueryConditions().getStudioLocation());
        }
        if(StringUtils.isNotBlank(pageDTO.getQueryConditions().getStudioService())){
            queryWrapper.eq(Studios::getStudioService, pageDTO.getQueryConditions().getStudioService());
        }
        if(StringUtils.isNotBlank(pageDTO.getQueryConditions().getStatus())){
            queryWrapper.eq(Studios::getStatus, pageDTO.getQueryConditions().getStatus());
        }
        if(StringUtils.isNotBlank(pageDTO.getQueryConditions().getRemark())){
            queryWrapper.eq(Studios::getRemark, pageDTO.getQueryConditions().getRemark());
        }
        //2. 执行分页查询
        Page<Studios> page = new Page<>();
        page.setCurrent(pageDTO.getPageNum());
        page.setSize(pageDTO.getPageSize());
        page(page,queryWrapper);
        List<Studios> records = page.getRecords();
        // 设置浏览量和收藏量
        setViewCountAndCollect(records);
        PageVO pageVO = new PageVO(page.getTotal(), records,true);
        //3. 返回结果
        return ResponseResult.okResult(pageVO);
    }

    /**
     * 新增数据
     *
     * @param studios 实例对象
     * @return 实例对象
     */
    public Studios insert(Studios studios){
        studiosMapper.insert(studios);
        return studios;
    }

    /**
     * 更新数据
     *
     * @param studios 实例对象
     * @return 实例对象
     */
    public Studios update(Studios studios){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<Studios> chainWrapper = new LambdaUpdateChainWrapper<>(studiosMapper);
        if(StringUtils.isNotBlank(studios.getStudioName())){
            chainWrapper.eq(Studios::getStudioName, studios.getStudioName());
        }
        if(StringUtils.isNotBlank(studios.getStudioLocation())){
            chainWrapper.eq(Studios::getStudioLocation, studios.getStudioLocation());
        }
        if(StringUtils.isNotBlank(studios.getStudioService())){
            chainWrapper.eq(Studios::getStudioService, studios.getStudioService());
        }
        if(StringUtils.isNotBlank(studios.getStatus())){
            chainWrapper.eq(Studios::getStatus, studios.getStatus());
        }
        if(StringUtils.isNotBlank(studios.getRemark())){
            chainWrapper.eq(Studios::getRemark, studios.getRemark());
        }
        //2. 设置主键，并更新
        chainWrapper.set(Studios::getId, studios.getId());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(studios.getId());
        }else{
            return studios;
        }
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    public boolean deleteById(Long id){
        int total = studiosMapper.deleteById(id);
        return total > 0;
    }

    @Override
    public ResponseResult getStudioDetails(Long id) {
        log.info("----------------------studioId:{}",id);

        String studioId = id.toString();
        // 浏览量增加
        redisCache.incrementCacheMapValue(SysConstants.STUDIOS_VIEW,studioId,1);
        // 先查询影楼的信息
        LambdaQueryWrapper<Studios> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Studios::getId, id);
        // 影楼的状态必须是正常的
        wrapper.eq(Studios::getStatus, SysConstants.STUDIO_STATUS_NORMAL);
        Studios studio = getOne(wrapper);
        // 设置浏览量和收藏量
        List<Studios> studiosList = new ArrayList<>();
        studiosList.add(studio);
        setViewCountAndCollect(studiosList);
        // Bean拷贝
        StudiosDetailsVO studiosDetailsVO = BeanCopyUtils.copyBean(studio, StudiosDetailsVO.class);
        // 查询影楼的图片列表
        List<String> images = samplesMapper.selectList(new LambdaQueryWrapper<Samples>().eq(Samples::getStudioId, id).eq(Samples::getType, 0))
                .stream()
                .map(Samples::getImageUrl)
                .collect(Collectors.toList());
        studiosDetailsVO.setImages(images);
        // 查询该影楼的评价数量
        LambdaQueryWrapper<Comment> studioCommentLambdaQueryWrapper = new LambdaQueryWrapper<>();
        studioCommentLambdaQueryWrapper.eq(Comment::getStudioId,id);
        int count = commentService.count(studioCommentLambdaQueryWrapper);
        studiosDetailsVO.setRateCount(count);
        // 查询该影楼下的摄影师信息
        LambdaQueryWrapper<StudioPhotographers> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StudioPhotographers::getStudioId, id);
        // 获取摄影师id
        List<Long> PhotograperIdList = studioPhotographersService.list(queryWrapper)
                .stream()
                .map(StudioPhotographers::getPhotographerId)
                .collect(Collectors.toList());
        QueryWrapper<Photographers> photographersQueryWrapper = new QueryWrapper<>();
        // 摄影师必须是启用后状态
        photographersQueryWrapper.eq("status", SysConstants.PHOTOGRAPHER_STATUS_ENABLE);
        photographersQueryWrapper.in("id", PhotograperIdList);

        List<PhotographerVO> photographerVOS = photographersService.list(photographersQueryWrapper).stream()
                .map(photographers -> {
                    PhotographerVO photographerVO = new PhotographerVO();
                    photographerVO.setPhotographerName(photographers.getPhotographerName());
                    photographerVO.setStyle(photographers.getStyle());
                    photographerVO.setAge(photographers.getAge());
                    photographerVO.setPhotographerEmail(photographers.getPhotographerEmail());
                    photographerVO.setDescription(photographers.getDescription());
                    photographerVO.setSex(photographers.getSex());
                    photographerVO.setPhoto(photographers.getPhoto());
                    return photographerVO;
                })
                .collect(Collectors.toList());
        // 封装结果并返回
        studiosDetailsVO.setPhotographerVOList(photographerVOS);
        return ResponseResult.okResult(studiosDetailsVO);
    }

    @Override
    public ResponseResult view(String id) {
        log.info("--------------------view:{}",id);
        redisCache.incrementCacheMapValue(SysConstants.STUDIOS_VIEW,id,1);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult collect(CollectDTO param) {
        // 获取当前登录用户id
        Long userId = null;
        try {
            userId = SecurityUtils.getUserId();
        }catch (Exception e){
            return ResponseResult.okResult("请先登录！");
        }
        // 不可重复收藏
        LambdaQueryWrapper<Collect> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Collect::getUserId,userId);
        queryWrapper.eq(Collect::getType,param.getType());
        queryWrapper.eq(Collect::getCollectId,param.getId());
        if(collectService.count(queryWrapper)>0){
            return ResponseResult.okResult(AppHttpCodeEnum.NOT_ALLOW_COLLECT_REPEATABLE);
        }
        redisCache.incrementCacheMapValue(SysConstants.STUDIOS_COLLECT,param.getId().toString(),1);
        Collect collect = new Collect();
        collect.setType(param.getType());
        collect.setCollectId(param.getId());
        collect.setName(param.getName());
        collect.setUserId(userId);
        collectService.save(collect);
        return ResponseResult.okResult("收藏成功~");
    }

    /**
     * 热榜查询
     * @param type
     * @return
     */
    @Override
    public ResponseResult hot(String type) {
        LambdaQueryWrapper<Studios> queryWrapper = new LambdaQueryWrapper<>();
        // 根据浏览量排行
        if (SysConstants.HOT_TYPE_VIEWCOUNT.equals(type)){
            queryWrapper.orderByDesc(Studios::getViewCount);
        }
        // 按照评分排行
        if (SysConstants.HOT_TYPE_POINT.equals(type)){
            queryWrapper.orderByDesc(Studios::getRate);
        }
        return ResponseResult.okResult(list(queryWrapper));
    }

    /**
     * 获取影楼的浏览量和收藏量
     * @param studiosList
     */
    private void   setViewCountAndCollect(List<Studios> studiosList) {
        // 收藏map
        Map<String, Integer> collectMap = redisCache.getCacheMap(SysConstants.STUDIOS_COLLECT);
        // 浏览量map
        Map<String, Integer> viewCountMap = redisCache.getCacheMap(SysConstants.STUDIOS_VIEW);
        for (Studios studios : studiosList) {
            String key = studios.getId().toString();
            studios.setCollectCount(Long.valueOf(collectMap.get(key)));
            studios.setViewCount(Long.valueOf(viewCountMap.get(key)));
        }
    }
}

