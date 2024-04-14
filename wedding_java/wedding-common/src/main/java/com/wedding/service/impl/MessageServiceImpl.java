package com.wedding.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wedding.domain.ResponseResult;
import com.wedding.domain.dto.PageDTO;
import com.wedding.domain.entity.Message;
import com.wedding.domain.enums.AppHttpCodeEnum;
import com.wedding.domain.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wedding.service.MessageService;
import com.wedding.mapper.MessageMapper;

/**
 * 留言表(Message)表服务实现类
 *
 * @author zhudake
 * @since 2024-03-24 18:58:59
 */
@Service("messageService")
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {
    @Autowired
    private MessageMapper messageMapper;


    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    public ResponseResult queryById(Long id) {
        Message message = getBaseMapper().selectById(id);
        return ResponseResult.okResult(message);
    }

    /**
     * 分页查询
     *
     * @param pageDTO 筛选条件
     * @return
     */
    public ResponseResult paginQuery(PageDTO<Message> pageDTO) {
        //1. 构建动态查询条件
        LambdaQueryWrapper<Message> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(pageDTO.getQueryConditions().getEmail())) {
            queryWrapper.eq(Message::getEmail, pageDTO.getQueryConditions().getEmail());
        }
        if (StringUtils.isNotBlank(pageDTO.getQueryConditions().getContent())) {
            queryWrapper.eq(Message::getContent, pageDTO.getQueryConditions().getContent());
        }
        if (StringUtils.isNotBlank(pageDTO.getQueryConditions().getRemark())) {
            queryWrapper.eq(Message::getRemark, pageDTO.getQueryConditions().getRemark());
        }
        //2. 执行分页查询
        Page<Message> page = new Page<>(pageDTO.getPageNum(), pageDTO.getPageSize(), true);
        page(page, queryWrapper);
        PageVO pageVO = new PageVO();
        pageVO.setRecords(page.getRecords());
        pageVO.setTotal(page.getTotal());
        //3. 返回结果
        return ResponseResult.okResult(pageVO);
    }

    /**
     * 新增数据
     *
     * @param message 实例对象
     * @return 实例对象
     */
    public ResponseResult insert(Message message) {
        messageMapper.insert(message);
        return ResponseResult.okResult();
    }

    /**
     * 更新数据
     *
     * @param message 实例对象
     * @return 实例对象
     */
    public ResponseResult update(Message message) {
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<Message> chainWrapper = new LambdaUpdateChainWrapper<Message>(messageMapper);
        if (StringUtils.isNotBlank(message.getEmail())) {
            chainWrapper.eq(Message::getEmail, message.getEmail());
        }
        if (StringUtils.isNotBlank(message.getContent())) {
            chainWrapper.eq(Message::getContent, message.getContent());
        }
        if (StringUtils.isNotBlank(message.getRemark())) {
            chainWrapper.eq(Message::getRemark, message.getRemark());
        }
        //2. 设置主键，并更新
        chainWrapper.set(Message::getId, message.getId());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最对象返回
        if (ret) {
            return ResponseResult.okResult("更新成功");
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_ERROR);
        }
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    public ResponseResult deleteById(Long id) {
        int total = messageMapper.deleteById(id);
        if (total > 0){
            return ResponseResult.okResult("删除成功");
        }else {
            return ResponseResult.errorResult(AppHttpCodeEnum.DELETE_ERROR);
        }
    }

}

