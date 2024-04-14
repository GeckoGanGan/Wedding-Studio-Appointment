package com.wedding.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wedding.constants.SysConstants;
import com.wedding.domain.ResponseResult;
import com.wedding.domain.entity.Comment;
import com.wedding.domain.entity.Users;
import com.wedding.domain.enums.AppHttpCodeEnum;
import com.wedding.domain.vo.CommentVo;
import com.wedding.domain.vo.PageVO;
import com.wedding.exception.SystemException;
import com.wedding.mapper.CommentMapper;
import com.wedding.service.CommentService;
import com.wedding.service.UsersService;
import com.wedding.utils.BeanCopyUtils;
import com.wedding.utils.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 评论表(StudioComment)表服务实现类
 *
 * @author zhudake
 * @since 2024-03-18 21:16:03
 */
@Service("studioCommentService")
@Slf4j
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    private UsersService userService;
    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult commentList(String commentTypeArticle, Long articleId, Integer pageNum, Integer pageSize) {
        return getStudioCommentList(commentTypeArticle,articleId,pageNum,pageSize);
    }
    @Override
    public ResponseResult addComment(Comment comment) {
        log.info("-----------------添加评论---------------");
        log.info("-----------------comment:{}",comment);
        //评论内容不能为空！
        if (!StringUtils.hasText(comment.getContent())){
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOTNULL);
        }
        save(comment);
        return ResponseResult.okResult();
    }

    private ResponseResult getStudioCommentList(String commentType,Long articleId, Integer pageNum, Integer pageSize){
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        // 评论类型
        wrapper.eq(Comment::getType,commentType);
        // 对articleId进行判断
        wrapper.eq(articleId!=null, Comment::getStudioId,articleId);
        // 查询所有的根评论
        wrapper.eq(Comment::getRootId, SysConstants.COMMENT_ISROOT);
        // 按时间倒序排序
        wrapper.orderByDesc(Comment::getCreatedTime);
        Page<Comment> page = new Page<>();
        page.setSize(pageSize);
        page.setCurrent(pageNum);
        page(page,wrapper);

        List<Comment> commentList = page.getRecords();
        if (commentList.isEmpty()){
            ResponseResult.errorResult(AppHttpCodeEnum.COMMENT_IS_NULL);
        }
        log.info("-------commentList--1:{}",commentList);
        // 获取头像
        // 设置自己评论列表
        List<CommentVo> commentVoList  = new ArrayList<>();
        commentList.forEach(comment -> {
           CommentVo commentVo = BeanCopyUtils.copyBean(comment, CommentVo.class);
            log.info("bean拷贝后-----commentVo:{}",commentVo);
            commentVo.setUsername(userService.getById(comment.getCreatedBy()).getNickName());

            List<CommentVo> childrenCommenList = getChildrenCommenList(comment);
            commentVo.setChildren(childrenCommenList);
            LambdaQueryWrapper<Users> wrapper1 = new LambdaQueryWrapper<>();
            wrapper1.eq(comment.getCreatedBy()!=null,Users::getId,comment.getCreatedBy());
            String avatar = userService.getOne(wrapper1).getAvatar();
            commentVo.setAvatar(avatar);
            commentVoList.add(commentVo);
            log.info("-------comment------:{}",commentVo);
        });
        log.info("-------commentList---2:{}",commentList);

        commentVoList.forEach(commentVo ->{
            log.info("-------commentVo------:{}",commentVo);
        });
        return ResponseResult.okResult(new PageVO(page.getTotal(),commentVoList,true));
    }
    // 查询子品论列表
    private  List<CommentVo> getChildrenCommenList(Comment comment){
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(comment.getId()!=null, Comment::getRootId, comment.getId());
        // 按时间倒序排序
        queryWrapper.orderByDesc(Comment::getCreatedTime);

        List<Comment> children = list(queryWrapper);
        if (!children.isEmpty()){
            List<CommentVo> commentVos = convertChildren(children);
            return commentVos;
        }
        return null;
    }

    private List<CommentVo> convertChildren(List<Comment> childrenList){
        if (childrenList == null || childrenList.isEmpty()) {
            return Collections.emptyList();  // 返回空列表或者根据实际情况处理
        }
        return childrenList.stream()
                .map(child->{
                    log.info("-------child:{}",child);
                    CommentVo commentVo = new CommentVo();
                    commentVo.setId(child.getId());
                    commentVo.setStudioId(child.getStudioId());
                    commentVo.setContent(child.getContent());
                    commentVo.setToCommentId(child.getToCommentId());
                    commentVo.setToCommentUserId(child.getToCommentUserId());
                    commentVo.setToCommentId(child.getToCommentId());
                    commentVo.setCreatedBy(child.getCreatedBy());
                    commentVo.setCreatedTime(child.getCreatedTime());
                    commentVo.setContent(child.getContent());
                    // 查询评论头像
                    LambdaQueryWrapper<Users> wrapper = new LambdaQueryWrapper<>();
                    wrapper.eq(Users::getId, child.getCreatedBy());
                    commentVo.setAvatar(userService.getOne(wrapper).getAvatar());

                    log.info("------nickName:{}",userService.getById(child.getCreatedBy()).getNickName());
                    commentVo.setUsername(userService.getById(child.getCreatedBy()).getNickName());

                    log.info("递归-------commentVo:{}",commentVo);
                    if (child.getToCommentUserId()!=-1){
                        String nickName = userService.getById(child.getToCommentUserId()).getNickName();
                        commentVo.setToCommentUserName(nickName);
                    }else {
                        commentVo.setToCommentUserName(null);
                    }
                    return commentVo;
                }).collect(Collectors.toList());
    }

}

