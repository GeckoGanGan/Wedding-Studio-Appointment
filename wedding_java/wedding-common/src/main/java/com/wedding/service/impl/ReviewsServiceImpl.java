package com.wedding.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wedding.domain.ResponseResult;
import com.wedding.domain.dto.PageDTO;
import com.wedding.domain.entity.Reviews;
import com.wedding.domain.vo.PageVO;
import com.wedding.mapper.ReviewsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import  com.wedding.service.ReviewsService;

import java.util.List;

/**
 * 评价表(Reviews)表服务实现类
 *
 * @author zhudake
 * @since 2024-03-01 19:52:24
 */
@Service("reviewsService")
@Slf4j
public class ReviewsServiceImpl extends ServiceImpl<ReviewsMapper, Reviews> implements ReviewsService {
    @Autowired
    private ReviewsMapper reviewsMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    public Reviews queryById(Long id){
        return reviewsMapper.selectById(id);
    }

    /**
     * 分页查询
     *
     * @param pageDTO 筛选条件+分页信息
     * @return 分页查询结果
     */
    public ResponseResult paginQuery(PageDTO<Reviews> pageDTO){
        //1. 构建动态查询条件
        LambdaQueryWrapper<Reviews> queryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(pageDTO.getQueryConditions().getType())){
            queryWrapper.eq(Reviews::getType, pageDTO.getQueryConditions().getType());
        }
        if(StringUtils.isNotBlank(pageDTO.getQueryConditions().getContent())){
            queryWrapper.eq(Reviews::getContent, pageDTO.getQueryConditions().getContent());
        }
        if(StringUtils.isNotBlank(pageDTO.getQueryConditions().getRemark())){
            queryWrapper.eq(Reviews::getRemark, pageDTO.getQueryConditions().getRemark());
        }
        //2. 执行分页查询
        Page<Reviews> page = new Page<>();
        page.setSize(pageDTO.getPageSize());
        page.setCurrent(pageDTO.getPageNum());
        page(page, queryWrapper);
        List<Reviews> records = page.getRecords();
        log.info("分页查询结果：{}", records);
        PageVO pageVO = new PageVO(page.getTotal(), records,true);
        //3. 返回结果
        return ResponseResult.okResult(pageVO);
    }

    /**
     * 新增数据
     *
     * @param reviews 实例对象
     * @return 实例对象
     */
    public Reviews insert(Reviews reviews){
        reviewsMapper.insert(reviews);
        return reviews;
    }

    /**
     * 更新数据
     *
     * @param reviews 实例对象
     * @return 实例对象
     */
    public Reviews update(Reviews reviews){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<Reviews> chainWrapper = new LambdaUpdateChainWrapper<Reviews>(reviewsMapper);
        if(StringUtils.isNotBlank(reviews.getType())){
            chainWrapper.eq(Reviews::getType, reviews.getType());
        }
        if(StringUtils.isNotBlank(reviews.getContent())){
            chainWrapper.eq(Reviews::getContent, reviews.getContent());
        }
        if(StringUtils.isNotBlank(reviews.getRemark())){
            chainWrapper.eq(Reviews::getRemark, reviews.getRemark());
        }
        //2. 设置主键，并更新
        chainWrapper.set(Reviews::getId, reviews.getId());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(reviews.getId());
        }else{
            return reviews;
        }
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    public boolean deleteById(Long id){
        int total = reviewsMapper.deleteById(id);
        return total > 0;
    }
}

