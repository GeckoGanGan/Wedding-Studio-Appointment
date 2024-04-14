package com.wedding.controller;

import com.wedding.domain.ResponseResult;
import com.wedding.domain.dto.ChatMessageDTO;
import com.wedding.domain.dto.PageDTO;
import com.wedding.domain.entity.ChatMessages;
import com.wedding.service.ChatMessagesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 聊天信息表;(chat_messages)表控制层
 * @author : zhudake
 * @date : 2024-3-9
 */
@Api(tags = "聊天信息表对象功能接口")
@RestController
@RequestMapping("/chat")
@Slf4j
public class ChatMessagesController{
    @Autowired
    private ChatMessagesService chatMessagesService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param messageId 主键
     * @return 实例对象
     */
    @ApiOperation("通过ID查询单条数据")
    @GetMapping("{messageid}")
    public ResponseEntity<ChatMessages> queryById(Long messageId){
        return ResponseEntity.ok(chatMessagesService.queryById(messageId));
    }

     /**
      * 分页查询
      *
      * @param queryParam 筛选条件
      * @return 查询结果
      */
     @ApiOperation("分页查询")
     @PostMapping("/page")
     public ResponseResult paginQuery(@RequestBody PageDTO<ChatMessages> queryParam){
         log.info("-----------进入studiosController.paginQuery方法-----------:{}",queryParam);
         return chatMessagesService.paginQuery(queryParam);
     }
    
    /** 
     * 新增数据
     *
     * @param chatMessages 实例对象
     * @return 实例对象
     */
    @ApiOperation("新增数据")
    @PostMapping
    public ResponseEntity<ChatMessages> add(ChatMessages chatMessages){
        return ResponseEntity.ok(chatMessagesService.insert(chatMessages));
    }
    
    /** 
     * 更新数据
     *
     * @param chatMessages 实例对象
     * @return 实例对象
     */
    @ApiOperation("更新数据")
    @PutMapping
    public ResponseEntity<ChatMessages> edit(ChatMessages chatMessages){
        return ResponseEntity.ok(chatMessagesService.update(chatMessages));
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param messageId 主键
     * @return 是否成功
     */
    @ApiOperation("通过主键删除数据")
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Long messageId){
        return ResponseEntity.ok(chatMessagesService.deleteById(messageId));
    }

    // 查询历史聊天记录
     @PostMapping("/history")
     @ApiOperation("查询历史聊天记录")
     public ResponseResult getChatHistory(@RequestBody ChatMessageDTO chatMessageDTO){
         List<ChatMessages> chatMessages = chatMessagesService.getChatMessages(chatMessageDTO);
         return ResponseResult.okResult(chatMessages);

     }
}