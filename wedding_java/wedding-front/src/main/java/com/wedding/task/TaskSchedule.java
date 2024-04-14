package com.wedding.task;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wedding.constants.SysConstants;
import com.wedding.domain.entity.Photographers;
import com.wedding.domain.entity.Studios;
import com.wedding.service.PhotographersService;
import com.wedding.service.StudiosService;
import com.wedding.utils.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhuqi
 * @Date: 2024/03/29/17:41
 * @Description: 定时任务类
 */

@Component
@Slf4j
public class TaskSchedule {
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private PhotographersService photographersService;

    @Autowired
    private StudiosService studiosService;

    /**
     * 摄影师的点赞量入库
     */
    @Scheduled(cron = "0 */10 * * * ?")
    public void storagePhotographerLikes() {
        log.info("开始执行 storagePhotographerLikes() 定时任务");
        Map<String, Integer> cacheMap = redisCache.getCacheMap(SysConstants.PHOTOGRAPHERS_LIKE);
        List<Long> photographersIdList = new ArrayList<>();
        Map<Long, Long> map = new HashMap<>();
        cacheMap.forEach((k, v) -> {
            Long photographerId = Long.valueOf(k);
            Long likes = v.longValue();
            map.put(photographerId, likes);
            photographersIdList.add(photographerId);
        });

        LambdaQueryWrapper<Photographers> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Photographers::getId, photographersIdList);
        List<Photographers> photographersList = photographersService.list(queryWrapper);

        photographersList.forEach(photographers -> photographers.setLikes(map.get(photographers.getId())));
        photographersService.updateBatchById(photographersList);
        log.info("结束执行 storagePhotographerLikes() 定时任务");
    }

    /**
     * 摄影师的点赞量入库
     */
    @Scheduled(cron = "0 */10 * * * ?")
    public void storageStudiosData() {
        log.info("开始执行 storageStudiosData() 定时任务");
        Map<String, Integer> viewCountMap = redisCache.getCacheMap(SysConstants.STUDIOS_VIEW);
        Map<String, Integer> collectMap = redisCache.getCacheMap(SysConstants.STUDIOS_COLLECT);

        List<Studios> studiosList = studiosService.list();

        studiosList.forEach(studios -> {
            String studiosId = studios.getId().toString();
            studios.setViewCount(Long.valueOf(viewCountMap.get(studiosId)));
            studios.setCollectCount(Long.valueOf(collectMap.get(studiosId)));
        });
        studiosService.updateBatchById(studiosList);
        log.info("结束执行 storageStudiosData() 定时任务");
    }

}
