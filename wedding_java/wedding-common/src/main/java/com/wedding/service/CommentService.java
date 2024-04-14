package com.wedding.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wedding.domain.ResponseResult;
import com.wedding.domain.entity.Comment;


/**
 * 评论表(StudioComment)表服务接口
 *
 * @author zhudake
 * @since 2024-03-18 21:16:03
 */
public interface CommentService extends IService<Comment> {

    ResponseResult commentList(String commentTypeArticle, Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult addComment(Comment comment);
}

