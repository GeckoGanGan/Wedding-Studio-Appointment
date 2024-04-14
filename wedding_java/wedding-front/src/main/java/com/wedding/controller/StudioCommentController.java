package com.wedding.controller;

import com.wedding.domain.ResponseResult;
import com.wedding.domain.entity.Comment;
import com.wedding.service.CommentService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhuqi
 * @Date: 2024/03/18/21:17
 * @Description: 影楼评论控制类
 */
@RestController
@RequestMapping("/comment")
@Slf4j
public class StudioCommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/commentList")
    @ApiOperation("获取影楼评论列表")
    public ResponseResult commentList(Long studioId, Integer pageNum, Integer pageSize,String type){
        return commentService.commentList(type,studioId,pageNum,pageSize);
    }
    @PostMapping("/add")
    @ApiOperation("添加影楼评论")
    public ResponseResult addComment(@RequestBody Comment comment){
        log.info("--------------------------------------------添加评论:{}",comment);
        return commentService.addComment(comment);
    }


}
