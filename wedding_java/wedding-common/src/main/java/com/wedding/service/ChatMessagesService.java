package com.wedding.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wedding.domain.ResponseResult;
import com.wedding.domain.dto.ChatMessageDTO;
import com.wedding.domain.dto.PageDTO;
import com.wedding.domain.entity.ChatMessages;

import java.util.List;


/**
 * 聊天信息表(ChatMessages)表服务接口
 *
 * @author zhudake
 * @since 2024-03-01 19:52:24
 */
public interface ChatMessagesService extends IService<ChatMessages> {
    /**
     * 通过ID查询单条数据
     *
     * @param messageId 主键
     * @return 实例对象
     */
    ChatMessages queryById(Long messageId);

    /**
     * 分页查询
     *
     * @param pageDTO 筛选条件+分页信息
     * @return 分页查询结果
     */
    ResponseResult paginQuery(PageDTO<ChatMessages> pageDTO);
    /**
     * 新增数据
     *
     * @param chatMessages 实例对象
     * @return 实例对象
     */
    ChatMessages insert(ChatMessages chatMessages);
    /**
     * 更新数据
     *
     * @param chatMessages 实例对象
     * @return 实例对象
     */
    ChatMessages update(ChatMessages chatMessages);
    /**
     * 通过主键删除数据
     *
     * @param messageId 主键
     * @return 是否成功
     */
    boolean deleteById(Long messageId);

    // 查询聊天记录
    List<ChatMessages> getChatMessages(ChatMessageDTO chatMessageDTO);
}

