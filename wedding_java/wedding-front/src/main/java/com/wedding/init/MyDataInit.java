package com.wedding.init;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wedding.constants.SysConstants;
import com.wedding.domain.entity.Photographers;
import com.wedding.domain.entity.Studios;
import com.wedding.service.PhotographersService;
import com.wedding.service.StudiosService;
import com.wedding.utils.RedisCache;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhuqi
 * @Date: 2024/04/02/22:34
 * @Description: 项目数据初始化到redis
 */
@Component
@Slf4j
public class MyDataInit implements CommandLineRunner {
    @Autowired
    private   RedisCache redisCache;
    @Autowired
    private PhotographersService photographersService;
    @Autowired
    private StudiosService studiosService;
    @Override
    public void run(String... args) {
      photographerLikeInit();
      studiosDataInit();
    }

    /**
     *  摄影师的id和点赞量存入redis
     */
    private void photographerLikeInit(){
        log.info("--------------------开始初始化摄影师点赞量--------------------");
        LambdaQueryWrapper<Photographers> queryWrapper = new LambdaQueryWrapper<>();
        // 摄影师的状态必须是未停用的
        queryWrapper.eq(Photographers::getStatus, SysConstants.PHOTOGRAPHER_STATUS_ENABLE);
        // 获取所有的摄影师的id和点赞量的映射关系
        Map<String, Integer> phototrapherIdAndLikeMap = photographersService.list(queryWrapper).stream()
                .collect(Collectors.toMap(photographers -> photographers.getId().toString(), photographers -> photographers.getLikes().intValue()));
        redisCache.setCacheMap(SysConstants.PHOTOGRAPHERS_LIKE, phototrapherIdAndLikeMap);
    }

    /**
     * 影楼的id和收藏量以及浏览量
     */
    private void studiosDataInit(){
        log.info("--------------------开始初始化影楼数据--------------------");
        LambdaQueryWrapper<Studios> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Studios::getStatus,SysConstants.STUDIO_STATUS_NORMAL);

       Map<String,Integer> studioIdAndCollectCountMap = new HashMap<>();
       Map<String,Integer> studioIdAndViewCountMap = new HashMap<>();
       List<Studios> studiosList = studiosService.list(wrapper);
       if (studiosList==null||studiosList.isEmpty()){
           return;
       }
       studiosList.forEach(studios -> {
           studioIdAndCollectCountMap.put(studios.getId().toString(),studios.getCollectCount().intValue());
           studioIdAndViewCountMap.put(studios.getId().toString(),studios.getViewCount().intValue());
       });
       redisCache.setCacheMap(SysConstants.STUDIOS_COLLECT, studioIdAndCollectCountMap);
       redisCache.setCacheMap(SysConstants.STUDIOS_VIEW, studioIdAndViewCountMap);

    }


}
