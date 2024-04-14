package com.wedding.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wedding.domain.ResponseResult;
import com.wedding.domain.dto.ChatMessageDTO;
import com.wedding.domain.dto.PageDTO;
import com.wedding.domain.entity.ChatMessages;
import com.wedding.domain.entity.Users;
import com.wedding.domain.vo.PageVO;
import com.wedding.mapper.ChatMessagesMapper;
import com.wedding.service.ChatMessagesService;
import com.wedding.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 聊天信息表(ChatMessages)表服务实现类
 *
 * @author zhudake
 * @since 2024-03-01 19:52:24
 */
@Service("chatMessagesService")
@Slf4j
public class ChatMessagesServiceImpl extends ServiceImpl<ChatMessagesMapper, ChatMessages> implements ChatMessagesService {
    @Autowired
    private ChatMessagesMapper chatMessagesMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param messageId 主键
     * @return 实例对象
     */
    public ChatMessages queryById(Long messageId) {
        return chatMessagesMapper.selectById(messageId);
    }

    /**
     * 分页查询
     *
     * @param pageDTO 筛选条件+分页信息
     * @return 分页查询结果
     */
    public ResponseResult paginQuery(PageDTO<ChatMessages> pageDTO) {
        //1. 构建动态查询条件
        LambdaQueryWrapper<ChatMessages> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(pageDTO.getQueryConditions().getMessageText())) {
            queryWrapper.eq(ChatMessages::getMessageText, pageDTO.getQueryConditions().getMessageText());
        }
        if (StringUtils.isNotBlank(pageDTO.getQueryConditions().getRemark())) {
            queryWrapper.eq(ChatMessages::getRemark, pageDTO.getQueryConditions().getRemark());
        }
        //2. 执行分页查询
        Page<ChatMessages> page = new Page<>();
        page.setSize(pageDTO.getPageSize());
        page.setCurrent(pageDTO.getPageNum());
        page(page, queryWrapper);
        List<ChatMessages> records = page.getRecords();
        log.info("分页查询结果：{}", records);
        PageVO pageVO = new PageVO(page.getTotal(), records,true);
        //3. 返回结果
        return ResponseResult.okResult(pageVO);
    }

    /**
     * 新增数据
     *
     * @param chatMessages 实例对象
     * @return 实例对象
     */
    public ChatMessages insert(ChatMessages chatMessages) {
        chatMessagesMapper.insert(chatMessages);
        return chatMessages;
    }

    /**
     * 更新数据
     *
     * @param chatMessages 实例对象
     * @return 实例对象
     */
    public ChatMessages update(ChatMessages chatMessages) {
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<ChatMessages> chainWrapper = new LambdaUpdateChainWrapper<ChatMessages>(chatMessagesMapper);
        if (StringUtils.isNotBlank(chatMessages.getMessageText())) {
            chainWrapper.eq(ChatMessages::getMessageText, chatMessages.getMessageText());
        }
        if (StringUtils.isNotBlank(chatMessages.getRemark())) {
            chainWrapper.eq(ChatMessages::getRemark, chatMessages.getRemark());
        }
        //2. 设置主键，并更新
        chainWrapper.set(ChatMessages::getMessageId, chatMessages.getMessageId());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if (ret) {
            return queryById(chatMessages.getMessageId());
        } else {
            return chatMessages;
        }
    }

    /**
     * 通过主键删除数据
     *
     * @param messageId 主键
     * @return 是否成功
     */
    public boolean deleteById(Long messageId) {
        int total = chatMessagesMapper.deleteById(messageId);
        return total > 0;
    }

    /**
     * 查询聊天记录
     * @param chatMessageDTO
     * @return
     */
    @Override
    public List<ChatMessages> getChatMessages(ChatMessageDTO chatMessageDTO) {
        log.info("--------------getChatMessages:{}--------------", chatMessageDTO);

        LambdaQueryWrapper<ChatMessages> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ChatMessages::getDelFlag, 0) // 添加删除标志条件
                .and(wrapper -> wrapper
                        .eq(ChatMessages::getSenderId, chatMessageDTO.getSenderId())
                        .eq(ChatMessages::getReceiverId, chatMessageDTO.getReceiverId())
                        .eq(ChatMessages::getStudioId, chatMessageDTO.getStudioId())
                )
                .or(wrapper -> wrapper
                        .eq(ChatMessages::getSenderId, chatMessageDTO.getReceiverId())
                        .eq(ChatMessages::getReceiverId, chatMessageDTO.getSenderId())
                        .eq(ChatMessages::getStudioId, chatMessageDTO.getStudioId())
                );

        List<ChatMessages> chatMessagesList = list(queryWrapper);
        List<ChatMessages> chatMessages = setAvatar(chatMessagesList, chatMessageDTO);
        return chatMessages;
    }


    @Autowired
    private UsersService usersService;
    // 设置用户头像
    private List<ChatMessages> setAvatar(List<ChatMessages> chatMessagesList,ChatMessageDTO chatMessageDTO) {
        LambdaQueryWrapper<Users> queryWrapper = new LambdaQueryWrapper<>();
        String senderAvatar =null;
        String receiverAvatar =null;

        if (chatMessageDTO.getSenderId()!=null){
            queryWrapper.eq(Users::getId,chatMessageDTO.getSenderId());
             senderAvatar = usersService.getOne(queryWrapper).getAvatar();
             queryWrapper.clear();
        }
        if (chatMessageDTO.getReceiverId()!=null){
            queryWrapper.eq(Users::getId,chatMessageDTO.getReceiverId());
             receiverAvatar = usersService.getOne(queryWrapper).getAvatar();
             queryWrapper.clear();
        }
        for (ChatMessages chatMessages : chatMessagesList) {
            chatMessages.setSenderAvatar(senderAvatar);
            chatMessages.setReceiverAvatar(receiverAvatar);
        }
        return  chatMessagesList;


    }
}

