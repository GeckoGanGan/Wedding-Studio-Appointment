package com.wedding.contrloller;

import com.wedding.domain.ResponseResult;
import com.wedding.domain.dto.PageDTO;
import com.wedding.domain.entity.Message;
import com.wedding.service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


 /**
 * 留言表;(message)表控制层
 * @author : zhudake
 * @date : 2024-4-7
 */
@Api(tags = "留言表对象功能接口")
@RestController
@RequestMapping("/message")
public class MessageController{
    @Autowired
    private MessageService messageService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation("通过ID查询单条数据")
    @GetMapping("{id}")
    public ResponseResult queryById(Long id){
        return messageService.queryById(id);
    }
    
    /** 
     * 分页查询
     *
     * @param queryParam 筛选条件
     * @return 查询结果
     */
    @ApiOperation("分页查询")
    @GetMapping
    public ResponseResult paginQuery(PageDTO<Message> queryParam){
        return messageService.paginQuery(queryParam);
    }
    
    /** 
     * 新增数据
     *
     * @param message 实例对象
     * @return 实例对象
     */
    @ApiOperation("新增数据")
    @PostMapping
    public ResponseResult add(Message message){
        return messageService.insert(message);
    }
    
    /** 
     * 更新数据
     *
     * @param message 实例对象
     * @return 实例对象
     */
    @ApiOperation("更新数据")
    @PutMapping
    public ResponseResult edit(Message message){
        return messageService.update(message);
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @ApiOperation("通过主键删除数据")
    @DeleteMapping
    public ResponseResult deleteById(Long id){
        return messageService.deleteById(id);
    }
}