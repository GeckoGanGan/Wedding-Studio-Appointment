package com.wedding.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wedding.constants.SysConstants;
import com.wedding.domain.ResponseResult;
import com.wedding.domain.dto.PageDTO;
import com.wedding.domain.entity.Collect;
import com.wedding.domain.entity.Photographers;
import com.wedding.domain.entity.Studios;
import com.wedding.domain.enums.AppHttpCodeEnum;
import com.wedding.domain.vo.PageVO;
import com.wedding.exception.SystemException;
import com.wedding.mapper.CollectMapper;
import com.wedding.mapper.PhotographersMapper;
import com.wedding.service.CollectService;
import com.wedding.service.PhotographersService;
import com.wedding.service.StudiosService;
import com.wedding.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 收藏表(Collect)表服务实现类
 *
 * @author zhudake
 * @since 2024-04-06 21:33:47
 */
@Service("collectService")
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class CollectServiceImpl extends ServiceImpl<CollectMapper, Collect> implements CollectService {
    @Autowired
    private StudiosService studiosService;
    @Autowired
    private PhotographersService photographersService;
    @Autowired
    private PhotographersMapper photographersMapper;

    @Override
    public ResponseResult getCollectByUserId(PageDTO<Collect> collectPageDTO) {

        String type = collectPageDTO.getQueryConditions().getType();
        // 想获取当前登录用户id
        Long userId = null;
        try {
            userId = SecurityUtils.getUserId();
        }catch (Exception e){
            return ResponseResult.okResult("请先登录！");
        }
        LambdaQueryWrapper<Collect> countWrapper = new LambdaQueryWrapper<>();
        countWrapper.eq(Collect::getUserId,userId);
        boolean hasData = count(countWrapper) > 0;
        log.info("---------------------------hasData:{}",hasData);

        LambdaQueryWrapper<Collect> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(type),Collect::getType,type);
        queryWrapper.eq(Collect::getUserId,userId);
        queryWrapper.like(StringUtils.isNotBlank(collectPageDTO.getQueryConditions().getName()),Collect::getName,collectPageDTO.getQueryConditions().getName());
        queryWrapper.orderByDesc(Collect::getCreatedTime);
        // 分页设置
        Page<Collect> page = new Page<>();
        page.setCurrent(collectPageDTO.getPageNum());
        page.setSize(collectPageDTO.getPageSize());
        page.setSearchCount(true);
        // 分页查询
        page(page,queryWrapper);
        PageVO pageVO = new PageVO();
        pageVO.setTotal(page.getTotal());
        pageVO.setRecords(page.getRecords());
        pageVO.setHasData(hasData);
        return ResponseResult.okResult(setStudiosAndPhotographersInfo(pageVO,type,hasData));
    }

    /**
     * 取消收藏
     * @param collectId
     * @return
     */
    @Override
    public ResponseResult collectCancel(Long collectId) {
        // 想获取当前登录用户id
        Long userId = null;
        try {
            userId = SecurityUtils.getUserId();
        }catch (Exception e){
            return ResponseResult.okResult("请先登录！");
        }
        LambdaQueryWrapper<Collect> deleteWrapper = new LambdaQueryWrapper<>();
        deleteWrapper.eq(Collect::getUserId,userId);
        deleteWrapper.eq(Collect::getCollectId,collectId);
        remove(deleteWrapper);

        return ResponseResult.okResult("取消收藏成功！");
    }

    /**
     * 设置摄影师信息和影楼信息
     * @param pageVO
     * @param type
     * @return
     */
    private PageVO setStudiosAndPhotographersInfo(PageVO pageVO, String type,boolean hasData){
        // 想获取当前登录用户id
        Long userId = null;
        try {
            userId = SecurityUtils.getUserId();
        }catch (Exception e){
            throw new SystemException(AppHttpCodeEnum.NEED_LOGIN);
        }

        List<Collect> records = pageVO.getRecords();
        PageVO vo = new PageVO();
        vo.setHasData(hasData);
        if (SysConstants.COLLECT_STUDIOS.equals(type)){
            List<Long> studioIdList = records.stream()
                    .filter(collect -> collect.getType().equals(SysConstants.COLLECT_STUDIOS))
                    .map(Collect::getCollectId)
                    .collect(Collectors.toList());
            if (studioIdList!=null&&!studioIdList.isEmpty()){
                // 查询影楼信息
                LambdaQueryWrapper<Studios> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.in(Studios::getId,studioIdList);
                List<Studios> studiosList = studiosService.list(queryWrapper);
                vo.setRecords(studiosList);
                vo.setTotal(pageVO.getTotal());
            }
        }else {
            List<Long> photographerIdList = records.stream()
                    .filter(collect -> collect.getType().equals(SysConstants.COLLECT_PHOTOGRAPHERS))
                    .map(Collect::getCollectId)
                    .collect(Collectors.toList());
            if (photographerIdList!=null&&!photographerIdList.isEmpty()){
                // 查询摄影师信息
                LambdaQueryWrapper<Photographers> wrapper = new LambdaQueryWrapper<>();
                // 摄影师的状态必须是启用的
                wrapper .eq(Photographers::getStatus,SysConstants.PHOTOGRAPHER_STATUS_ENABLE);
                wrapper.in(Photographers::getId,photographerIdList);
                List<Long> photographerIds = photographersService.list(wrapper).stream().map(Photographers::getId).collect(Collectors.toList());
                List<Photographers> photographersByPhotographerIds  = photographersMapper.getPhotographersByPhotographerIds(photographerIds);
                for (Photographers photographersByPhotographerId : photographersByPhotographerIds) {
                    log.info("-------------------:{}",photographersByPhotographerId);
                }
                vo.setRecords(photographersByPhotographerIds);
                vo.setTotal(pageVO.getTotal());
            }
        }
        return vo;
    }


}

